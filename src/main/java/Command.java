public enum Command {
    LIST, TODO, DEADLINE, EVENT, MARK, UNMARK, DELETE, HELP, BYE, UNKNOWN;

    public static Command from(String cmd) {
        if (cmd == null) return UNKNOWN;
        return switch (cmd.toLowerCase()) {
            case "list" -> LIST;
            case "todo" -> TODO;
            case "deadline" -> DEADLINE;
            case "event" -> EVENT;
            case "mark" -> MARK;
            case "unmark" -> UNMARK;
            case "delete" -> DELETE;
            case "help" -> HELP;
            case "bye" -> BYE;
            default -> UNKNOWN;
        };
    }
}
