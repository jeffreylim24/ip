import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading tasks from and saving tasks to persistent storage (a local file).
 */

public class Storage {
    private final String path;

    public Storage(String fileName) {
        this.path = "./data/" + fileName;

        // Check if user has './data' directory & creates if necessary
        File directory = new File("./data");
        directory.mkdir();  // No need for exists() check, as mkdir() will do nothing if it already exists

        // Check if user has the file & creates if necessary
        File file = new File(this.path);
        try {
            file.createNewFile();   // No need for exists() check for the same reason as above
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }
    }

    public void save(TaskList tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.path))) {
            for (Task task : tasks.asList()) {
                writer.write(task.toFormattedString());  // Convert task to string format
                writer.newLine();               // Add a new line after each task
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving tasks.");
            e.printStackTrace();
        }
    }

    public TaskList load() {
        ArrayList<Task> tasks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(this.path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parse each line to create the appropriate task
                Task task = parseTask(line);
                tasks.add(task);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading tasks.");
            e.printStackTrace();
        }
        return new TaskList(tasks);
    }

    public Task parseTask(String line) {
        // Split the line using a delimiter
        String[] parts = line.split("\\|");

        // Trim each part to remove leading and trailing spaces
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        // Depending on the task type, create accordingly
        return switch (parts[0]) {
            case "T" -> new Todo(parts[2]);
            case "D" -> new Deadline(parts[2], parts[3]);
            case "E" -> new Event(parts[2], parts[3], parts[4]);
            default -> {
                System.out.println("Unknown task type: " + parts[0]);
                yield null;
            }
        };
    }
}
