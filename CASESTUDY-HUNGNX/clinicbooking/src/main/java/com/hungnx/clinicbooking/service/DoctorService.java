package com.hungnx.clinicbooking.service;

import com.hungnx.clinicbooking.entity.Doctor;
import com.hungnx.clinicbooking.exception.DoctorNotFoundException;
import com.hungnx.clinicbooking.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public List<Doctor> findAll() {
        return doctorRepository.findAllWithSpecialty();
    }

    public Doctor findById(Integer id) {
        return doctorRepository.findByIdWithSpecialty(id)
                .orElseThrow(() -> new DoctorNotFoundException("Bác sĩ không tồn tại"));
    }
}
