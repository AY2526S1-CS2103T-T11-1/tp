package seedu.address.logic.jobcommands;

import seedu.address.model.jobapplication.Model;

/**
 * Saves the current state of data in HustleHub
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";

    private static final String MESSAGE_ON_SUCCESS = "Saved job applications successfully!";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_ON_SUCCESS, false, false, true);
    }
}
