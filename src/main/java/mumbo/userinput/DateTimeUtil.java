package mumbo.userinput;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Utility class for parsing and formatting date/time inputs.
 */
public class DateTimeUtil {

    private static final DateTimeFormatter PRETTY_DATE =
            DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH);

    private static final DateTimeFormatter PRETTY_DATE_TIME =
            DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm", Locale.ENGLISH);

    private static final DateTimeFormatter[] CANDIDATES = new DateTimeFormatter[] {
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    };

    /**
     * Parse a date/time string into a LocalDateTime.
     * Accepts a few common formats (see CANDIDATES).
     * @param s the input string
     * @return the parsed LocalDateTime
     */
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
                } catch (Exception ignore2) {
                    continue;
                }
            }
        }
        throw new DateTimeParseException("Unrecognised date/time: " + s, s, 0);
    }



    /**
     * Prettify a LocalDateTime for display to the user.
     * @param dt the date/time
     * @return the pretty string
     */
    public static String prettify(LocalDateTime dt) {
        // If time is midnight, we assume user only typed a date
        return dt.toLocalTime().equals(LocalTime.MIDNIGHT)
                ? dt.toLocalDate().format(PRETTY_DATE)
                : dt.format(PRETTY_DATE_TIME);
    }

    /**
     * Format a LocalDateTime in ISO format (used by Mumbo.Storage).
     * @param dt the date/time
     * @return the ISO string
     */
    public static String iso(LocalDateTime dt) {
        return dt.toString();
    }

    /**
     * Parse a date/time in ISO format (used by Mumbo.Storage).
     * @param s the ISO string
     * @return the parsed LocalDateTime
    */
    public static LocalDateTime parseIso(String s) {
        return LocalDateTime.parse(s);
    }
}
