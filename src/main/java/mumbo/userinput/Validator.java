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
            throw new MumboException("Do be so kind as to specify which task.");
        }
        try {
            int n = Integer.parseInt(s.trim());
            if (n <= 0) {
                throw new MumboException("The index ought to be a positive integer (1, 2, 3, ...).");
            }
        } catch (NumberFormatException e) {
            throw new MumboException("That does not appear to be a number. For example: mark 2");
        }
    }

    /**
     * Validates that the integer s is within the range [min, max].
     * @param s the integer to validate
     */
    public static void validateInRange(int s, int min, int max) {
        // Handle out-of-range gracefully even when the list is empty (max can be < min).
        if (s > max || s < min) {
            throw new MumboException("I'm afraid that is out of range; you have only " + max + " task(s) in the list.");
        }
    }

    /**
     * Validates the format of a todo command.
     * @param s the description of the todo
     */
    public static void validateTodo(String s) {
        if (s == null || s.isBlank()) {
            throw new MumboException("I'm afraid the description cannot be empty.");
        }
    }

    /**
     * Validates the format of a deadline command.
     * @param s the description and deadline string
     */
    public static void validateDeadline(String s) {
        if (s == null || s.isBlank()) {
            throw new MumboException("I'm afraid the description cannot be empty.");
        }
        String[] segments = s.split("\\s*/by\\s*", 2);
        if (segments.length < 2 || segments[0].isBlank() || segments[1].isBlank()) {
            throw new MumboException("Pray specify its deadline with /by <deadline>.");
        }
    }

    /**
     * Validates the format of an event command.
     * @param s the description and event time range string
     */
    public static void validateEvent(String s) {
        if (s == null || s.isBlank()) {
            throw new MumboException("I’m afraid the description cannot be empty.");
        }
        String[] by = s.split("\\s*/from\\s*", 2);
        if (by.length < 2 || by[0].isBlank()) {
            throw new MumboException("Pray specify the event start with /from <start>.");
        }
        String[] range = by[1].split("\\s*/to\\s*", 2);
        if (range.length < 2 || range[0].isBlank() || range[1].isBlank()) {
            throw new MumboException("Pray specify the event end with /to <end>.");
        }
        try {
            LocalDateTime start = DateTimeUtil.parseDateTime(range[0]);
            LocalDateTime end = DateTimeUtil.parseDateTime(range[1]);
            if (end.isBefore(start)) {
                throw new MumboException("The event cannot conclude before it begins, I'm afraid.");
            }
        } catch (DateTimeParseException e) {
            throw new MumboException("That date appears to be invalid.\nPlease use one of the following formats:\n"
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
            throw new MumboException("Kindly type 'yes' or 'no'.");
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
            throw new MumboException("Do kindly specify a keyword to search for.");
        }
    }

    /**
     * Validates the tag command argument.
     * @param s the index and tag name string
     * @throws MumboException if the format is incorrect or index is invalid
     */
    public static void validateTag(String s) {
        if (s == null || s.isBlank()) {
            throw new MumboException("Do be so kind as to specify the task index and the tag name.");
        }
        String[] segments = s.split("\\s+", 2);
        if (segments.length < 2 || segments[0].isBlank() || segments[1].isBlank()) {
            throw new MumboException("Please specify both the task index and the tag name.");
        }
        try {
            validateInt(segments[0]);
        } catch (MumboException e) {
            throw new MumboException("The task index ought to be a positive integer (1, 2, 3, ...).");
        }
    }
}
