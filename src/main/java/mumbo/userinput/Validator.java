package mumbo.userinput;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import mumbo.exception.MumboException;

/**
 * Validator class to validate user input after parsing.
 * Includes methods to validate integers, ranges, todo, deadline, event formats,
 * and yes/no responses.
 */
public class Validator {

    /**
     * Validates that the input string represents a positive integer.
     * @param s the input string to validate
     * @throws MumboException if the input is null, blank, not a number, or not a positive integer
     */
    public static void validateInt(String s) {
        if (s == null || s.isBlank()) {
            throw new MumboException("Please specify which task.");
        }
        try {
            int n = Integer.parseInt(s.trim());
            if (n <= 0) {
                throw new MumboException("Index must be a positive integer (1, 2, 3, ...).");
            }
        } catch (NumberFormatException e) {
            throw new MumboException("That doesn't look like a number. Try something like: mark 2");
        }
    }

    /**
     * Validates that the integer s is within the range [min, max].
     * @param s the integer to validate
     */
    public static void validateInRange(int s, int min, int max) {
        // Handle out-of-range gracefully even when the list is empty (max can be < min).
        if (s > max || s < min) {
            throw new MumboException("That's out of range! You only have " + max + " tasks in the list.");
        }
    }

    /**
     * Validates the format of a todo command.
     * @param s the description of the todo
     */
    public static void validateTodo(String s) {
        if (s == null || s.isBlank()) {
            throw new MumboException("Uh Oh! The description cannot be empty.");
        }
    }

    /**
     * Validates the format of a deadline command.
     * @param s the description and deadline string
     */
    public static void validateDeadline(String s) {
        if (s == null || s.isBlank()) {
            throw new MumboException("Uh Oh! The description cannot be empty.");
        }
        String[] segments = s.split("\\s*/by\\s*", 2);
        if (segments.length < 2 || segments[0].isBlank() || segments[1].isBlank()) {
            throw new MumboException("Please specify its deadline with /by <deadline>");
        }
    }

    /**
     * Validates the format of an event command.
     * @param s the description and event time range string
     */
    public static void validateEvent(String s) {
        if (s == null || s.isBlank()) {
            throw new MumboException("Uh Oh! The description cannot be empty.");
        }
        String[] by = s.split("\\s*/from\\s*", 2);
        if (by.length < 2 || by[0].isBlank()) {
            throw new MumboException("Please specify event start with /from <start>");
        }
        String[] range = by[1].split("\\s*/to\\s*", 2);
        if (range.length < 2 || range[0].isBlank() || range[1].isBlank()) {
            throw new MumboException("Please specify event end with /to <end>");
        }
        try {
            LocalDateTime start = DateTimeUtil.parseDateTime(range[0]);
            LocalDateTime end = DateTimeUtil.parseDateTime(range[1]);
            if (end.isBefore(start)) {
                throw new MumboException("Oh no! The event can't end before it even starts!");
            }
        } catch (DateTimeParseException e) {
            throw new MumboException(e.getMessage() + "\nPlease use one of the following formats:\n"
                    + "1) yyyy/MM/dd\n"
                    + "2) yyyy/MM/dd HH:mm\n"
                    + "3) dd/MM/yyyy\n"
                    + "4) dd/MM/yyyy HH:mm");
        }
    }

    /**
     * Validates a yes/no response.
     * @param s the input string to validate
     * @return true if the response is affirmative (yes), false if negative (no)
     * @throws MumboException if the input is not a valid yes/no response
     */
    public static boolean validateYesNo(String s) {
        assert s != null : "Yes/No input must not be null";
        String s1 = s.trim().toLowerCase();
        if (!(s1.equals("y") || s1.equals("n") || s1.equals("yes") || s1.equals("no"))) {
            throw new MumboException("Please type 'yes or 'no'.");
        }
        return s1.equals("y") || s1.equals("yes");
    }

    /**
     * Validates the find command argument.
     * @param s the keyword to search for
     * @throws MumboException if the keyword is null or blank
     */
    public static void validateFind(String s) {
        if (s == null || s.isBlank()) {
            throw new MumboException("Please specify a keyword to search for.");
        }
    }
}
