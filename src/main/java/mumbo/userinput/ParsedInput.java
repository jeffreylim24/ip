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

    /**
     * Gets the command of this parsed input
     * @return the command of this parsed input
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Gets the argument at the specified index (1-based)
     * @param index the index of the argument to get (1-based)
     * @return the argument at the specified index, or null if the index is out of bounds
     */
    public String getArgX(int index) {
        if (index < 1 || index > args.length) {
            return null;
        }
        return args[index - 1];
    }
}
