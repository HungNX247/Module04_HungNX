package com.hungnx.clinicbooking.service;

import com.hungnx.clinicbooking.entity.User;
import com.hungnx.clinicbooking.enums.Role;
import com.hungnx.clinicbooking.repository.UserRepository;
import com.hungnx.clinicbooking.web.form.ChangePasswordForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAllPatient() {
        return userRepository.findByRoleOrderByIdDesc(Role.PATIENT);
    }
    public void changePassword(Integer currentUserId, ChangePasswordForm form) {
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng"));

        if (!form.getNewPassword().equals(form.getConfirmPassword())) {
            throw new IllegalArgumentException("Mật khẩu mới nhập không khớp");
        }

        if (!passwordEncoder.matches(form.getCurrentPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Mật khẩu hiện tại không đúng");
        }

        if (passwordEncoder.matches(form.getNewPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Mật khẩu mới không được trùng với mật khẩu hiện tại");
        }

        user.setPasswordHash(passwordEncoder.encode(form.getNewPassword()));
        userRepository.save(user);
    }
}
