package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;

import java.time.LocalDateTime;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.logic.jobcommands.AddJobCommand;
import seedu.address.logic.jobcommands.DeleteJobCommand;
import seedu.address.model.jobapplication.JobApplication;

public class JobBookCommandParserTest {
    private final JobBookCommandParser parser = new JobBookCommandParser();

    @Test
    public void parseCommand_add() throws Exception {
        String companyName = "Google";
        String role = "SoftwareEngineer";
        LocalDateTime deadline = LocalDateTime.parse("2025-12-31T23:59");
        JobApplication.Status status = JobApplication.Status.APPLIED;

        JobApplication application = new JobApplication(companyName, role, deadline, status, new HashSet<>());
        AddJobCommand command = (AddJobCommand) parser.parseCommand(
            AddJobCommand.COMMAND_WORD + " n/Google r/SoftwareEngineer s/APPLIED d/2025-12-31T23:59");
        assertEquals(new AddJobCommand(application), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {

        DeleteJobCommand command = (DeleteJobCommand) parser.parseCommand(
            DeleteJobCommand.COMMAND_WORD + " " + INDEX_FIRST_APPLICATION.getOneBased());
        assertEquals(new DeleteJobCommand(INDEX_FIRST_APPLICATION), command);
    }

    // @Test
    // public void parseCommand_clear() throws Exception {
    //     assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
    //     assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    // }

    // @Test
    // public void parseCommand_delete() throws Exception {
    //     DeleteCommand command = (DeleteCommand) parser.parseCommand(
    //             DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
    //     assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    // }

    // @Test
    // public void parseCommand_edit() throws Exception {
    //     Person person = new PersonBuilder().build();
    //     EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
    //     EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
    //             + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
    //     assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    // }

    // @Test
    // public void parseCommand_exit() throws Exception {
    //     assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
    //     assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    // }

    // @Test
    // public void parseCommand_find() throws Exception {
    //     List<String> keywords = Arrays.asList("foo", "bar", "baz");
    //     FindCommand command = (FindCommand) parser.parseCommand(
    //             FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
    //     assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    // }

    // @Test
    // public void parseCommand_help() throws Exception {
    //     assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
    //     assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    // }

    // @Test
    // public void parseCommand_list() throws Exception {
    //     assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
    //     assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    // }

    // @Test
    // public void parseCommand_unrecognisedInput_throwsParseException() {
    //     assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE),
    //     ()
    //         -> parser.parseCommand(""));
    // }

    // @Test
    // public void parseCommand_unknownCommand_throwsParseException() {
    //     assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    // }
}
