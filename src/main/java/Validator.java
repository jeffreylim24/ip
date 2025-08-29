public class Validator {
    public Validator() {}

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

    public static void validateInRange(int s, int min, int max) {
        if (s > max && s < min) {
            throw new MumboException("That's out of range! You only have " + max + " tasks in the list.");
        }
    }

    public static void validateTodo(String s) {
        if (s == null || s.isBlank()) {
            throw new MumboException("Uh Oh! The description cannot be empty.");
        }
    }

    public static void validateDeadline(String s) {
        if (s == null || s.isBlank()) {
            throw new MumboException("Uh Oh! The description cannot be empty.");
        }
        String[] segments = s.split("\\s*/by\\s*", 2);
        if (segments.length < 2 || segments[0].isBlank() || segments[1].isBlank()) {
            throw new MumboException("Please specify its deadline with /by <deadline>");
        }
    }

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
    }

    public static boolean validateYesNo(String s) {
        String s1 = s.trim().toLowerCase();
        if (!(s1.equals("y") || s1.equals("n") || s1.equals("yes") || s1.equals("no"))) {
            throw new MumboException("Please type 'yes or 'no'.");
        }
        return s1.equals("y") || s1.equals("yes");
    }
}
