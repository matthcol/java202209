package time;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

public class TestTime {

    @Test
    void historyOfTime() {
        // how to differ two types with same name
        //        java.util.Date oldschoolDate;
        //        java.sql.Date jdbcDate;
        Date oldschoolDate = new Date(); // java 1.0
        System.out.println(oldschoolDate);

        Calendar weirdDate = Calendar.getInstance(); // java 1.1
        System.out.println(weirdDate);
        System.out.println(weirdDate.getClass());
        // in 99.9999 % cases :
        assertSame(GregorianCalendar.class, weirdDate.getClass());

        // Modern types
        LocalDate modernDate = LocalDate.now();  // year, month, date
        System.out.println(modernDate);

        LocalDateTime trueDateTime = LocalDateTime.now(); // year, month, day, hour, min, sec, ...
        System.out.println(trueDateTime);

        LocalTime time = LocalTime.now();
        System.out.println(time);

        // With TZ
        ZonedDateTime hereDateTime = ZonedDateTime.now();
        System.out.println(hereDateTime);

        ZoneId tzHawai = ZoneId.of("Pacific/Honolulu");
        ZonedDateTime hawaiDateTime = ZonedDateTime.now(tzHawai);
        System.out.println(hawaiDateTime);
        LocalDate localDateHawai = hawaiDateTime.toLocalDate();
        LocalTime localTimeHawai = hawaiDateTime.toLocalTime();
        System.out.println("Date Hawai: " + localDateHawai);
        System.out.println("Time Hawai: " + localTimeHawai);

        ZoneId tzParis = ZoneId.of("Europe/Paris");
        ZonedDateTime parisDt = ZonedDateTime.of(trueDateTime, tzParis);
        System.out.println(parisDt);
    }

    @Test
    void testMakeFromParts() {
        LocalDateTime dt = LocalDateTime.of(1999, 12, 31, 23, 59);
        LocalDate d = LocalDate.of(1998, 7, 12);
    }

    @Test
    void testParsingISO() {
        String isoDateStr = "1998-07-12";
        LocalDate date = LocalDate.parse(isoDateStr);
        assertAll(
                () -> assertEquals(1998, date.getYear()),
                () -> assertEquals(Month.JULY, date.getMonth()),
                () -> assertEquals(7, date.getMonth().getValue()),
                () -> assertEquals(12, date.getDayOfMonth()));
        System.out.println(date);
        System.out.println(date.getMonth().getValue());
    }


    @ParameterizedTest
    @ValueSource(strings={"12/07/2022", "12/07/1998", "29/02/2000"})
    @NullSource
    void testParameterized(String frDateStr) {
        System.out.println(frDateStr);
    }

    @ParameterizedTest
    @MethodSource("time.DataSource#frenchDateSource")
    void testParsingFrench(String frDateStr,
                           int expectedYear,
                           int expectedMonth,
                           int expectedDayOfMonth)
    {
        DateTimeFormatter frFormat = DateTimeFormatter.ofPattern("d/MM/y");
        LocalDate date = LocalDate.parse(frDateStr,frFormat);
        assertAll(
                () -> assertEquals(expectedYear, date.getYear()),
                () -> assertEquals(expectedMonth, date.getMonth().getValue()),
                () -> assertEquals(expectedDayOfMonth, date.getDayOfMonth()));
    }

    @Test
    void testComputeTime() {
        LocalDateTime dt = LocalDateTime.now();
        LocalDateTime dt2 = dt.plusDays(40);
        System.out.println(dt2);
        Duration delta = Duration.ofDays(3)
                .plus(Duration.ofHours(5));
        LocalDateTime dt3 = dt2.plus(delta);
        System.out.println(dt3);
        Duration delta2 = Duration.parse("P2DT7H");
        LocalDateTime dt4 = dt3.plus(delta);
        System.out.println(dt4);
    }
}
