package com.codegym.springtimezonegradle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.TimeZone;

@Controller
public class TimeController {
    @GetMapping(value = "/world-clock")
    public String getTimeByTimezone(ModelMap modelMap, @RequestParam(name = "city", required = false, defaultValue = "Asia/Ho_Chi_Minh") String city){
        Date date = new Date();

        TimeZone localTimeZone = TimeZone.getDefault();

        TimeZone targetTimeZone = TimeZone.getTimeZone(city);

        long timeDifference = targetTimeZone.getRawOffset() - localTimeZone.getRawOffset();

        long targetTimeMilliseconds = date.getTime() + timeDifference;

        date.setTime(targetTimeMilliseconds);

        modelMap.addAttribute("city",city);
        modelMap.addAttribute("date", date);
        return "index";
    }
}
