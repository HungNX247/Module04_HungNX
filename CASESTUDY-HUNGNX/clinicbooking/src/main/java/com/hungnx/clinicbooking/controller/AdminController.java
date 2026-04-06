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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    private void loadLookupData(Model model) {
        model.addAttribute("patients",userService.findAllPatient());
        model.addAttribute("doctors", doctorService.findAll());
    }
}
