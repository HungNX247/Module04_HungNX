package com.hungnx.clinicbooking.config;

import com.hungnx.clinicbooking.formatter.LocalDateFormatter;
import com.hungnx.clinicbooking.formatter.LocalTimeFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry formatterRegistry) {
        formatterRegistry.addFormatter(new LocalDateFormatter());
        formatterRegistry.addFormatter(new LocalTimeFormatter());
    }
}
