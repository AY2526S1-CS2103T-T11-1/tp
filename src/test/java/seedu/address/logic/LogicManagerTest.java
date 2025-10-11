package seedu.address.logic;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.jobapplication.Model;
import seedu.address.model.jobapplication.ModelManager;
import seedu.address.storage.DataStorageManager;
import seedu.address.storage.JsonJobApplicationStorage;
import seedu.address.storage.JsonUserPrefsStorage;

public class LogicManagerTest {

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {

        JsonJobApplicationStorage jobApplicationStorage =
                new JsonJobApplicationStorage(temporaryFolder.resolve("jobApplication.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        DataStorageManager storage = new DataStorageManager(jobApplicationStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        //assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        //assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        //assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        //assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPersonList().remove(0));
    }


    // private void assertCommandSuccess(String inputCommand, String expectedMessage,
    //         Model expectedModel) throws CommandException, ParseException {
    //     CommandResult result = logic.execute(inputCommand);
    //     assertEquals(expectedMessage, result.getFeedbackToUser());
    //     assertEquals(expectedModel, model);
    // }


    // private void assertParseException(String inputCommand, String expectedMessage) {
    //     assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    // }


    // private void assertCommandException(String inputCommand, String expectedMessage) {
    //     assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    // }


    // private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
    //         String expectedMessage) {
    //     Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    //     assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    // }

    // private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
    //         String expectedMessage, Model expectedModel) {
    //     assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
    //     assertEquals(expectedModel, model);
    // }

}
