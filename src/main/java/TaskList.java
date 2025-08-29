import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates a list of tasks and provides operations such as add, delete, mark, and clear.
 */

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> existing) {
        this.tasks = new ArrayList<>(existing);
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int i) {
        return tasks.get(i);
    }

    public List<Task> asList() {
        return new ArrayList<>(tasks);
    }

    public Task add(Task t) {
        tasks.add(t);
        return t;
    }

    public Task delete(int index1Based) {
        return tasks.remove(index1Based - 1);
    }

    public Task mark(int index1Based, boolean done) {
        Task t = tasks.get(index1Based - 1);
        t.mark(done);
        return t;
    }

    public void clear() {
        tasks.clear();
    }
}
