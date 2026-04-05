package com.hungnx.clinicbooking.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        try {
           return LocalDate.parse(text, FORMATTER);
        } catch (Exception e) {
            throw new ParseException("Ngày không đúng đinh dạng", 0);
        }
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return object == null ? "": object.format(FORMATTER);
    }
}
