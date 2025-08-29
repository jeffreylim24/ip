import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateTimeUtil {

    // Accept a few common formats:
    // 1) yyyy-MM-dd
    // 2) yyyy-MM-dd HH:mm
    // 3) dd-MM-yyyy
    // 4) dd-MM-yyyy HH:mm
    private static final DateTimeFormatter[] CANDIDATES = new DateTimeFormatter[] {
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    };

    public static LocalDateTime parseDateTime(String s) {
        s = s.trim();
        for (DateTimeFormatter f : CANDIDATES) {
            try {
                // Try as LocalDateTime
                return LocalDateTime.parse(s, f);
            } catch (Exception ignore) {
                try {
                    // Try as LocalDate next
                    LocalDate d = LocalDate.parse(s, f);
                    return d.atStartOfDay();
                } catch (Exception ignore2) {}
            }
        }
        throw new DateTimeParseException("Unrecognised date/time: " + s, s, 0);
    }

    // Pretty printing used in toString()
    private static final DateTimeFormatter PRETTY_DATE_TIME =
            DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm", Locale.ENGLISH);

    private static final DateTimeFormatter PRETTY_DATE =
            DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH);

    public static String prettify(LocalDateTime dt) {
        // If time is midnight, we assume user only typed a date
        return dt.toLocalTime().equals(LocalTime.MIDNIGHT)
                ? dt.toLocalDate().format(PRETTY_DATE)
                : dt.format(PRETTY_DATE_TIME);
    }

    // For Storage (stable, unambiguous)
    public static String iso(LocalDateTime dt) {
        return dt.toString();
    }

    public static LocalDateTime parseIso(String s) {
        return LocalDateTime.parse(s);
    }
}
