package com.mapfre.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public class DateUtils {
    // Formateador para solo FECHA (ej: "20/01/2026")
    private static final DateTimeFormatter UI_DATE_FORMATTER = DateTimeFormatter.ofPattern("d/MM/yyyy");
    // Formateador para FECHA Y HORA (ej: "20/01/2026 10:21:04")
    private static final DateTimeFormatter UI_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm:ss");

    private static final DateTimeFormatter FLEXIBLE_FORMATTER =
            new DateTimeFormatterBuilder()
                    .appendPattern("d/MM/yyyy")
                    .optionalStart()
                    .appendPattern(" HH:mm:ss")
                    .optionalEnd()
                    .toFormatter();


    /**
     * Convierte un String de fecha a LocalDate.
     * Ejemplo: Input: "20/06/2026" → Result: LocalDate(2026-06-20)
     */
    public static LocalDate parseDate(String dateString) throws DateTimeParseException {
        return LocalDate.parse(dateString, UI_DATE_FORMATTER);
    }

    /**
     * Convierte un String de fecha y hora a LocalDateTime.
     * Ejemplo: Input: "20/06/2026 14:30:45" → Result: LocalDateTime(2026-06-20T14:30:45)
     */
    public static LocalDateTime parseDateTime(String dateTimeString) throws DateTimeParseException {
        return LocalDateTime.parse(dateTimeString, UI_DATETIME_FORMATTER);
    }

    /**
     * Convierte un String flexible de fecha o fecha+hora a LocalDateTime.
     * Ejemplo 1: Input: "20/06/2026" → Result: LocalDateTime(2026-06-20T00:00:00)
     * Ejemplo 2: Input: "20/06/2026 14:30:45" → Result: LocalDateTime(2026-06-20T14:30:45)
     */
    public static LocalDateTime parseToLocalDateTime(String text) {
        try {
            TemporalAccessor parsed = FLEXIBLE_FORMATTER.parse(text);

            LocalTime localTime = parsed.isSupported(ChronoField.HOUR_OF_DAY)
                    ? LocalTime.from(parsed)
                    : LocalTime.MIDNIGHT;

            return LocalDate.from(parsed).atTime(localTime);

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Unsupported date format from UI: " + text, e
            );
        }
    }

    /**
     * Convierte un String en formato español completo a LocalDate.
     * Ejemplo: Input: "20 de junio de 2026" → Result: LocalDate(2026-06-20)
     */
    public static LocalDate parseStringFullCalendarSpanishDateToLocalDate(String dateString) {
        String cleaned = dateString.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy",
                new Locale("es", "ES"));
        return LocalDate.parse(cleaned, formatter);
    }

    /**
     * Convierte un LocalDate al formato español completo.
     * Ejemplo: Input: LocalDate(2026-06-20) → Result: "20 de junio de 2026"
     */
    public static String formatLocalDateToFullCalendarSpanishFormat(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy",
                new Locale("es", "ES"));
        return formatter.format(date);
    }

    /**
     * Convierte un LocalDate a String en formato dd/MM/yyyy.
     * Ejemplo: Input: LocalDate(2026-06-20) → Result: "20/06/2026"
     */
    public static String formatLocalDateToStringDdMmYyyy(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return formatter.format(date);
    }

    /**
     * Convierte un String en formato dd/MM/yyyy a LocalDate.
     * Ejemplo: Input: "20/06/2026" → Result: LocalDate(2026-06-20)
     * NOTA: Requiere exactamente dos dígitos para el día (ej: "05/06/2026" no "5/06/2026")
     */
    public static LocalDate parseStringDdMmYyyyToLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateString, formatter);
    }
}
