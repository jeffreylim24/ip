package mumbo.task;

/**
 * A parent task category that all types of tasks fall under
 * Contains minimally a description, a type, and its completion status
 * Handles marking & unmarking of tasks
 */
public abstract class Task {
    protected String task;
    protected boolean isDone;
    protected final TaskType type;

    /**
     * Creates a task with its necessary details
     * @param type a TaskType enum
     * @param task a String depicting the task description
     */
    public Task(TaskType type, String task) {
        this.task = task;
        this.isDone = false;
        this.type = type;
    }

    /**
     * A method to mark or unmark the task as done, depending on its input
     * @param x a boolean to mark or unmark the task
     */
    public void mark(boolean x) {
        isDone = x;
    }

    /**
     * A getter method to check if a particular task is done.
     * @return a boolean that represents a task's completion status
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Represents the task in a specific String format for saving onto the storage file, for easier readability
     * @return a String in a specified format
     */
    public abstract String toFormattedString();

    @Override
    public String toString() {
        return "[" + type.tag() + "][" + (isDone ? "X" : " ") + "] " + task;
    }
}
