package com.hungnx.clinicbooking.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateAppointmentException.class)
    public String handleDuplicateAppointment(DuplicateAppointmentException duplicateAppointmentException, Model model) {
        model.addAttribute("title", "Trùng lịch khám");
        model.addAttribute("message", duplicateAppointmentException.getMessage());
        model.addAttribute("backUrl","/appointments");
        return "error/business-error";
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public String handleAppointmentNotFound(AppointmentNotFoundException appointmentNotFoundException, Model model) {
        model.addAttribute("title", "Không tìm thấy lịch hẹn");
        model.addAttribute("message", appointmentNotFoundException.getMessage());
        model.addAttribute("backUrl", "/appointments");
        return "error/business-error";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrity(DataIntegrityViolationException dataIntegrityViolationException, Model model) {
        model.addAttribute("title", "Lỗi dữ liệu");
        model.addAttribute("message","Dữ liệu bị trùng hoặc vi phạm ràng buộc. Vui lòng kiểm tra lại.");
        model.addAttribute("backUrl", "/appointments");
        return "error/business-error";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied() {
        return "error/403";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneric(Exception exception, Model model) {
        model.addAttribute("title", "Đã xảy ra lỗi");
        model.addAttribute("message", exception.getMessage());
        model.addAttribute("backUrl", "/");
        return "error/business-error";
    }
}
