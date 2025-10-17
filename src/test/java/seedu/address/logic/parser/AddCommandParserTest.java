package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.JobCommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.JobCommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.logic.jobcommands.AddJobCommand;
import seedu.address.model.jobapplication.JobApplication;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the AddJobCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the AddJobCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_validArgs_returnsAddJobCommand() {
        String companyName = "Google";
        String role = "SoftwareEngineer";
        LocalDateTime deadline = LocalDateTime.parse("2025-12-31T23:59");
        JobApplication.Status status = JobApplication.Status.APPLIED;

        JobApplication expectedApplication = new JobApplication(companyName, role, deadline, status, new HashSet<>());
        assertParseSuccess(parser, " n/Google r/SoftwareEngineer s/APPLIED d/2025-12-31T23:59",
                new AddJobCommand(expectedApplication));
    }

    @Test
    public void parse_validArgsWithDifferentStatus_returnsAddJobCommand() {
        String companyName = "Microsoft";
        String role = "DataScientist";
        LocalDateTime deadline = LocalDateTime.parse("2025-11-30T17:00");
        JobApplication.Status status = JobApplication.Status.INPROGRESS;

        JobApplication expectedApplication = new JobApplication(companyName, role, deadline, status, new HashSet<>());
        assertParseSuccess(parser, " n/Microsoft r/DataScientist s/INPROGRESS d/2025-11-30T17:00",
                new AddJobCommand(expectedApplication));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddJobCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingArgs_throwsParseException() {
        // Only company name
        assertParseFailure(parser, " n/Google",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddJobCommand.MESSAGE_USAGE));

        // Only company name and role
        assertParseFailure(parser, " n/Google r/SoftwareEngineer",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddJobCommand.MESSAGE_USAGE));

        // Missing status
        assertParseFailure(parser, " n/Google r/SoftwareEngineer d/2025-12-31T23:59",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddJobCommand.MESSAGE_USAGE));

        // Missing deadline
        assertParseFailure(parser, " n/Google r/SoftwareEngineer s/APPLIED",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddJobCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDeadline_throwsParseException() {
        assertParseFailure(parser, " n/Google r/SoftwareEngineer s/APPLIED d/invalid-date",
                "Invalid deadline format. Expected format: yyyy-MM-ddTHH:mm");
    }

    @Test
    public void parse_invalidStatus_throwsParseException() {
        assertParseFailure(parser, " n/Google r/SoftwareEngineer s/INVALID_STATUS d/2025-12-31T23:59",
                "Invalid status. Valid values are: APPLIED, INPROGRESS, REJECTED");
    }
}
