package com.hungnx.clinicbooking.controller;

import com.hungnx.clinicbooking.service.DoctorService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping("/doctors")
    public String doctorList(Model model) {
        model.addAttribute("doctors",doctorService.findAll());
        return "doctor/list";
    }
}
