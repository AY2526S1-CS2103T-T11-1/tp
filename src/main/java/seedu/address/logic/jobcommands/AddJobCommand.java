package seedu.address.logic.jobcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.JobMessages;
import seedu.address.logic.jobcommands.exceptions.JobCommandException;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.model.jobapplication.Model;

/**
 * Adds a job application to the job book.
 */
public class AddJobCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a job application to the job book.\n"
            + "Parameters: n/COMPANY_NAME r/ROLE s/STATUS d/DEADLINE t/TAG\n"
            + "Example: " + COMMAND_WORD + " n/Google r/SoftwareEngineer s/APPLIED d/2025-12-31T23:59 t/6-Month";

    public static final String MESSAGE_SUCCESS = "New job application added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPLICATION =
            "This job application already exists in the job book";

    private final JobApplication toAdd;

    /**
     * Creates an AddJobCommand to add the specified {@code JobApplication}
     */
    public AddJobCommand(JobApplication application) {
        requireNonNull(application);
        toAdd = application;
    }

    @Override
    public CommandResult execute(Model model) throws JobCommandException {
        requireNonNull(model);

        if (model.hasApplication(toAdd)) {
            throw new JobCommandException(MESSAGE_DUPLICATE_APPLICATION);
        }

        model.addJobApplication(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, JobMessages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddJobCommand)) {
            return false;
        }

        AddJobCommand otherAddCommand = (AddJobCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
