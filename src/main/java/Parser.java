class ParsedInput {
    final Command command;
    final String[] args;

    ParsedInput(Command command, String... args) {
        this.command = command;
        this.args = args == null ? new String[0] : args;
    }
}

public class Parser {
    public static ParsedInput parse(String raw) {
        if (raw == null || raw.isBlank()) {
            return new ParsedInput(Command.UNKNOWN);
        }
        String[] parts = raw.trim().split("\\s+", 2);
        Command cmd = Command.from(parts[0]);
        String arg = parts.length > 1 ? parts[1].trim() : null;

        switch (cmd) {
        case TODO:
            try {
                Validator.validateTodo(arg);
            } catch (MumboException e) {
                return new ParsedInput(Command.ERROR, e.getMessage());
            }

            return new ParsedInput(cmd, arg);

        case DEADLINE:
            try {
                Validator.validateDeadline(arg);
            } catch (MumboException e) {
                return new ParsedInput(Command.ERROR, e.getMessage());
            }

            String[] segments = arg.split("\\s*/by\\s*", 2);
            return new ParsedInput(cmd, segments[0].trim(), segments[1].trim());

        case EVENT:
            try {
                Validator.validateEvent(arg);
            } catch (MumboException e) {
                return new ParsedInput(Command.ERROR, e.getMessage());
            }

            String[] by = arg.split("\\s*/from\\s*", 2);
            String[] range = by[1].split("\\s*/to\\s*", 2);
            return new ParsedInput(cmd, by[0].trim(), range[0].trim(), range[1].trim());

        case MARK:
        case UNMARK:
        case DELETE:
            try {
                Validator.validateInt(arg);
            } catch (MumboException e) {
                return new ParsedInput(Command.ERROR, e.getMessage());
            }
            return new ParsedInput(cmd, arg);

        case LIST:
        case CLEAR:
        case HELP:
        case BYE:
            return new ParsedInput(cmd);

        default:
            return new ParsedInput(Command.UNKNOWN);
        }
    }

    public static boolean parseYesNo(String s) {
        String s1 = s.trim().toLowerCase();
        return s1.equals("y") || s1.equals("yes");
    }
}
