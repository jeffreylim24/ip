package mumbo.ui;

import java.util.Scanner;

import mumbo.task.Task;
import mumbo.task.TaskList;

/**
 * Handles interactions with the user such as displaying messages and reading commands.
 * Responsible for input/output logic.
 */

public class Ui {
    private static final String LINE = "____________________________________";
    private final Scanner scanner = new Scanner(System.in);

    public void showWelcome() {
        System.out.println(LINE + "\nHello, I'm Mumbo.Mumbo!\nWhat can I do for you?\n" + LINE);
    }

    public void showBye() {
        System.out.println(LINE + "\nBye. Hope to see you again soon!\n" + LINE);
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public void showError(String message) {
        System.out.println(LINE + "\n" + message + "\n" + LINE);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showAdded(Task t, int size) {
        System.out.println(LINE + "\nGot it. I've added this task: \n " + t);
        System.out.println("Now you have " + size + " tasks in the list.\n" + LINE);
    }

    public void showDeleted(Task t, int size) {
        System.out.println(LINE + "\nNoted. I've removed this task: \n " + t);
        System.out.println("Now you have " + size + " tasks in the list.\n" + LINE);
    }

    public void showMarked(Task t, boolean done) {
        System.out.println(LINE);
        System.out.println(done ? "Nice! I've marked this task as done:" : "OK, I've marked this task as not done yet:");
        System.out.println(" " + t);
        System.out.println(LINE);
    }

    public void showList(TaskList tasks) {
        System.out.println(LINE);
        if (tasks.size() == 0) {
            System.out.println("Your list is empty.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
        System.out.println(LINE);
    }

    public void showClear() {
        System.out.println(LINE);
        System.out.println("Cleared all tasks.");
        System.out.println(LINE);
    }

    public void queryClearCache() {
        System.out.println(LINE);
        System.out.println("One last thing before you go! Do you want to clear your saved tasks? (Y/N)");
        System.out.println(LINE);
    }

    public void showHelp() {
        System.out.println(LINE + "\nCommands:\n" +
                "1. list\n" +
                "2. todo <desc>\n" +
                "3. deadline <desc> /by <when>\n" +
                "4. event <desc> /from <start> /to <end>\n" +
                "5. mark <n>\n" +
                "6. unmark <n>\n" +
                "7. delete <n>\n" +
                "8. clear\n" +
                "9. help\n" +
                "10. bye\n" + LINE);
    }
}
