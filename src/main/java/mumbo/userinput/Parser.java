package mumbo.userinput;

import mumbo.command.Command;
import mumbo.exception.MumboException;

/**
 * Parsers raw user input into an acceptable format for internal processing
 */
public class Parser {

    /**
     * Parsing method for most inputs
     * @param raw a String of the raw user input
     * @return returns a ParsedInput object which contains a Command and its set of arguments
     */
    public static ParsedInput parse(String raw) {
        if (raw == null || raw.isBlank()) {
            return new ParsedInput(Command.UNKNOWN);
        }
        String[] parts = raw.trim().split("\\s+", 2);
        Command cmd = Command.from(parts[0]);
        String arg = parts.length > 1 ? parts[1].trim() : null;

        switch (cmd) {
        case TODO:
            return parseTodo(arg);
        case DEADLINE:
            return parseDeadline(arg);
        case EVENT:
            return parseEvent(arg);
        case MARK:
        case UNMARK:
        case DELETE:
            return parseIntCommand(cmd, arg);
        case FIND:
            return parseFind(arg);
        case LIST:
        case CLEAR:
        case HELP:
        case BYE:
            return new ParsedInput(cmd);
        default:
            return new ParsedInput(Command.UNKNOWN);
        }
    }

    private static ParsedInput parseTodo(String arg) {
        try {
            Validator.validateTodo(arg);
        } catch (MumboException e) {
            return new ParsedInput(Command.ERROR, e.getMessage());
        }
        assert arg != null && !arg.isBlank() : "Todo argument must not be null/blank after validation";
        return new ParsedInput(Command.TODO, arg);
    }

    private static ParsedInput parseDeadline(String arg) {
        try {
            Validator.validateDeadline(arg);
        } catch (MumboException e) {
            return new ParsedInput(Command.ERROR, e.getMessage());
        }
        String[] segments = arg.split("\\s*/by\\s*", 2);
        assert segments.length == 2 : "Deadline must split into description and /by part";
        assert !segments[0].trim().isEmpty() && !segments[1].trim().isEmpty()
                : "Deadline description and /by must be non-blank";
        return new ParsedInput(Command.DEADLINE, segments[0].trim(), segments[1].trim());
    }

    private static ParsedInput parseEvent(String arg) {
        try {
            Validator.validateEvent(arg);
        } catch (MumboException e) {
            return new ParsedInput(Command.ERROR, e.getMessage());
        }
        String[] by = arg.split("\\s*/from\\s*", 2);
        String[] range = by[1].split("\\s*/to\\s*", 2);
        assert by.length == 2 : "Event must contain /from";
        assert range.length == 2 : "Event must contain /to";
        assert !by[0].trim().isEmpty() && !range[0].trim().isEmpty() && !range[1].trim().isEmpty()
                : "Event parts must be non-blank";
        return new ParsedInput(Command.EVENT, by[0].trim(), range[0].trim(), range[1].trim());
    }

    private static ParsedInput parseIntCommand(Command cmd, String arg) {
        try {
            Validator.validateInt(arg);
        } catch (MumboException e) {
            return new ParsedInput(Command.ERROR, e.getMessage());
        }
        return new ParsedInput(cmd, arg);
    }

    private static ParsedInput parseFind(String arg) {
        try {
            Validator.validateFind(arg);
        } catch (MumboException e) {
            return new ParsedInput(Command.ERROR, e.getMessage());
        }
        assert arg != null && !arg.isBlank() : "Find keyword must not be null/blank after validation";
        return new ParsedInput(Command.FIND, arg);
    }
}
