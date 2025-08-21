import java.util.ArrayList;
import java.util.Scanner;

public class Mumbo {
    public static void main(String[] args) {
        String line = "____________________________________";
        String greeting = line + "\nHello! I'm Mumbo!\nWhat can I do for you?\n" + line;
        String farewell = line + "\nBye. Hope to see you again soon!\n" + line;

        Scanner scanner = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();

        System.out.println(greeting);

        while (true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("Bye")) {
                System.out.println(farewell);
                break;
            } else if (input.equalsIgnoreCase("list")) {
                System.out.println(line);
                for (int i = 0; i < list.size(); i++) {
                    System.out.println((i + 1) + ". " + list.get(i));
                }
                System.out.println(line);
            } else {
                list.add(input);
                System.out.println(line + "\nadded: " + input + "\n" + line);
            }
        }

        scanner.close();
    }
}
