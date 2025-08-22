import java.util.ArrayList;
import java.util.Scanner;

public class Mumbo {

    private static final String line = "____________________________________";

    public Mumbo() {}

    public static void main(String[] args) {
        // Set up
        String greeting = line + "\nHello! I'm Mumbo!\nWhat can I do for you?\n" + line;
        String farewell = line + "\nBye. Hope to see you again soon!\n" + line;

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<>();

        System.out.println(greeting);

        // Read user input
        while (true) {
            String input = scanner.nextLine();

            // Split into command & argument
            String[] parts = input.split("\\s+", 2);
            String command = parts[0];
            String argument = (parts.length > 1) ? parts[1] : null; // null if it is a 1 word command

            if (command.equalsIgnoreCase("bye")) {
                System.out.println(farewell);
                break;
            }

            switch (command) {
                case "list":
                    System.out.println(line);
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println((i + 1) + ". " + list.get(i).toString());
                    }
                    System.out.println(line);
                    break;

                case "mark":
                case "unmark":
                    if (argument != null && argument.matches("\\d+")) {
                        int index = Integer.parseInt(argument);
                        boolean done = command.equals("mark");
                        if (index > 0 && index <= list.size()) {
                            Task task = list.get(index - 1);
                            task.mark(done);
                            if (done) {
                                System.out.println(line + "\nNice! I've marked this task as done.\n" + task + "\n" + line);
                            } else {
                                System.out.println(line + "\nOk, I've marked this task as not done yet:\n" + task + "\n" + line);
                            }
                        } else {
                            System.out.println(line + "\nError: " + argument + " exceeds list size.\n" + line);
                        }
                    } else {
                        System.out.println(line + "\nError: " + argument + " is not a valid input.\n" + line);
                    }
                    break;

                case "todo":
                    if (argument == null) {
                        System.out.println(line +"\nThe description is empty.\n" + line);
                        break;
                    }
                    Todo todo = new Todo(argument);
                    list.add(todo);
                    System.out.println(line + "\nGot it. I've added this task:");
                    System.out.println(" " + todo);
                    System.out.println("Now you have " + list.size() + " tasks in the list.\n" + line);
                    break;

                case "deadline":
                    if (argument == null) {
                        System.out.println(line +"\nThe description is empty.\n" + line);
                        break;
                    }
                    int x = argument.indexOf(" /by ");
                    if (x == -1) {
                        System.out.println(line + "\nPlease specify deadline using /by <deadline>.\n" + line);
                        break;
                    }
                    String dTask = argument.substring(0, x);
                    String by = argument.substring(x + 5);
                    if (dTask.isEmpty() || by.isEmpty()) {
                        System.out.println(line + "\nPlease provide both task description and its deadline.\n" + line);
                        break;
                    }
                    Deadline deadline = new Deadline(dTask, by);
                    list.add(deadline);
                    System.out.println(line + "\nGot it. I've added this task:");
                    System.out.println(" " + deadline);
                    System.out.println("Now you have " + list.size() + " tasks in the list.\n" + line);
                    break;

                case "event":
                    if (argument == null) {
                        System.out.println(line +"\nThe description is empty.\n" + line);
                        break;
                    }
                    int startX = argument.indexOf(" /from ");
                    int endX = argument.indexOf(" /to ");
                    if (startX == -1 || endX == -1 || endX < startX) {
                        System.out.println(line + "\nPlease specify event duration using /from <start> /to <end>\n" + line);
                    }
                    String eTask = argument.substring(0, startX);
                    String start = argument.substring(startX + 7, endX);
                    String end = argument.substring(endX + 5);
                    if (eTask.isEmpty() || start.isEmpty() || end.isEmpty()) {
                        System.out.println(line + "\nPlease provide description, /from <start> and /to <end>\n" + line);
                        break;
                    }
                    Event event = new Event(eTask, start, end);
                    list.add(event);
                    System.out.println(line + "\nGot it. I've added this task:");
                    System.out.println(" " + event);
                    System.out.println("Now you have " + list.size() + " tasks in the list.\n" + line);
                    break;

                default:
                    Task task = new Task(input);
                    list.add(task);
                    System.out.println(line + "\nadded: " + input + "\n" + line);
                    break;
            }
        }
        scanner.close();
    }
}
