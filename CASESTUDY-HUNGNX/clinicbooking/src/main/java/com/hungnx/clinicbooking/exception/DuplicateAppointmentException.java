package com.hungnx.clinicbooking.exception;

public class DuplicateAppointmentException extends  RuntimeException {
    public DuplicateAppointmentException(String message) {
        super(message);
    }
}
