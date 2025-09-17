package mumbo.userinput;

import mumbo.command.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;



/**
 * Test suite for the Parser class.
 * Covers valid and invalid inputs for all supported commands.
 * Ensures correct command identification and argument extraction.
 */
public class ParserTest {
    @Test
    void testNullInput() {
        ParsedInput input = Parser.parse(null);
        assertEquals(Command.UNKNOWN, input.getCommand());
    }

    @Test
    void testBlankInput() {
        ParsedInput input = Parser.parse("   ");
        assertEquals(Command.UNKNOWN, input.getCommand());
    }

    @Test
    void testTodoValid() {
        ParsedInput input = Parser.parse("todo buy milk");
        assertEquals(Command.TODO, input.getCommand());
        assertEquals("buy milk", input.getArgX(1));
    }

    @Test
    void testTodoInvalid() {
        ParsedInput input = Parser.parse("todo"); // missing description
        assertEquals(Command.ERROR, input.getCommand());
        assertNotNull(input.getArgX(1)); // should hold the error message
    }

    @Test
    void testDeadlineValid() {
        ParsedInput input = Parser.parse("deadline submit assignment /by tomorrow");
        assertEquals(Command.DEADLINE, input.getCommand());
        assertEquals("submit assignment", input.getArgX(1));
        assertEquals("tomorrow", input.getArgX(2));
    }

    @Test
    void testDeadlineInvalid() {
        ParsedInput input = Parser.parse("deadline noByClause");
        assertEquals(Command.ERROR, input.getCommand());
        assertNotNull(input.getArgX(1)); // error message
    }

    @Test
    void testEventValid() {
        ParsedInput input = Parser.parse("event concert /from 06/06/2025 20:00 /to 06/06/2025 23:59");
        assertEquals(Command.EVENT, input.getCommand());
        assertEquals("concert", input.getArgX(1));
        assertEquals("06/06/2025 20:00", input.getArgX(2));
        assertEquals("06/06/2025 23:59", input.getArgX(3));
    }

    @Test
    void testEventInvalid() {
        ParsedInput input = Parser.parse("event party /from today"); // missing /to
        assertEquals(Command.ERROR, input.getCommand());
        assertNotNull(input.getArgX(1)); // error message
    }

    @Test
    void testMarkValid() {
        ParsedInput input = Parser.parse("mark 3");
        assertEquals(Command.MARK, input.getCommand());
        assertEquals("3", input.getArgX(1));
    }

    @Test
    void testMarkInvalid() {
        ParsedInput input = Parser.parse("mark abc"); // not an integer
        assertEquals(Command.ERROR, input.getCommand());
    }

    @Test
    void testUnmarkValid() {
        ParsedInput input = Parser.parse("unmark 2");
        assertEquals(Command.UNMARK, input.getCommand());
        assertEquals("2", input.getArgX(1));
    }

    @Test
    void testDeleteValid() {
        ParsedInput input = Parser.parse("delete 5");
        assertEquals(Command.DELETE, input.getCommand());
        assertEquals("5", input.getArgX(1));
    }

    @Test
    void testDeleteInvalid() {
        ParsedInput input = Parser.parse("delete notANumber");
        assertEquals(Command.ERROR, input.getCommand());
    }

    @Test
    void testListCommand() {
        ParsedInput input = Parser.parse("list");
        assertEquals(Command.LIST, input.getCommand());
        assertNull(input.getArgX(1));
    }

    @Test
    void testClearCommand() {
        ParsedInput input = Parser.parse("clear");
        assertEquals(Command.CLEAR, input.getCommand());
        assertNull(input.getArgX(1));
    }

    @Test
    void testHelpCommand() {
        ParsedInput input = Parser.parse("help");
        assertEquals(Command.HELP, input.getCommand());
        assertNull(input.getArgX(1));
    }

    @Test
    void testByeCommand() {
        ParsedInput input = Parser.parse("bye");
        assertEquals(Command.BYE, input.getCommand());
        assertNull(input.getArgX(1));
    }

    @Test
    void testUnknownCommand() {
        ParsedInput input = Parser.parse("foobar something");
        assertEquals(Command.UNKNOWN, input.getCommand());
    }

    @Test
    void testParseFindValid() {
        ParsedInput result = Parser.parse("find book");
        assertEquals(Command.FIND, result.command);
        assertEquals("book", result.args[0]);
    }

    @Test
    void testParseFindEmpty() {
        ParsedInput result = Parser.parse("find");
        assertEquals(Command.ERROR, result.command);
        assertTrue(result.args[0].toLowerCase().contains("keyword"));
    }

    @Test
    void testParseFindBlank() {
        ParsedInput result = Parser.parse("find   ");
        assertEquals(Command.ERROR, result.command);
        assertTrue(result.args[0].toLowerCase().contains("keyword"));
    }

    // FINDTAG tests
    @Test
    void testFindTagValid() {
        ParsedInput result = Parser.parse("findtag urgent");
        assertEquals(Command.FINDTAG, result.command);
        assertEquals("urgent", result.args[0]);
    }

    @Test
    void testFindTagMissingKeyword() {
        ParsedInput result = Parser.parse("findtag");
        assertEquals(Command.ERROR, result.command);
        assertTrue(result.args[0].toLowerCase().contains("keyword"));
    }

    // TAG command tests
    @Test
    void testTagValid() {
        ParsedInput result = Parser.parse("tag 3 urgent");
        assertEquals(Command.TAG, result.command);
        assertArrayEquals(new String[]{"3", "urgent"}, result.args);
    }

    @Test
    void testTagMissingArgs() {
        ParsedInput result = Parser.parse("tag");
        assertEquals(Command.ERROR, result.command);
        assertTrue(result.args[0].toLowerCase().contains("task index"));
    }

    @Test
    void testTagMissingTagName() {
        ParsedInput result = Parser.parse("tag 2   ");
        assertEquals(Command.ERROR, result.command);
        assertTrue(result.args[0].toLowerCase().contains("tag name"));
    }

    @Test
    void testTagNonIntegerIndex() {
        ParsedInput result = Parser.parse("tag abc urgent");
        assertEquals(Command.ERROR, result.command);
        assertTrue(result.args[0].toLowerCase().contains("positive integer"));
    }

    // Robustness: arguments with extra spaces
    @Test
    void testTodoExtraSpaces() {
        ParsedInput input = Parser.parse("  todo    read    book   ");
        assertEquals(Command.TODO, input.getCommand());
        assertEquals("read    book", input.getArgX(1)); // internal spaces preserved after first split
    }

    @Test
    void testDeadlineExtraSpacesAroundBy() {
        ParsedInput input = Parser.parse("deadline  finish report    /by    next week   ");
        assertEquals(Command.DEADLINE, input.getCommand());
        assertEquals("finish report", input.getArgX(1));
        assertEquals("next week", input.getArgX(2));
    }

    @Test
    void testEventExtraSpaces() {
        ParsedInput input = Parser.parse("event  hackathon   /from  2025/09/01   /to   2025/09/03  ");
        // Date formats may not parse; ensure either proper parsing or error
        if (input.getCommand() == Command.EVENT) {
            assertEquals("hackathon", input.getArgX(1));
            assertEquals("2025/09/01", input.getArgX(2));
            assertEquals("2025/09/03", input.getArgX(3));
        } else {
            assertEquals(Command.ERROR, input.getCommand());
        }
    }

    // Defensive: ensure ERROR carries message non-null
    @Test
    void testErrorCarriesMessage() {
        ParsedInput input = Parser.parse("deadline   ");
        assertEquals(Command.ERROR, input.getCommand());
        assertNotNull(input.args[0]);
        assertFalse(input.args[0].isBlank());
    }
}
