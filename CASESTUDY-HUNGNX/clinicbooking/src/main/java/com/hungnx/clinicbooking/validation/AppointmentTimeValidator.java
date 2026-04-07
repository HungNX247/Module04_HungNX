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
        LocalTime lunchStart = LocalTime.of(12, 0);
        LocalTime lunchEnd = LocalTime.of(13, 30);

        boolean inLunchBreak = !localTime.isBefore(lunchStart) && localTime.isBefore(lunchEnd);
        boolean validMinute = localTime.getMinute() == 0 || localTime.getMinute() == 30;

        if (localTime.isBefore(start) || localTime.isAfter(end)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Giờ khám bệnh từ 07:00 - 17:00")
                    .addConstraintViolation();
            return false;
        }

        if (!validMinute) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "Giờ khám chỉ được chọn theo mốc 30 phút: 07:00, 07:30, 08:00...."
            ).addConstraintViolation();
            return false;
        }

        if (inLunchBreak) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Không thể đặt lịch trong giờ nghỉ trưa (12:00 - 13:30). " +
                    "Vui lòng chọn khung giờ khác").addConstraintViolation();
            return false;
        }
        return true;
    }
}