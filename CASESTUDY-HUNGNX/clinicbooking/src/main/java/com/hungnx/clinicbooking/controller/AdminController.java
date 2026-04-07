package com.hungnx.clinicbooking.controller;

import com.hungnx.clinicbooking.service.AppointmentService;
import com.hungnx.clinicbooking.service.DoctorService;
import com.hungnx.clinicbooking.service.UserService;
import com.hungnx.clinicbooking.web.form.AdminAppointmentForm;
import com.hungnx.clinicbooking.web.form.AppointmentForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final UserService userService;

    @GetMapping("/appointments")
    public String appointmentList(Model model) {
        model.addAttribute("appointments", appointmentService.findAllForAdmin());
        return "admin/appointment-list";
    }

    @GetMapping("/appointments/create")
    public String createPage(Model model) {
        if (!model.containsAttribute("form")) {
            model.addAttribute("form", new AdminAppointmentForm());
        }
        loadLookupData(model);
        return "admin/appointment-form";
    }

    @PostMapping("/appointments/create")
    public String create(@Valid @ModelAttribute("form")AdminAppointmentForm form,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            loadLookupData(model);
            return "/admin/appointment-form";
        }
        appointmentService.createByAdmin(form);
        return "redirect:/admin/appointments?created";
    }

    @PostMapping("/appointments/{id}/delete")
    public String deleteCancelAppointment(@PathVariable Integer id,
                                          RedirectAttributes redirectAttributes) {
        try {
            appointmentService.deleteCancelByAdmin(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa lịch hẹn thành công");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/appointments";
    }

    private void loadLookupData(Model model) {
        model.addAttribute("patients",userService.findAllPatient());
        model.addAttribute("doctors", doctorService.findAll());
    }
}
