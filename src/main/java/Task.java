public class Task {
    private String task;
    private boolean status;

    public Task(String task) {
        this.task = task;
        this.status = false;
    }

    public void mark(boolean x) {
        status = x;
    }

    public boolean isDone() {
        return this.status;
    }

    @Override
    public String toString() {
        return "[" + (status ? "X" : " ") + "] " + task;
    }
}
