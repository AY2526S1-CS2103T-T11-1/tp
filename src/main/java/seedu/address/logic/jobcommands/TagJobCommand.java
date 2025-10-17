package seedu.address.logic.jobcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.jobapplication.JobApplication.MAX_TAGS;
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
 * Tags a job application with a number of short tags.
 */
public class TagJobCommand extends Command {

    public static final String MESSAGE_TAG_APPLICATION_SUCCESS = "Application has been tagged!";
    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_MAX_TAGS = "Each application can have up to " + MAX_TAGS + " tags.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tags a job application.\n"
            + "Parameters: index_name t/tag t/tag t/tag\n"
            + "Example: " + COMMAND_WORD + " 1 t/Intern t/Summer t/on-site";

    protected final Index targetIndex;
    private final Set<Tag> tags;

    /**
     * Creates an AddJobCommand to add the specified {@code JobApplication}
     */
    public TagJobCommand(Index targetIndex, Set<Tag> tags) {
        requireNonNull(targetIndex);
        requireNonNull(tags);
        this.targetIndex = targetIndex;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws JobCommandException {
        requireNonNull(model);

        List<JobApplication> lastShownList = model.getFilteredApplicationList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new JobCommandException(JobMessages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }

        JobApplication jobToTag = lastShownList.get(targetIndex.getZeroBased());
        this.tags.addAll(jobToTag.getTags());

        if (this.tags.size() > MAX_TAGS) {
            throw new JobCommandException(MESSAGE_MAX_TAGS);
        }

        jobToTag.setTags(tags);

        // Update the viewed job applications
        model.updateFilteredJobApplicationList(PREDICATE_SHOW_ALL_APPLICATIONS);

        return new CommandResult(String.format(MESSAGE_TAG_APPLICATION_SUCCESS, JobMessages.format(jobToTag)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagJobCommand)) {
            return false;
        }

        TagJobCommand otherTagCommand = (TagJobCommand) other;
        boolean areTagsEqual = this.tags.equals(otherTagCommand.tags);
        boolean isIndexSame = targetIndex.equals(otherTagCommand.targetIndex);

        return areTagsEqual && isIndexSame;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex.getZeroBased())
                .add("tags", tags.toString())
                .toString();
    }
}
