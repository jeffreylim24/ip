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
        assert fileName != null && !fileName.isBlank() : "Storage filename must not be null/blank";
        this.ui = new Ui();
        this.storage = new Storage(fileName);
        this.tasks = storage.load();
        assert this.storage != null : "Storage must be initialised";
        assert this.tasks != null : "Loaded TaskList must not be null";
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
        if (isAwaitingByeConfirmation) {
            return handleByeConfirmation(input);
        }

        shouldExit = false;
        ParsedInput in = Parser.parse(input);
        assert in != null : "ParsedInput must not be null";

        switch (in.command) {
        case LIST:
            return handleList();
        case TODO:
            return handleTodo(in);
        case DEADLINE:
            return handleDeadline(in);
        case EVENT:
            return handleEvent(in);
        case MARK:
            return handleMark(in);
        case UNMARK:
            return handleUnmark(in);
        case DELETE:
            return handleDelete(in);
        case CLEAR:
            return handleClear();
        case HELP:
            return handleHelp();
        case FIND:
            return handleFind(in);
        case BYE:
            return handleBye();
        case ERROR:
            return in.args[0];
        default:
            return "Sorry, I didn't quite catch that...\nTry typing 'help' to see possible commands";
        }
    }

    // Private handler methods for each command:

    private String handleByeConfirmation(String input) {
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

    private String handleList() {
        return ui.getListMessage(tasks);
    }

    private String handleTodo(ParsedInput in) {
        assert in.args.length >= 1 : "TODO requires one argument";
        Task t = tasks.add(new Todo(in.args[0]));
        storage.save(tasks);
        return ui.getAddedMessage(t, tasks.size());
    }

    private String handleDeadline(ParsedInput in) {
        try {
            assert in.args.length >= 2 : "DEADLINE requires two arguments";
            Task td = tasks.add(new Deadline(in.args[0], DateTimeUtil.parseDateTime(in.args[1])));
            storage.save(tasks);
            return ui.getAddedMessage(td, tasks.size());
        } catch (DateTimeParseException e) {
            return ui.getDateFormatErrorMessage();
        }
    }

    private String handleEvent(ParsedInput in) {
        try {
            assert in.args.length >= 3 : "EVENT requires three arguments";
            Task te = tasks.add(new Event(in.args[0],
                    DateTimeUtil.parseDateTime(in.args[1]),
                    DateTimeUtil.parseDateTime(in.args[2])));
            storage.save(tasks);
            return ui.getAddedMessage(te, tasks.size());
        } catch (DateTimeParseException e) {
            return ui.getDateFormatErrorMessage();
        }
    }

    private String handleMark(ParsedInput in) {
        assert in.args.length >= 1 : "MARK requires one argument";
        try {
            int mIndex = Integer.parseInt(in.args[0]);
            Validator.validateInRange(mIndex, 1, tasks.size());
            Task tm = tasks.mark(mIndex, true);
            storage.save(tasks);
            return ui.getMarkedMessage(tm, true);
        } catch (MumboException e) {
            return e.getMessage();
        }
    }

    private String handleUnmark(ParsedInput in) {
        assert in.args.length >= 1 : "UNMARK requires one argument";
        try {
            int uIndex = Integer.parseInt(in.args[0]);
            Validator.validateInRange(uIndex, 1, tasks.size());
            Task tu = tasks.mark(uIndex, false);
            storage.save(tasks);
            return ui.getMarkedMessage(tu, false);
        } catch (MumboException e) {
            return e.getMessage();
        }
    }

    private String handleDelete(ParsedInput in) {
        assert in.args.length >= 1 : "DELETE requires one argument";
        try {
            int idx = Integer.parseInt(in.args[0]);
            Validator.validateInRange(idx, 1, tasks.size());
            Task dt = tasks.delete(idx);
            storage.save(tasks);
            return ui.getDeletedMessage(dt, tasks.size());
        } catch (MumboException e) {
            return e.getMessage();
        }
    }

    private String handleClear() {
        tasks.clear();
        storage.save(tasks);
        return ui.getClearMessage();
    }

    private String handleHelp() {
        return ui.getHelpMessage();
    }

    private String handleFind(ParsedInput in) {
        assert in.args.length >= 1 : "FIND requires one argument";
        TaskList matchingTasks = tasks.find(in.args[0]);
        return ui.getFindMessage(matchingTasks);
    }

    private String handleBye() {
        if (tasks.isEmpty()) {
            shouldExit = true;
            return ui.getByeMessage();
        } else {
            isAwaitingByeConfirmation = true;
            return ui.getClearCacheQuery(tasks.size());
        }
    }
}
