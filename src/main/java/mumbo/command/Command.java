package mumbo.command;

/**
 * An enum of commands recognised by Mumbo
 */
public enum Command {
    LIST, TODO, DEADLINE, EVENT, MARK, UNMARK, DELETE, CLEAR, HELP, BYE, UNKNOWN, ERROR;

    /**
     * Converts a string input into its respective command enum
     * @param cmd a String input from the user
     * @return its respective command enum
     */
    public static Command from(String cmd) {
        if (cmd == null) {
            return UNKNOWN;
        }
        return switch (cmd.toLowerCase()) {
        case "list" -> LIST;
        case "todo" -> TODO;
        case "deadline" -> DEADLINE;
        case "event" -> EVENT;
        case "mark" -> MARK;
        case "unmark" -> UNMARK;
        case "delete" -> DELETE;
        case "clear" -> CLEAR;
        case "help" -> HELP;
        case "bye" -> BYE;
        default -> UNKNOWN;
        };
    }
}
