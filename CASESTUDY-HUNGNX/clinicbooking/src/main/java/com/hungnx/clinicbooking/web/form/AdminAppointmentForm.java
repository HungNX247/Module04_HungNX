package com.hungnx.clinicbooking.web.form;

import com.hungnx.clinicbooking.validation.AppointmentDateTimeCarrier;
import com.hungnx.clinicbooking.validation.ValidAppointmentDateTime;
import com.hungnx.clinicbooking.validation.ValidAppointmentTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ValidAppointmentDateTime
public class AdminAppointmentForm implements AppointmentDateTimeCarrier {
    @NotNull(message = "Vui lòng chọn bệnh nhân")
    private Integer patientId;

    @NotNull(message = "Vui lòng chọn bác sĩ")
    private Integer doctorId;

    @NotNull(message = "Vui lòng chọn ngày khám")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;

    @NotNull(message = "Vui lòng chọn giờ khám")
    @DateTimeFormat(pattern = "HH:mm")
    @ValidAppointmentTime
    private LocalTime appointmentTime;

    @Size(max = 255, message = "Ghi chú tối đa 255 ký tự")
    private String note;
}
