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
            String[] parts = input.split(" ", 2);
            String command = parts[0];
            String argument = (parts.length > 1) ? parts[1] : null; // null if it is a 1 word command

            if (command.equalsIgnoreCase("bye")) {
                System.out.println(farewell);
                break;
            }

            switch (command) {
                case "mark":
                    if (argument != null && argument.matches("\\d+")) {
                        int index = Integer.parseInt(argument);
                        if (index > 0 && index <= list.size()) {
                            Task task = list.get(index - 1);
                            task.mark(true);
                            System.out.println(line + "\nNice! I've marked this task as done.\n" + task);
                        } else {
                            System.out.println(line + "\nError: " + argument + " is not a valid input.\n" + line);
                        }
                    } else {
                        System.out.println(line + "\nError: " + argument + " is not a valid input.\n" + line);
                    }
                    break;

                case "unmark":
                    if (argument != null && argument.matches("\\d+")) {
                        int index = Integer.parseInt(argument);
                        if (index > 0 && index <= list.size()) {
                            Task task = list.get(index - 1);
                            task.mark(false);
                            System.out.println(line + "\nOk, I've marked this task as not done yet:\n" + task);
                        } else {
                            System.out.println(line + "\nError: " + argument + " is not a valid input.\n" + line);
                        }
                    } else {
                        System.out.println(line + "\nError: " + argument + " is not a valid input.\n" + line);
                    }
                    break;

                case "list":
                    System.out.println(line);
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println((i + 1) + ". " + list.get(i).toString());
                    }
                    System.out.println(line);
                    break;

                default:
                    Task t = new Task(input);
                    list.add(t);
                    System.out.println(line + "\nadded: " + input + "\n" + line);
                    break;
            }
        }
        scanner.close();
    }
}
