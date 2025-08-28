public abstract class Task {
    protected String task;
    protected boolean isDone;
    protected final TaskType type;

    public Task(TaskType type, String task) {
        this.task = task;
        this.isDone = false;
        this.type = type;
    }

    public void mark(boolean x) {
        isDone = x;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public abstract String toFormattedString();

    @Override
    public String toString() {
        return "[" + type.tag() + "][" + (isDone ? "X" : " ") + "] " + task;
    }
}
