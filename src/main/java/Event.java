import java.time.LocalDateTime;

/**
 * Event class
 *
 * A type of class that has a description, a start and an end
 */

public class Event extends Task {
    protected LocalDateTime start;
    protected LocalDateTime end;

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
