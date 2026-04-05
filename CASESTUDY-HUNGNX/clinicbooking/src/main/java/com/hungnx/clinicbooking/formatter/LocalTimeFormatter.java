package com.hungnx.clinicbooking.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalTimeFormatter implements Formatter<LocalTime> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        try {
           return LocalTime.parse(text, FORMATTER);
        } catch (Exception e) {
            throw new ParseException("Giờ không đúng định dạng", 0);
        }
    }

    @Override
    public String print(LocalTime object, Locale locale) {
        return object == null? "" : object.format(FORMATTER);
    }
}
