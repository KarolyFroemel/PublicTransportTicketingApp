package ptc.springframework.publictransportrest.testdata;

import java.time.LocalDateTime;

public class DateTestData {

    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }

    public static LocalDateTime getYesterdayLocalDateTime() {
        return LocalDateTime.now().minusDays(1L);
    }

    public static LocalDateTime getTomorrowLocalDateTime() {
        return LocalDateTime.now().plusDays(1L);
    }

    public static LocalDateTime getPlusOneYearLocalDateTime() {
        return LocalDateTime.now().plusYears(1L);
    }

    public static LocalDateTime getPlusOneMonthLocalDateTime() {
        return LocalDateTime.now().plusMonths(1L);
    }
}
