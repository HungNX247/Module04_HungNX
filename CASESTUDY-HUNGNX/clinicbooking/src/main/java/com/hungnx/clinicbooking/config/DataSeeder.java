package com.hungnx.clinicbooking.config;

import com.hungnx.clinicbooking.entity.Doctor;
import com.hungnx.clinicbooking.entity.Specialty;
import com.hungnx.clinicbooking.entity.User;
import com.hungnx.clinicbooking.enums.Role;
import com.hungnx.clinicbooking.repository.DoctorRepository;
import com.hungnx.clinicbooking.repository.SpecialtyRepository;
import com.hungnx.clinicbooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {
    @Bean
    CommandLineRunner seedData(UserRepository userRepository,
                               SpecialtyRepository specialtyRepository,
                               DoctorRepository doctorRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByPhone("0334992991").isEmpty()) {
                userRepository.save(
                        User.builder().fullName("System Admin 1")
                                .phone("0334992975")
                                .passwordHash(passwordEncoder.encode("123456"))
                                .role(Role.ADMIN)
                                .build()
                );
            }

            Specialty general = specialtyRepository.findByName("Nội tổng quát")
                    .orElseGet(() -> specialtyRepository.save(
                            Specialty.builder().name("Nội tổng quát")
                                    .build()
                    ));

            Specialty ent = specialtyRepository.findByName("Tai Mũi Họng")
                    .orElseGet(() -> specialtyRepository.save(
                            Specialty.builder().name("Tai Mũi Họng")
                                    .build()
                    ));

            Specialty cardio = specialtyRepository.findByName("Tim mạch")
                    .orElseGet(() -> specialtyRepository.save(
                            Specialty.builder(). name("Tim mạch")
                            .build()));

            if (doctorRepository.count() == 0) {
                doctorRepository.save(
                        Doctor.builder()
                                .fullName("BS. Lê Thị Yến")
                                .specialty(general)
                                .phone("0334992910")
                                .price(200000)
                                .build()
                );

                doctorRepository.save(
                        Doctor.builder()
                                .fullName("BS. Trần Ngọc Dương")
                                .specialty(ent)
                                .phone("0334992911")
                                .price(250000)
                                .build()
                );

                doctorRepository.save(
                        Doctor.builder()
                                .fullName("BS. Phạm Ngọc Thảo")
                                .specialty(cardio)
                                .phone("0334992912")
                                .price(300000)
                                .build()
                );
            }
        };
    }
}
