package mumbo.ui;

import mumbo.task.Task;
import mumbo.task.TaskList;

/**
 * Handles interactions with the user such as displaying messages and reading commands.
 * Responsible for input/output logic and message formatting.
 */
public class Ui {

    /**
     * Gets the welcome message.
     * @return the formatted welcome message
     */
    public String getWelcomeMessage() {
        return "Hello, I'm Mumbo!\nWhat can I do for you?";
    }

    /**
     * Gets the goodbye message.
     * @return the formatted goodbye message
     */
    public String getByeMessage() {
        return "Bye! Hope to see you again soon!";
    }

    /**
     * Gets the message for when a task is added.
     * @param t The task that was added.
     * @param size The current number of tasks in the list.
     * @return the formatted message
     */
    public String getAddedMessage(Task t, int size) {
        assert t != null : "Task to add must not be null";
        assert size >= 0 : "Task count must be non-negative";
        return "Got it. I've added this task:\n  " + t + "\nNow you have " + size + " tasks in the list.";
    }

    /**
     * Gets the message for when a task is deleted.
     * @param t The task that was deleted.
     * @param size The current number of tasks in the list.
     * @return the formatted message
     */
    public String getDeletedMessage(Task t, int size) {
        assert t != null : "Deleted task must not be null";
        assert size >= 0 : "Task count must be non-negative";
        return "Noted. I've removed this task:\n  " + t + "\nNow you have " + size + " tasks in the list.";
    }

    /**
     * Gets the message for when a task is marked/unmarked.
     * @param t The task that was marked or unmarked.
     * @param done True if the task was marked as done, false if unmarked.
     * @return the formatted message
     */
    public String getMarkedMessage(Task t, boolean done) {
        assert t != null : "Marked task must not be null";
        String action = done ? "Nice! I've marked this task as done:" : "OK, I've marked this task as not done yet:";
        return action + "\n  " + t;
    }

    /**
     * Gets the formatted list of tasks.
     * @param tasks The list of tasks to be displayed.
     * @return the formatted list message
     */
    public String getListMessage(TaskList tasks) {
        assert tasks != null : "TaskList must not be null";
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
     * Gets the message for when tasks are cleared.
     * @return the formatted clear message
     */
    public String getClearMessage() {
        return "All tasks have been cleared!";
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
     * Gets the message for find results.
     * @param tasks a TaskList of matching tasks
     * @return the formatted find message
     */
    public String getFindMessage(TaskList tasks) {
        assert tasks != null : "TaskList must not be null";
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
     * Gets the help message listing all available commands.
     * @return the formatted help message
     */
    public String getHelpMessage() {
        String bullet = "\u2022 ";
        return "Here are the available commands:\n"
                + bullet + "list - show all tasks\n"
                + bullet + "todo <description> - add a todo task\n"
                + bullet + "deadline <description> /by <date> - add a deadline\n"
                + bullet + "event <description> /from <start> /to <end> - add an event\n"
                + bullet + "mark <number> - mark task as done\n"
                + bullet + "unmark <number> - mark task as not done\n"
                + bullet + "delete <number> - delete a task\n"
                + bullet + "find <keyword> - find tasks containing keyword\n"
                + bullet + "findtag <tag> - find tasks by tag\n"
                + bullet + "tag <index> <tag> - tags a task with a tag\n"
                + bullet + "clear - clear all tasks\n"
                + bullet + "bye - exit the program";
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

    /**
     * Gets the message for when a task is tagged.
     * @param t the task that was tagged
     * @param tag the tag that was added
     * @return the formatted tag message
     */
    public String getTaggedMessage(Task t, String tag) {
        assert t != null : "Tagged task must not be null";
        assert tag != null && !tag.isBlank() : "Tag must not be null or blank";
        return "Got it. I've tagged this task accordingly:\n  " + t + " Tag: " + t.getTag();
    }
}
