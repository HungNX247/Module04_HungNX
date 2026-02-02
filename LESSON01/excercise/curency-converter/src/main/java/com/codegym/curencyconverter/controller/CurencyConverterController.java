package com.codegym.curencyconverter.controller;

import com.codegym.curencyconverter.model.CurrencyForm;
import com.codegym.curencyconverter.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CurencyConverterController {
    @Autowired
    private CurrencyService currencyService;
    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("form", new CurrencyForm());
        model.addAttribute("currencies",currencyService.getRates().keySet());
        return "index";
    }

    @PostMapping("/convert")
    public String convert(@ModelAttribute CurrencyForm form,
                          Model model) {
        if (form.getAmount() <= 0) {
            model.addAttribute("error","Số tiền phải lớn hơn 0");
        } else {
            double result = currencyService.convert(form.getFrom(), form.getTo(), form.getAmount());
            model.addAttribute("result",result);
        }

        model.addAttribute("currencies",currencyService.getRates().keySet());
        return "index";
    }
}
