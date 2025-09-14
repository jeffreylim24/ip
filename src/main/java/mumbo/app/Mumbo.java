package mumbo.app;

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
 * Core application class that orchestrates the Mumbo task chatbot.
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Parses user input into commands via {@link mumbo.userinput.Parser}</li>
 *   <li>Mutates and persists tasks using {@link mumbo.task.TaskList} and {@link mumbo.storage.Storage}</li>
 *   <li>Renders user-facing messages and prompts through {@link mumbo.ui.Ui}</li>
 *   <li>Manages application lifecycle, including graceful exit and optional cache clearing</li>
 * </ul>
 *
 * <p>Usage patterns:</p>
 * <ul>
 *   <li>CLI: call {@link #run()} to start an interactive loop.</li>
 *   <li>GUI: call {@link #getResponse(String)} per user input and then check {@link #shouldExit()}.</li>
 * </ul>
 *
 * <p>Persistence: tasks are loaded from the provided storage file on construction and
 * saved after any state-changing command (e.g., add, mark, delete, clear).</p>
 *
 * <p>Thread-safety: this class is not thread-safe.</p>
 *
 * @see mumbo.ui.Ui
 * @see mumbo.storage.Storage
 * @see mumbo.task.TaskList
 * @see mumbo.userinput.Parser
 */
public class Mumbo {
    private static final String DEFAULT_FILE_PATH = "mumbo-tasks.txt";
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;
    private boolean isAwaitingByeConfirmation = false; // Track if we're waiting for bye confirmation
    private boolean shouldExit = false; // Track if the application should exit

    /**
     * Creates a new chatbot backed by the given storage file.
     *
     * <p>On construction, tasks are loaded from {@code fileName}. If the file does not
     * exist or cannot be read, an empty task list is used.</p>
     *
     * @param fileName path to the persistent storage file
     */
    public Mumbo(String fileName) {
        this.ui = new Ui();
        this.storage = new Storage(fileName);
        this.tasks = storage.load();
    }

    /**
     * Starts the interactive CLI loop and blocks until exit.
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
                    ui.showError(ui.getDateFormatErrorMessage());
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
                    ui.showError(ui.getDateFormatErrorMessage());
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

            case FIND:
                TaskList matchingTasks = tasks.find(in.args[0]);
                ui.showFind(matchingTasks);
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
     * CLI entry point. Starts the chatbot using the default storage file.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        new Mumbo(DEFAULT_FILE_PATH).run();
    }

    /**
     * Indicates whether the application should exit after the last processed input.
     *
     * @return {@code true} if an exit was requested; {@code false} otherwise
     */
    public boolean shouldExit() {
        return shouldExit;
    }

    /**
     * Processes a single line of user input and returns the response message.
     *
     * <p>Designed for GUI usage: maintains conversational state (e.g., bye confirmation)
     * across calls. After handling an exit flow, consult {@link #shouldExit()} to know
     * whether the host UI should close.</p>
     *
     * @param input raw user input
     * @return user-facing response message
     */
    public String getResponse(String input) {
        // Handle bye confirmation flow first
        if (isAwaitingByeConfirmation) {
            try {
                boolean shouldClear = Validator.validateYesNo(input);
                if (shouldClear) {
                    tasks.clear();
                    storage.save(tasks);
                    isAwaitingByeConfirmation = false;
                    shouldExit = true;
                    return ui.getClearedOnExitMessage();
                } else {
                    isAwaitingByeConfirmation = false;
                    shouldExit = true;
                    return ui.getByeMessage();
                }
            } catch (MumboException e) {
                return ui.getYesNoErrorMessage();
            }
        }

        // Reset exit flag for non-bye commands
        shouldExit = false;

        ParsedInput in = Parser.parse(input);

        switch (in.command) {
        case LIST:
            return ui.getListMessage(tasks);

        case TODO:
            Task t = tasks.add(new Todo(in.args[0]));
            storage.save(tasks);
            return ui.getAddedMessage(t, tasks.size());

        case DEADLINE:
            try {
                Task td = tasks.add(new Deadline(in.args[0], DateTimeUtil.parseDateTime(in.args[1])));
                storage.save(tasks);
                return ui.getAddedMessage(td, tasks.size());
            } catch (DateTimeParseException e) {
                return ui.getDateFormatErrorMessage();
            }

        case EVENT:
            try {
                Task te = tasks.add(new Event(in.args[0],
                        DateTimeUtil.parseDateTime(in.args[1]),
                        DateTimeUtil.parseDateTime(in.args[2])));
                storage.save(tasks);
                return ui.getAddedMessage(te, tasks.size());
            } catch (DateTimeParseException e) {
                return ui.getDateFormatErrorMessage();
            }

        case MARK:
            int mIndex = Integer.parseInt(in.args[0]);
            Validator.validateInRange(mIndex, 1, tasks.size());
            Task tm = tasks.mark(mIndex, true);
            storage.save(tasks);
            return ui.getMarkedMessage(tm, true);

        case UNMARK:
            int uIndex = Integer.parseInt(in.args[0]);
            Validator.validateInRange(uIndex, 1, tasks.size());
            Task tu = tasks.mark(uIndex, false);
            storage.save(tasks);
            return ui.getMarkedMessage(tu, false);

        case DELETE:
            int idx = Integer.parseInt(in.args[0]);
            Validator.validateInRange(idx, 1, tasks.size());
            Task dt = tasks.delete(idx);
            storage.save(tasks);
            return ui.getDeletedMessage(dt, tasks.size());

        case CLEAR:
            tasks.clear();
            storage.save(tasks);
            return ui.getClearMessage();

        case HELP:
            return ui.getHelpMessage();

        case FIND:
            TaskList matchingTasks = tasks.find(in.args[0]);
            return ui.getFindMessage(matchingTasks);

        case BYE:
            if (tasks.isEmpty()) {
                shouldExit = true;
                return ui.getByeMessage();
            } else {
                isAwaitingByeConfirmation = true;
                return ui.getClearCacheQuery(tasks.size());
            }

        case ERROR:
            return in.args[0];

        default:
            return "Sorry, I didn't quite catch that...\nTry typing 'help' to see possible commands";
        }
    }
}
