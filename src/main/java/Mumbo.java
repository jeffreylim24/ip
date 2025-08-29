import java.util.ArrayList;
import java.util.Scanner;

/**
 * The main chatbot class that ties together the UI, storage, and task list.
 * Manages the overall application flow.
 */

public class Mumbo {
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    public Mumbo(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = storage.load();
    }

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
                Task td = tasks.add(new Deadline(in.args[0], in.args[1]));
                ui.showAdded(td, tasks.size());
                storage.save(tasks);
                break;

            case EVENT:
                Task te = tasks.add(new Event(in.args[0], in.args[1], in.args[2]));
                ui.showAdded(te, tasks.size());
                storage.save(tasks);
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
                ui.queryClearCache();
                boolean isKept;
                while (true) {
                    String reply = ui.readCommand();
                    try {
                        isKept = Validator.validateYesNo(reply);
                        break;
                    } catch (MumboException e) {
                        ui.showError(e.getMessage());
                    }
                }
                if (isKept) {
                    isExit = true;
                    break;
                }
                tasks.clear();
                storage.save(tasks);
                ui.showClear();
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

    public static void main(String[] args) {
        new Mumbo("mumbo_tasks.txt").run();
    }
}
