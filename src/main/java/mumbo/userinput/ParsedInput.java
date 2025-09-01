package mumbo.userinput;

import mumbo.command.Command;

/**
 * Mumbo.ParsedInput class
 *
 * An input that has been separated into its command and arguments
 */

public class ParsedInput {
    public final Command command;
    public final String[] args;

    /**
     * Creates a parsed input with the specified characteristics
     * @param command an Enum of different commands accepted by Mumbo
     * @param args a set of organised strings for a number of arguments
     */
    ParsedInput(Command command, String... args) {
        this.command = command;
        this.args = args == null ? new String[0] : args;
    }

    public Command getCommand() {
        return command;
    }

    public String getArgX(int index) {
        if (index < 1 || index > args.length) {
            return null;
        }
        return args[index - 1];
    }
}
