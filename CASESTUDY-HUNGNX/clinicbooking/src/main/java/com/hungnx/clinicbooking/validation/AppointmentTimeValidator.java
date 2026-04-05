package com.hungnx.clinicbooking.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;

public class AppointmentTimeValidator implements ConstraintValidator<ValidAppointmentTime, LocalTime> {
    @Override
    public boolean isValid(LocalTime localTime, ConstraintValidatorContext constraintValidatorContext) {
        if (localTime == null) {
            return true;
        }

        LocalTime start = LocalTime.of(7, 0);
        LocalTime end = LocalTime.of(17, 0);

        boolean inWorkingHours = !localTime.isBefore(start) && !localTime.isAfter(end);
        boolean validMinute = localTime.getMinute() == 0 || localTime.getMinute() == 30;
        boolean lunchBreak = !localTime.isBefore(LocalTime.of(12, 0)) && localTime.isAfter(LocalTime.of(13, 30));
        return inWorkingHours && validMinute && lunchBreak;
    }
}
