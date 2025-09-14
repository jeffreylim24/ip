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
    protected String tag;

    /**
     * Creates a task with its necessary details
     * @param type a TaskType enum
     * @param task a String depicting the task description
     */
    public Task(TaskType type, String task) {
        assert type != null : "Task type must not be null";
        assert task != null && !task.isBlank() : "Task description must not be null/blank";
        this.task = task;
        this.isDone = false;
        this.type = type;
        this.tag = null;
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
     * A getter method to get a Task's description
     * @return a String that represents the task's description
     */
    public String getDescription() {
        return this.task;
    }

    /**
     * Represents the task in a specific String format for saving onto the storage file, for easier readability
     * @return a String in a specified format
     */
    public abstract String toFormattedString();

    /**
     * A method to tag a task with a specific tag
     * @param tag a String that represents the tag to be added
     * @return the Task object itself, for method chaining
     */
    public Task tag(String tag) {
        assert tag != null && !tag.isBlank() : "Tag must not be null or blank";
        this.tag = tag;
        return this;
    }

    /**
     * A getter method to get a Task's tag
     * @return a String that represents the task's tag
     */
    public String getTag() {
        return this.tag;
    }

    @Override
    public String toString() {
        return "[" + type.tag() + "][" + (isDone ? "X" : " ") + "] " + task;
    }
}
