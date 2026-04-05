package com.hungnx.clinicbooking.validation;

import java.time.LocalDate;
import java.time.LocalTime;

public interface AppointmentDateTimeCarrier {
    LocalDate getAppointmentDate();
    LocalTime getAppointmentTime();
}
