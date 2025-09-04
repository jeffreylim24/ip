package mumbo.ui;

import java.util.Scanner;

import mumbo.task.Task;
import mumbo.task.TaskList;

/**
 * Handles interactions with the user such as displaying messages and reading commands.
 * Responsible for input/output logic and message formatting.
 */
public class Ui {
    private static final String LINE = "____________________________________";
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Displays the welcome message when the application starts.
     */
    public void showWelcome() {
        System.out.println(getWelcomeMessage());
    }

    /**
     * Gets the welcome message.
     * @return the formatted welcome message
     */
    public String getWelcomeMessage() {
        return "Hello, I'm Mumbo!\nWhat can I do for you?";
    }

    /**
     * Displays the goodbye message when the application exits.
     */
    public void showBye() {
        System.out.println(getByeMessage());
    }

    /**
     * Gets the goodbye message.
     * @return the formatted goodbye message
     */
    public String getByeMessage() {
        return "Bye! Hope to see you again soon!";
    }

    /**
     * Displays a line separator for better readability in the console.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Displays an error message to the user.
     * @param message The error message to be displayed.
     */
    public void showError(String message) {
        System.out.println(LINE + "\n" + message + "\n" + LINE);
    }

    /**
     * Reads a command input from the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays a message confirming that a task has been added, along with the current number of tasks.
     * @param t The task that was added.
     * @param size The current number of tasks in the list.
     */
    public void showAdded(Task t, int size) {
        System.out.println(getAddedMessage(t, size));
    }

    /**
     * Gets the message for when a task is added.
     * @param t The task that was added.
     * @param size The current number of tasks in the list.
     * @return the formatted message
     */
    public String getAddedMessage(Task t, int size) {
        return "Got it. I've added this task:\n  " + t + "\nNow you have " + size + " tasks in the list.";
    }

    /**
     * Displays a message confirming that a task has been deleted, along with the current number of tasks.
     * @param t The task that was deleted.
     * @param size The current number of tasks in the list.
     */
    public void showDeleted(Task t, int size) {
        System.out.println(getDeletedMessage(t, size));
    }

    /**
     * Gets the message for when a task is deleted.
     * @param t The task that was deleted.
     * @param size The current number of tasks in the list.
     * @return the formatted message
     */
    public String getDeletedMessage(Task t, int size) {
        return "Noted. I've removed this task:\n  " + t + "\nNow you have " + size + " tasks in the list.";
    }

    /**
     * Displays a message confirming that a task has been marked or unmarked.
     * @param t The task that was marked or unmarked.
     * @param done True if the task was marked as done, false if unmarked.
     */
    public void showMarked(Task t, boolean done) {
        System.out.println(getMarkedMessage(t, done));
    }

    /**
     * Gets the message for when a task is marked/unmarked.
     * @param t The task that was marked or unmarked.
     * @param done True if the task was marked as done, false if unmarked.
     * @return the formatted message
     */
    public String getMarkedMessage(Task t, boolean done) {
        String action = done ? "Nice! I've marked this task as done:" : "OK, I've marked this task as not done yet:";
        return action + "\n  " + t;
    }

    /**
     * Displays the list of tasks to the user.
     * @param tasks The list of tasks to be displayed.
     */
    public void showList(TaskList tasks) {
        System.out.println(getListMessage(tasks));
    }

    /**
     * Gets the formatted list of tasks.
     * @param tasks The list of tasks to be displayed.
     * @return the formatted list message
     */
    public String getListMessage(TaskList tasks) {
        if (tasks.isEmpty()) {
            return "You have no tasks in your list.";
        } else {
            StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                sb.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
            }
            return sb.toString().trim();
        }
    }

    /**
     * Displays a message confirming that all tasks have been cleared.
     */
    public void showClear() {
        System.out.println(getClearMessage());
    }

    /**
     * Gets the message for when tasks are cleared.
     * @return the formatted clear message
     */
    public String getClearMessage() {
        return "All tasks have been cleared!";
    }

    /**
     * Asks the user if they want to clear the saved tasks before exiting.
     */
    public void queryClearCache() {
        System.out.println(LINE);
        System.out.println("One last thing before you go! Do you want to clear your saved tasks? (Y/N)");
        System.out.println(LINE);
    }

    /**
     * Gets the query message for clearing cache before exit.
     * @param taskCount The number of tasks in the list.
     * @return the formatted query message
     */
    public String getClearCacheQuery(int taskCount) {
        return "You have " + taskCount + " task(s) in your list.\n"
                + "Do you want to clear your tasks before leaving?\n"
                + "Type 'yes' or 'no' (or 'y' or 'n').";
    }

    /**
     * Gets the message for when tasks are cleared on exit.
     * @return the formatted message
     */
    public String getClearedOnExitMessage() {
        return "All tasks have been cleared!\nBye! Hope to see you again soon!";
    }

    /**
     * Displays the matching tasks the user searched for
     * @param tasks a TaskList of matching tasks
     */
    public void showFind(TaskList tasks) {
        System.out.println(getFindMessage(tasks));
    }

    /**
     * Gets the message for find results.
     * @param tasks a TaskList of matching tasks
     * @return the formatted find message
     */
    public String getFindMessage(TaskList tasks) {
        if (tasks.isEmpty()) {
            return "No matching tasks found.";
        } else {
            StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                sb.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
            }
            return sb.toString().trim();
        }
    }

    /**
     * Displays the help message listing all available commands.
     */
    public void showHelp() {
        System.out.println(getHelpMessage());
    }

    /**
     * Gets the help message listing all available commands.
     * @return the formatted help message
     */
    public String getHelpMessage() {
        return "Here are the available commands:\n"
                + "• list - show all tasks\n"
                + "• todo <description> - add a todo task\n"
                + "• deadline <description> /by <date> - add a deadline\n"
                + "• event <description> /from <start> /to <end> - add an event\n"
                + "• mark <number> - mark task as done\n"
                + "• unmark <number> - mark task as not done\n"
                + "• delete <number> - delete a task\n"
                + "• find <keyword> - find tasks containing keyword\n"
                + "• clear - clear all tasks\n"
                + "• bye - exit the program";
    }

    /**
     * Gets the error message for invalid date format.
     * @return the formatted error message
     */
    public String getDateFormatErrorMessage() {
        return "Invalid date format!\nPlease use one of the following formats:\n"
                + "1) yyyy/MM/dd\n"
                + "2) yyyy/MM/dd HH:mm\n"
                + "3) dd/MM/yyyy\n"
                + "4) dd/MM/yyyy HH:mm";
    }

    /**
     * Gets the error message for yes/no validation.
     * @return the formatted error message
     */
    public String getYesNoErrorMessage() {
        return "Please type 'yes' or 'no' (or 'y' or 'n').";
    }
}
