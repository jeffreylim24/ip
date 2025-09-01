package mumbo.task;

/**
 * Represents the types of tasks supported by Mumbo.
 * TODO: A simple task
 * DEADLINE: A task with a deadline
 * EVENT: A task with a start and end time
 */
public enum TaskType {
    TODO("T"), DEADLINE("D"), EVENT("E");

    /**
     * The tag used to represent the task type in storage or display.
     */
    private final String tag;

    /**
     * Constructs a TaskType with the specified tag.
     * @param tag the tag representing the task type
     */
    TaskType(String tag) {
        this.tag = tag;
    }

    /**
     * Returns the tag associated with this task type.
     * @return the tag string
     */
    public String tag() {
        return tag;
    }
}
