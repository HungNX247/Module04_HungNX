package com.hungnx.clinicbooking.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterForm {
    @NotBlank(message = "Vui lòng nhập họ tên")
    @Size(min = 2, message = "Họ tên ít nhất phải có 2 ký tự")
    private String fullName;

    @NotBlank(message = "Vui lòng nhập số điện thoại")
    @Pattern(
            regexp = "^(0[35789])[0-9]{8}$",
            message = "Số điện thoại không hợp lệ. Ví dụ: 0901234567"
    )
    private String phone;

    @NotBlank(message = "Vui lòng nhập mật khẩu")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    @NotBlank(message = "Vui lòng nhập mật khẩu")
    private String confirmPassword;
}
