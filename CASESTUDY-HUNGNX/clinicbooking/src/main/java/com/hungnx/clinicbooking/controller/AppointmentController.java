package com.hungnx.clinicbooking.controller;

import com.hungnx.clinicbooking.entity.Appointment;
import com.hungnx.clinicbooking.entity.User;
import com.hungnx.clinicbooking.service.AppointmentService;
import com.hungnx.clinicbooking.service.DoctorService;
import com.hungnx.clinicbooking.web.form.AppointmentForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;

    @GetMapping
    public String list(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("appointments", appointmentService.findMine(user.getId()));
        return "appointment/list";
    }

    @GetMapping("/create")
    public String createPage(@RequestParam(required = false) Integer doctorId, Model model) {
        if (!model.containsAttribute("form")) {
            AppointmentForm form = new AppointmentForm();
            if (doctorId != null) {
                form.setDoctorId(doctorId);
            }

            model.addAttribute("form", form);
        }
        model.addAttribute("doctors", doctorService.findAll());
        model.addAttribute("isEdit", false);
        model.addAttribute("pageTitle", "Đặt lịch khám");
        return "appointment/form";
    }

    @PostMapping("/create")
    public String create(@AuthenticationPrincipal User user,
                         @Valid@ModelAttribute("form") AppointmentForm form,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("doctors", doctorService.findAll());
            model.addAttribute("isEdit", false);
            model.addAttribute("pageTitle","Đặt lịch khám");
            return "appointment/form";
        }

        appointmentService.createMine(user.getId(), form);
        return "redirect:/appointments?created";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable Integer id,
                           @AuthenticationPrincipal User user,
                           Model model) {
        Appointment appointment = appointmentService.getMineDetail(id, user.getId());

        if (!model.containsAttribute("form")) {
            AppointmentForm form = new AppointmentForm();
            form.setDoctorId(appointment.getDoctor().getId());
            form.setAppointmentDate(appointment.getAppointmentDate());
            form.setAppointmentTime(appointment.getAppointmentTime());
            form.setNote(appointment.getNote());
            model.addAttribute("form",form);
        }

        model.addAttribute("doctors", doctorService.findAll());
        model.addAttribute("isEdit", true);
        model.addAttribute("appointmentId", id);
        model.addAttribute("pageTitle", "Sửa lịch hẹn");
        return "appointment/form";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Integer id,
                       @AuthenticationPrincipal User user,
                       @Valid @ModelAttribute("form") AppointmentForm form,
                       BindingResult bindingResult,
                       Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("doctors", doctorService.findAll());
            model.addAttribute("isEdit", true);
            model.addAttribute("appointmentId", id);
            model.addAttribute("pageTitle", "Sửa lịch hẹn");
            return "appointment/form";
        }

        appointmentService.updateMine(user.getId(), id, form);
        return "redirect:/appointments?updated";
    }

    @PostMapping("/{id}/cancel")
    public String cancel(@PathVariable Integer id,
                         @AuthenticationPrincipal User user) {
        appointmentService.cancelMine(id, user.getId());
        return "redirect:/appointments?canceled";
    }
}
