package com.hungnx.clinicbooking.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {

    private static final DateTimeFormatter VN_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        try {
            if (text == null || text.isBlank()) {
                return null;
            }

            text = text.trim();

            if (text.contains("/")) {
                return LocalDate.parse(text, VN_FORMATTER);
            }

            return LocalDate.parse(text, ISO_FORMATTER);
        } catch (Exception e) {
            throw new ParseException("Ngày không đúng định dạng", 0);
        }
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return object == null ? "" : object.format(ISO_FORMATTER);
    }
}