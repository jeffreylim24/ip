package mumbo.task;

/**
 * Mumbo.Todo class
 *
 * A type of Mumbo.Task that only has a description
 */

public class Todo extends Task {

    /**
     * Constructor for a Todo task
     * @param task the description of the task
     */
    public Todo(String task) {
        super(TaskType.TODO, task);
    }

    @Override
    public String toFormattedString() {
        return "T | " + (isDone ? "1" : "0") + " | " + task;
    }
}