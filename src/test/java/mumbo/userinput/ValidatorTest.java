package mumbo.userinput;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import mumbo.exception.MumboException;

/**
 * Tests for {@link Validator#validateEvent(String)} ensuring all edge cases are covered.
 */
public class ValidatorTest {

    // Helper to assert MumboException with substring
    private void assertFailsWith(String input, String expectedSubstringLower) {
        MumboException ex = assertThrows(MumboException.class, () -> Validator.validateEvent(input));
        assertTrue(ex.getMessage().toLowerCase().contains(expectedSubstringLower),
                () -> "Expected message to contain '" + expectedSubstringLower + "' but was: " + ex.getMessage());
    }

    @Test
    void testNullInput() {
        assertFailsWith(null, "description");
    }

    @Test
    void testBlankInput() {
        assertFailsWith("   ", "description");
    }

    @Test
    void testMissingFromClause() {
        assertFailsWith("project kickoff", "from");
    }

    @Test
    void testMissingAfterFromValue() {
        // Depending on split behaviour, may complain about /to or /from next stage
        MumboException ex = assertThrows(MumboException.class, () -> Validator.validateEvent("project /from   "));
        String msgLower = ex.getMessage().toLowerCase();
        assertTrue(msgLower.contains("from") || msgLower.contains("to"),
                () -> "Expected message to mention from or to but was: " + ex.getMessage());
    }

    @Test
    void testMissingToClause() {
        assertFailsWith("project /from 2025/09/01", "to");
    }

    @Test
    void testMissingAfterToValue() {
        assertFailsWith("project /from 2025/09/01 /to   ", "to");
    }

    @Test
    void testEndBeforeStart() {
        assertFailsWith("retro /from 2025/09/10 /to 2025/09/09", "conclude before");
    }

    @Test
    void testInvalidStartDateFormat() {
        assertFailsWith("meeting /from 2025/09/01 /to 2025-09-02", "invalid");
    }

    @Test
    void testInvalidEndDateFormat() {
        assertFailsWith("meeting /from 2025-09-01 /to 02-09-2025", "invalid");
    }

    @Test
    void testBothDatesInvalid() {
        assertFailsWith("meeting /from 2025/13/99 /to nonsense", "invalid");
    }

    @Test
    void testValidDateOnlyRange() {
        assertDoesNotThrow(() -> Validator.validateEvent("sprint /from 01/09/2025 /to 05/09/2025"));
    }

    @Test
    void testValidDateTimeRange() {
        assertDoesNotThrow(() -> Validator.validateEvent("launch /from 01/09/2025 09:00 /to 01/09/2025 17:00"));
    }

    @Test
    void testSameStartEndAllowed() {
        assertDoesNotThrow(() -> Validator.validateEvent("checkpoint /from 2025/09/01 /to 2025/09/01"));
    }

    @Test
    void testExtraSpacesNormalization() {
        assertDoesNotThrow(() -> Validator.validateEvent("hackathon   /from   2025/09/01   /to   2025/09/03   "));
    }

    @Test
    void testToBeforeFromOrderMalformed() {
        MumboException ex = assertThrows(MumboException.class,
                () -> Validator.validateEvent("project /to 2025/09/02 /from 2025/09/01"));
        assertTrue(ex.getMessage().toLowerCase().contains("from") || ex.getMessage().toLowerCase().contains("to"));
    }

    @Test
    void testMultipleFromClauses() {
        assertFailsWith("project /from 2025/09/01 /from 2025/09/02 /to 2025/09/03", "to");
    }
}
