public class Event extends Task {
    protected String start;
    protected String end;

    public Event(String task, String start, String end) {
        super(TaskType.EVENT, task);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + start + " to: " + end + ")";
    }
}
