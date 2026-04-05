package com.hungnx.clinicbooking.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AppointmentDateTimeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAppointmentDateTime {
    String message() default "Ngày giờ không hợp lệ";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
