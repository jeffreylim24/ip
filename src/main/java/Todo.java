public class Todo extends Task {
    public Todo(String task) {
        super(TaskType.TODO, task);
    }

    @Override
    public String toFormattedString() {
        return "T | " + (isDone ? "1" : "0") + " | " + task;
    }
}