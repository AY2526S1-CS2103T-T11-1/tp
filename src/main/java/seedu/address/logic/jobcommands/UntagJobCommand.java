package seedu.address.logic.jobcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.jobapplication.Model.PREDICATE_SHOW_ALL_APPLICATIONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.JobMessages;
import seedu.address.logic.jobcommands.exceptions.JobCommandException;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.model.jobapplication.Model;
import seedu.address.model.tag.Tag;

/**
 * Removes tags from a job application.
 */
public class UntagJobCommand extends Command {

    public static final String MESSAGE_TAG_REMOVAL_SUCCESS = "Tags have been removed!";
    public static final String COMMAND_WORD = "untag";

    public static final String MESSAGE_MISSING_TAG = "The entered tags do not exist!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes tags from a job application.\n"
            + "Parameters: index_name t/tag t/tag t/tag\n"
            + "Example: " + COMMAND_WORD + " 1 t/Intern t/Summer t/on-site";

    protected final Index targetIndex;
    private final Set<Tag> tagsToRemove;

    /**
     * Creates an AddJobCommand to add the specified {@code JobApplication}
     */
    public UntagJobCommand(Index targetIndex, Set<Tag> tags) {
        requireNonNull(targetIndex);
        requireNonNull(tags);
        this.targetIndex = targetIndex;
        this.tagsToRemove = tags;
    }

    @Override
    public CommandResult execute(Model model) throws JobCommandException {
        requireNonNull(model);

        List<JobApplication> lastShownList = model.getFilteredApplicationList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new JobCommandException(JobMessages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }

        JobApplication jobToUntag = lastShownList.get(targetIndex.getZeroBased());
        Set<Tag> existingTags = jobToUntag.getTags();

        for (Tag tag : tagsToRemove) {
            if (existingTags.contains(tag)) {
                existingTags.remove(tag);
            } else {
                throw new JobCommandException(MESSAGE_MISSING_TAG);
            }
        }

        model.updateFilteredJobApplicationList(PREDICATE_SHOW_ALL_APPLICATIONS);

        return new CommandResult(String.format(MESSAGE_TAG_REMOVAL_SUCCESS, JobMessages.format(jobToUntag)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UntagJobCommand)) {
            return false;
        }

        UntagJobCommand otherTagCommand = (UntagJobCommand) other;
        boolean areTagsEqual = this.tagsToRemove.equals(otherTagCommand.tagsToRemove);
        boolean isIndexSame = targetIndex.equals(otherTagCommand.targetIndex);

        return areTagsEqual && isIndexSame;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex.getZeroBased())
                .add("tags", tagsToRemove.toString())
                .toString();
    }
}
