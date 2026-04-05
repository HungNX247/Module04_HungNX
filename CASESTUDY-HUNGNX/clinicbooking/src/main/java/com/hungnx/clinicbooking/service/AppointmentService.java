package com.hungnx.clinicbooking.service;

import com.hungnx.clinicbooking.entity.Appointment;
import com.hungnx.clinicbooking.entity.Doctor;
import com.hungnx.clinicbooking.entity.User;
import com.hungnx.clinicbooking.enums.AppointmentStatus;
import com.hungnx.clinicbooking.enums.Role;
import com.hungnx.clinicbooking.exception.AppointmentNotFoundException;
import com.hungnx.clinicbooking.exception.DuplicateAppointmentException;
import com.hungnx.clinicbooking.repository.AppointmentRepository;
import com.hungnx.clinicbooking.repository.UserRepository;
import com.hungnx.clinicbooking.web.form.AdminAppointmentForm;
import com.hungnx.clinicbooking.web.form.AppointmentForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorService doctorService;
    private final UserRepository userRepository;

    public List<Appointment> findMine(Integer patientId) {
        return appointmentRepository.findAllMine(patientId);
    }

    public List<Appointment> findAllForAdmin() {
        return appointmentRepository.findAllForAdmin();
    }

    public Appointment getMineDetail(Integer appointmentId, Integer patientId) {
        return appointmentRepository.findMineById(appointmentId, patientId)
                .orElseThrow(() -> new AppointmentNotFoundException("Không tìm thấy lịch hẹn của bạn"));
    }

    @Transactional
    public void createMine(Integer patientId, AppointmentForm form) {
        User patient = findPatient(patientId);
        Doctor doctor = doctorService.findById(form.getDoctorId());
        ensureDoctorSlotAvailable(
                form.getDoctorId(),
                form.getAppointmentDate(),
                form.getAppointmentTime(),
                null
        );

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .appointmentTime(form.getAppointmentTime())
                .appointmentDate(form.getAppointmentDate())
                .status(AppointmentStatus.BOOKED)
                .note(normallizeNote(form.getNote()))
                .build();

        appointmentRepository.save(appointment);
    }

    @Transactional
    public void createByAdmin(AdminAppointmentForm form) {
        User patient = findPatient(form.getPatientId());
        Doctor doctor = doctorService.findById(form.getDoctorId());

        ensureDoctorSlotAvailable(
                form.getDoctorId(),
                form.getAppointmentDate(),
                form.getAppointmentTime(),
                null
        );

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .appointmentDate(form.getAppointmentDate())
                .appointmentTime(form.getAppointmentTime())
                .status(AppointmentStatus.BOOKED)
                .note(normallizeNote(form.getNote()))
                .build();

        appointmentRepository.save(appointment);
    }

    @Transactional
    public void updateMine(Integer patientId, Integer appointmentId, AppointmentForm form) {
        Appointment old = appointmentRepository.findMineById(appointmentId, patientId)
                .orElseThrow(() -> new AppointmentNotFoundException("Không tìm thấy lịch hẹn của bạn"));

        if (old.getStatus() != AppointmentStatus.BOOKED) {
            throw new IllegalArgumentException("Chỉ lịch ở trạng thái BOOKED mới được sửa");
        }

        LocalDateTime oldAppointmentDateTime = LocalDateTime.of(
                old.getAppointmentDate(),
                old.getAppointmentTime()
        );

        long minutes = Duration.between(LocalDateTime.now(), oldAppointmentDateTime).toMinutes();
        if (minutes < 60) {
            throw new IllegalArgumentException("Chỉ được sửa lịch trước giờ khám ít nhất 60 phút");
        }

        Doctor doctor = doctorService.findById(form.getDoctorId());

        ensureDoctorSlotAvailable(
                form.getDoctorId(),
                form.getAppointmentDate(),
                form.getAppointmentTime(),
                appointmentId
        );

        old.setDoctor(doctor);
        old.setAppointmentDate(form.getAppointmentDate());
        old.setAppointmentTime(form.getAppointmentTime());
        old.setNote(form.getNote());
    }

    @Transactional
    public void cancelMine(Integer appointmentId, Integer patientId) {
        Appointment appointment = appointmentRepository.findMineById(appointmentId, patientId)
                .orElseThrow(() -> new AppointmentNotFoundException("Không tìm thấy lịch hẹn của bạn"));

        if (appointment.getStatus() != AppointmentStatus.BOOKED) {
            throw new IllegalArgumentException("Chỉ lịch ở trạng thái BOOKED mới được hủy");
        }

        appointment.setStatus(AppointmentStatus.CANCELED);
    }

    private User findPatient(Integer patientId) {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bệnh nhân"));

        if (patient.getRole() != Role.PATIENT) {
            throw new IllegalArgumentException("Người được chọn không phải bệnh nhân");
        }
        return patient;
    }

    private String normallizeNote(String note) {
        return (note == null || note.isBlank()) ? null : note.trim();
    }

    private void ensureDoctorSlotAvailable(Integer doctorId,
                                           LocalDate date,
                                           LocalTime time,
                                           Integer excludeId) {
        boolean busy = excludeId == null ? appointmentRepository.existsByDoctor_IdAndAppointmentDateAndAppointmentTimeAndStatus(
                doctorId, date, time, AppointmentStatus.BOOKED
        ) : appointmentRepository.existsByDoctor_IdAndAppointmentDateAndAppointmentTimeAndStatusAndIdNot(
                doctorId, date, time, AppointmentStatus.BOOKED, excludeId
        );

        if (busy) {
            throw new DuplicateAppointmentException("Trùng lịch khám. Bác sĩ đã có người đặt ở khung giờ này");
        }
    }
}
