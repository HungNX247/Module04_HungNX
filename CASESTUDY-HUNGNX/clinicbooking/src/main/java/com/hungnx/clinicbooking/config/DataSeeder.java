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
            seedUsers(userRepository, passwordEncoder);

            Specialty general = getOrCreateSpecialty(specialtyRepository, "Nội tổng quát");
            Specialty ent = getOrCreateSpecialty(specialtyRepository, "Tai Mũi Họng");
            Specialty cardio = getOrCreateSpecialty(specialtyRepository, "Tim mạch");
            Specialty rhm = getOrCreateSpecialty(specialtyRepository, "Răng hàm mặt");
            Specialty nhi = getOrCreateSpecialty(specialtyRepository, "Khoa Nhi");
            Specialty ngoai = getOrCreateSpecialty(specialtyRepository, "Ngoại tổng hợp");

            seedDoctorIfNotExists(doctorRepository, "BS. Lê Thị Yến", general, "0334992910", 200000);
            seedDoctorIfNotExists(doctorRepository, "BS. Trần Ngọc Dương", ent, "0334992911", 250000);
            seedDoctorIfNotExists(doctorRepository, "BS. Phạm Ngọc Thảo", cardio, "0334992912", 300000);
            seedDoctorIfNotExists(doctorRepository, "BS. Ngô Hồng Loan", rhm, "0334992913", 500000);
            seedDoctorIfNotExists(doctorRepository, "BS. Nguyễn Tấn Trường", nhi, "0334992914", 350000);
            seedDoctorIfNotExists(doctorRepository, "BS. Tạ Hoàng Phong", ngoai, "0334992915", 450000);
            seedDoctorIfNotExists(doctorRepository, "BS. Hoàng Minh Đức", general, "0334992916", 400000);
        };
    }

    private void seedUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        if (userRepository.findByPhone("0334992991").isEmpty()) {
            userRepository.save(
                    User.builder()
                            .fullName("System Admin 1")
                            .phone("0334992991")
                            .passwordHash(passwordEncoder.encode("123456"))
                            .role(Role.ADMIN)
                            .build()
            );
        }

        if (userRepository.findByPhone("0900000001").isEmpty()) {
            userRepository.save(
                    User.builder()
                            .fullName("Nguyễn Văn Bệnh Nhân")
                            .phone("0900000001")
                            .passwordHash(passwordEncoder.encode("123456"))
                            .role(Role.PATIENT)
                            .build()
            );
        }

        if (userRepository.findByPhone("0900000002").isEmpty()) {
            userRepository.save(
                    User.builder()
                            .fullName("Trần Thị Bệnh Nhân")
                            .phone("0900000002")
                            .passwordHash(passwordEncoder.encode("123456"))
                            .role(Role.PATIENT)
                            .build()
            );
        }
    }

    private Specialty getOrCreateSpecialty(SpecialtyRepository specialtyRepository, String name) {
        return specialtyRepository.findByName(name)
                .orElseGet(() -> specialtyRepository.save(
                        Specialty.builder()
                                .name(name)
                                .build()
                ));
    }

    private void seedDoctorIfNotExists(DoctorRepository doctorRepository,
                                       String fullName,
                                       Specialty specialty,
                                       String phone,
                                       Integer price) {
        if (doctorRepository.findByPhone(phone).isEmpty()) {
            doctorRepository.save(
                    Doctor.builder()
                            .fullName(fullName)
                            .specialty(specialty)
                            .phone(phone)
                            .price(price)
                            .build()
            );
        }
    }
}