package mumbo;

import java.time.format.DateTimeParseException;

import mumbo.exception.MumboException;
import mumbo.storage.Storage;
import mumbo.task.Deadline;
import mumbo.task.Event;
import mumbo.task.Task;
import mumbo.task.TaskList;
import mumbo.task.Todo;
import mumbo.ui.Ui;
import mumbo.userinput.DateTimeUtil;
import mumbo.userinput.ParsedInput;
import mumbo.userinput.Parser;
import mumbo.userinput.Validator;

/**
 * Mumbo.Mumbo class
 *
 * The main chatbot class that ties together the UI, storage, and task list
 * Manages the overall application flow
 */

public class Mumbo {
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Initialises the chatbot with its cache file
     * @param fileName a String containing the name of its cache file
     */
    public Mumbo(String fileName) {
        this.ui = new Ui();
        this.storage = new Storage(fileName);
        this.tasks = storage.load();
    }

    /**
     * Starts the chatbot
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            String input = ui.readCommand();
            ParsedInput in = Parser.parse(input);
            switch (in.command) {
            case LIST:
                ui.showList(tasks);
                break;

            case TODO:
                Task t = tasks.add(new Todo(in.args[0]));
                ui.showAdded(t, tasks.size());
                storage.save(tasks);
                break;

            case DEADLINE:
                try {
                    Task td = tasks.add(new Deadline(in.args[0], DateTimeUtil.parseDateTime(in.args[1])));
                    ui.showAdded(td, tasks.size());
                    storage.save(tasks);
                } catch (DateTimeParseException e) {
                    ui.showError(e.getMessage() + "\nPlease use one of the following formats:\n"
                            + "1) yyyy/MM/dd\n"
                            + "2) yyyy/MM/dd HH:mm\n"
                            + "3) dd/MM/yyyy\n"
                            + "4) dd/MM/yyyy HH:mm");
                }
                break;

            case EVENT:
                try {
                    Task te = tasks.add(new Event(in.args[0],
                            DateTimeUtil.parseDateTime(in.args[1]),
                            DateTimeUtil.parseDateTime(in.args[2])));
                    ui.showAdded(te, tasks.size());
                    storage.save(tasks);
                } catch (DateTimeParseException e) {
                    ui.showError(e.getMessage() + "\nPlease use one of the following formats:\n"
                            + "1) yyyy/MM/dd\n"
                            + "2) yyyy/MM/dd HH:mm\n"
                            + "3) dd/MM/yyyy\n"
                            + "4) dd/MM/yyyy HH:mm");
                }
                break;

            case MARK:
                int mIndex = Integer.parseInt(in.args[0]);
                try {
                    Validator.validateInRange(mIndex, 1, tasks.size());
                } catch (MumboException e) {
                    ui.showError(e.getMessage());
                    break;
                }
                Task tm = tasks.mark(mIndex, true);
                ui.showMarked(tm, true);
                storage.save(tasks);
                break;

            case UNMARK:
                int uIndex = Integer.parseInt(in.args[0]);
                try {
                    Validator.validateInRange(uIndex, 1, tasks.size());
                } catch (MumboException e) {
                    ui.showError(e.getMessage());
                    break;
                }
                Task tu = tasks.mark(uIndex, false);
                ui.showMarked(tu, false);
                storage.save(tasks);
                break;

            case DELETE:
                int idx = Integer.parseInt(in.args[0]);
                try {
                    Validator.validateInRange(idx, 1, tasks.size());
                } catch (MumboException e) {
                    ui.showError(e.getMessage());
                    break;
                }
                Task dt = tasks.delete(idx);
                ui.showDeleted(dt, tasks.size());
                storage.save(tasks);
                break;

            case CLEAR:
                tasks.clear();
                storage.save(tasks);
                ui.showClear();
                break;

            case HELP:
                ui.showHelp();
                break;

            case BYE:
                if (tasks.isEmpty()) {
                    isExit = true;
                    break;
                }
                ui.queryClearCache();
                boolean isCleared;
                while (true) {
                    String reply = ui.readCommand();
                    try {
                        isCleared = Validator.validateYesNo(reply);
                        break;
                    } catch (MumboException e) {
                        ui.showError(e.getMessage());
                    }
                }
                if (isCleared) {
                    tasks.clear();
                    storage.save(tasks);
                    ui.showClear();
                    isExit = true;
                    break;
                }
                isExit = true;
                break;

            case ERROR:
                ui.showError(in.args[0]);
                break;

            default:
                ui.showError("Sorry, I didn't quite catch that...\nTry typing 'help' to see possible commands");
            }
        }
        ui.showBye();
    }

    /**
     * Starts the chatbot
     */
    public static void main(String[] args) {
        new Mumbo("mumbo_tasks.txt").run();
    }
}
