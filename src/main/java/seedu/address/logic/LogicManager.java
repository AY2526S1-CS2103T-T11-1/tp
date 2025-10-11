package seedu.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.jobcommands.Command;
import seedu.address.logic.jobcommands.CommandResult;
import seedu.address.logic.jobcommands.exceptions.JobCommandException;
import seedu.address.logic.parser.JobBookCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.jobapplication.Model;
import seedu.address.model.person.Person;
import seedu.address.storage.DataStorage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;

    //New code
    private final DataStorage dataStorage;

    private final JobBookCommandParser jobBookCommandParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, DataStorage dataStorage) {
        this.model = model;
        this.dataStorage = dataStorage;
        jobBookCommandParser = new JobBookCommandParser();
    }

    @Override
    public CommandResult execute(String commandText) throws JobCommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = jobBookCommandParser.parseCommand(commandText);
        commandResult = command.execute(model);

        // Only save if the command indicates it should trigger a save operation
        if (commandResult.shouldSave()) {
            try {
                dataStorage.saveJobApplicationData(model.getJobBook().getApplicationList());
            } catch (AccessDeniedException e) {
                throw new JobCommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
            } catch (IOException ioe) {
                throw new JobCommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
            }
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        //return model.getAddressBook();
        return new AddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return null;
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getJobBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
