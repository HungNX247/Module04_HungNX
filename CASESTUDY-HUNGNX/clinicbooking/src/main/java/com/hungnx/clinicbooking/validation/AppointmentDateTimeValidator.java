package com.hungnx.clinicbooking.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class AppointmentDateTimeValidator implements ConstraintValidator<ValidAppointmentDateTime, AppointmentDateTimeCarrier> {
    @Override
    public boolean isValid(AppointmentDateTimeCarrier appointmentDateTimeCarrier, ConstraintValidatorContext constraintValidatorContext) {
        if (appointmentDateTimeCarrier == null
                ||appointmentDateTimeCarrier.getAppointmentDate() == null
        || appointmentDateTimeCarrier.getAppointmentTime() == null) {
            return true;
        }

        LocalDateTime appointmentDateTime = LocalDateTime.of(appointmentDateTimeCarrier.getAppointmentDate(),
                appointmentDateTimeCarrier.getAppointmentTime());

        if (appointmentDateTime.isBefore(LocalDateTime.now())) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Không được đặt lịch trong quá khứ")
                    .addPropertyNode("appointmentDate")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
