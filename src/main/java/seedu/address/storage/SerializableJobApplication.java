package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link JobApplication}.
 */
public class SerializableJobApplication {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Job Application's %s field is missing!";

    private final String companyName;
    private final String role;
    private final String deadline;
    private final String status;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code SerializableJobApplication} with the given job application details.
     */
    @JsonCreator
    public SerializableJobApplication(@JsonProperty("companyName") String companyName,
                                      @JsonProperty("role") String role,
                                      @JsonProperty("deadline") String deadline,
                                      @JsonProperty("status") String status,
                                      @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.companyName = companyName;
        this.role = role;
        this.deadline = deadline;
        this.status = status;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code JobApplication} into this class for Jackson use.
     */
    public SerializableJobApplication(JobApplication application) {
        companyName = application.getCompanyName();
        role = application.getRole();
        deadline = application.getDeadline().toString();
        status = application.getStatus().name();
        tags.addAll(application.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code JobApplication} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public JobApplication toModelType() throws IllegalValueException {

        if (companyName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Company Name"));
        }
        if (role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Role"));
        }
        if (deadline == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Deadline"));
        }
        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Status"));
        }

        final List<Tag> tagList = new ArrayList<>();

        for (JsonAdaptedTag tag : tags) {
            tagList.add(tag.toModelType());
        }

        final Set<Tag> tags = new HashSet<>(tagList);

        return new JobApplication(companyName, role,
            LocalDateTime.parse(deadline), JobApplication.Status.valueOf(status), tags);
    }

}
