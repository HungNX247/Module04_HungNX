package com.hungnx.clinicbooking.repository;

import com.hungnx.clinicbooking.entity.Appointment;
import com.hungnx.clinicbooking.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query("""
select a\s
from Appointment a\s
join fetch a.patient p\s
join fetch a.doctor d\s
join fetch d.specialty\s
where p.id = :patientId\s
order by a.appointmentDate desc , a.appointmentTime desc\s
""")
    List<Appointment> findAllMine(Integer patientId);

    @Query("""
select a\s
from Appointment  a\s
join fetch a.patient p\s
join fetch a.doctor d\s
join fetch d.specialty\s
where a.id = :id and p.id = :patientId""")
    Optional<Appointment> findMineById(Integer id, Integer patientId);

    @Query("""
select a\s
from Appointment a\s
join fetch a.patient p\s
join fetch a.doctor d\s
join fetch d.specialty\s
order by a.appointmentDate desc , a.appointmentTime desc\s
""")
    List<Appointment> findAllForAdmin();

    boolean existsByDoctor_IdAndAppointmentDateAndAppointmentTimeAndStatus(
            Integer doctorId,
            LocalDate appointmentDate,
            LocalTime appointmentTime,
            AppointmentStatus status
    );

    boolean existsByDoctor_IdAndAppointmentDateAndAppointmentTimeAndStatusAndIdNot(
            Integer doctorId,
            LocalDate appointmentDate,
            LocalTime appointmentTime,
            AppointmentStatus status,
            Integer id
    );
}
