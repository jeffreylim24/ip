package mumbo.task;

import java.time.LocalDateTime;

import mumbo.userinput.DateTimeUtil;

/**
 * Mumbo.Event class
 *
 * A type of class that has a description, a start and an end
 */

public class Event extends Task {
    protected LocalDateTime start;
    protected LocalDateTime end;

    /**
     * Creates an Event task
     * @param task a String depicting the task's description
     * @param start a LocalDateTime object that signifies the start of the event
     * @param end a LocalDateTime object that signifies the end of the event
     */
    public Event(String task, LocalDateTime start, LocalDateTime end) {
        super(TaskType.EVENT, task);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toFormattedString() {
        return "E | " + (isDone ? "1" : "0") + " | "
                + task + " | "
                + start + " | "
                + end;
    }

    @Override
    public String toString() {
        return super.toString()
                + " (from: " + DateTimeUtil.prettify(start)
                + " to: " + DateTimeUtil.prettify(end) + ")";
    }
}
