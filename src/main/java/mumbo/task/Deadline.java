package mumbo.task;

import java.time.LocalDateTime;

import mumbo.userinput.DateTimeUtil;

/**
 * Mumbo.Deadline class
 *
 * A type of task that has a description and a deadline
 */

public class Deadline extends Task {
    protected LocalDateTime deadline;

    /**
     * Creates a deadline task with the specified characteristics
     * @param task a String containing the task description
     * @param deadline a String specifying its deadline
     */
    public Deadline(String task, LocalDateTime deadline) {
        super(TaskType.DEADLINE, task);
        this.deadline = deadline;
    }

    @Override
    public String toFormattedString() {
        return "D | " + (isDone ? "1" : "0") + " | " + task + " | " + deadline;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + DateTimeUtil.prettify(deadline) + ")";
    }
}
