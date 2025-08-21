import java.util.Scanner;

public class Mumbo {
    public static void main(String[] args) {
        String line = "____________________________________\n";
        String greeting = line + "Hello! I'm Mumbo!\nWhat can I do for you?\n" + line;
        String farewell = "Bye. Hope to see you again soon!\n" + line;
        System.out.println(greeting);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("Bye")) {
                System.out.println(farewell);
                break;
            }

            System.out.println(line + input + "\n" + line);
        }

        scanner.close();
    }
}
