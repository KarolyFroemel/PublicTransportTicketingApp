package ptc.springframework.publictransportrest.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatterHelper {
    private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String parseDate(LocalDateTime date) {
            return date.format(DATE_FORMATTER);
    }

    public static String parseTimestamp(LocalDateTime timestamp) {
        return timestamp.format(DATE_TIME_FORMATTER);
    }

}
