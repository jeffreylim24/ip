public class Deadline extends Task {
    protected String deadline;

    public Deadline(String task, String deadline) {
        super(TaskType.DEADLINE, task);
        this.deadline = deadline;
    }

    @Override
    public String toFormattedString() {
        return "D | " + (isDone ? "1" : "0") + " | " + task + " | " + this.deadline;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + deadline + ")";
    }
}
