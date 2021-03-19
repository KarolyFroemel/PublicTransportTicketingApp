package ptc.springframework.publictransportrest.helper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatterHelper {
    private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String parseDate(LocalDate date) {
            return date.format(DATE_FORMATTER);
    }

    public static String parseTimestamp(LocalDateTime timestamp) {
        return timestamp.format(DATE_TIME_FORMATTER);
    }

    public static LocalDateTime parseStringToStartOfDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return localDate.atStartOfDay();
    }

    public static LocalDateTime parseStringToEndOfDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return localDate.atTime(23,59,59,99);
    }



}
