package mumbo.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates a list of tasks and provides operations such as add, delete, mark, and clear.
 */

public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates a TaskList object, which is a list of tasks with additional methods
     * @param existing a List of Task objects
     */
    public TaskList(List<Task> existing) {
        this.tasks = new ArrayList<>(existing);
    }

    /**
     * @return Returns the size of the list of tasks
     */
    public int size() {
        return tasks.size();
    }

    /**
     * A method to get a task at a specified index
     * @param i an int representing the index of the task
     * @return returns specified task
     */
    public Task get(int i) {
        return tasks.get(i);
    }

    /**
     * Converts itself into a List object
     * @return returns a List of Tasks
     */
    public List<Task> asList() {
        return new ArrayList<>(tasks);
    }

    /**
     * Adds a task to the list
     * @param t Task to be added
     * @return returns the task for later use
     */
    public Task add(Task t) {
        tasks.add(t);
        return t;
    }

    /**
     * Deletes a specified task from the list
     * @param index1Based a 1 based integer index
     * @return returns the deleted task
     */
    public Task delete(int index1Based) {
        return tasks.remove(index1Based - 1);
    }

    /**
     * Marks/unmarks a specified task from the list
     * @param index1Based a 1 based integer index
     * @param done boolean to determine whether to mark or unmark
     * @return returns the marked/unmarked task
     */
    public Task mark(int index1Based, boolean done) {
        Task t = tasks.get(index1Based - 1);
        t.mark(done);
        return t;
    }

    /**
     * Clear all tasks from the list;
     */
    public void clear() {
        tasks.clear();
    }

    /**
     * Checks if the list of tasks is empty
     * @return returns a boolean
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}
