package com.hungnx.clinicbooking.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AppointmentTimeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAppointmentTime {
    String message() default "Khung giờ khám không hợp lệ";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
