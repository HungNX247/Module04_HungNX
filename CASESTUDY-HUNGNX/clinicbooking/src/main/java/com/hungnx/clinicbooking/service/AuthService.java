package com.hungnx.clinicbooking.service;

import com.hungnx.clinicbooking.entity.User;
import com.hungnx.clinicbooking.enums.Role;
import com.hungnx.clinicbooking.repository.UserRepository;
import com.hungnx.clinicbooking.web.form.RegisterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegisterForm registerForm) {
        if (!registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            throw new IllegalArgumentException("Mật khẩu nhập lại không khớp");
        }

        if (userRepository.findByPhone(registerForm.getPhone().trim()).isPresent()) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại");
        }

        User user = User.builder()
                .fullName(registerForm.getFullName().trim())
                .phone(registerForm.getPhone().trim())
                .passwordHash(passwordEncoder.encode(registerForm.getPassword().trim()))
                .role(Role.PATIENT)
                .build();

        userRepository.save(user);
    }
}
