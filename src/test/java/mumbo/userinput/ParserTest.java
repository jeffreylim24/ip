package mumbo.userinput;

import mumbo.command.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;



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
    void testParseYesNoTrue() {
        assertTrue(Parser.parseYesNo("y"));
        assertTrue(Parser.parseYesNo("yes"));
        assertTrue(Parser.parseYesNo(" YeS "));
    }

    @Test
    void testParseYesNoFalse() {
        assertFalse(Parser.parseYesNo("n"));
        assertFalse(Parser.parseYesNo("no"));
        assertFalse(Parser.parseYesNo(" No "));
    }
}
