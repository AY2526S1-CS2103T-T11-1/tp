package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.storage.SerializableJobApplication.MISSING_FIELD_MESSAGE_FORMAT;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.model.tag.Tag;

public class SerializableJobApplicationTest {

    // Valid test data
    private static final String VALID_COMPANY_NAME = "Google";
    private static final String VALID_ROLE = "Software Engineer";
    private static final LocalDateTime VALID_DEADLINE = LocalDateTime.of(2024, 12, 31, 23, 59);
    private static final JobApplication.Status VALID_STATUS = JobApplication.Status.APPLIED;
    private static final Set<Tag> TAGS = new HashSet<>();

    // Invalid test data
    private static final String INVALID_DEADLINE = "invalid-date";
    private static final String INVALID_STATUS = "INVALID_STATUS";

    @Test
    public void toModelType_validSerializableJobApplication_returnsJobApplication() throws Exception {
        JobApplication jobApplication = new JobApplication(VALID_COMPANY_NAME,
            VALID_ROLE, VALID_DEADLINE, VALID_STATUS, TAGS);
        SerializableJobApplication serializableJobApplication = new SerializableJobApplication(jobApplication);

        // Verify that the serializable object can be converted back to model type
        assertEquals(serializableJobApplication.toModelType(), jobApplication);
    }

    @Test
    public void toModelType_missingCompanyName_throwsIllegalValueException() {
        SerializableJobApplication serializableJobApplication = new SerializableJobApplication(
            null, VALID_ROLE, VALID_DEADLINE.toString(), VALID_STATUS.name(), new ArrayList<>());

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Company Name");
        IllegalValueException exception = assertThrows(IllegalValueException.class,
                serializableJobApplication::toModelType);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void toModelType_missingRole_throwsIllegalValueException() {
        SerializableJobApplication serializableJobApplication = new SerializableJobApplication(
            VALID_COMPANY_NAME, null, VALID_DEADLINE.toString(), VALID_STATUS.name(), new ArrayList<>());

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Role");
        IllegalValueException exception = assertThrows(IllegalValueException.class,
            serializableJobApplication::toModelType);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void toModelType_missingDeadline_throwsIllegalValueException() {
        SerializableJobApplication serializableJobApplication = new SerializableJobApplication(
            VALID_COMPANY_NAME, VALID_ROLE, null, VALID_STATUS.name(), new ArrayList<>());

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Deadline");
        IllegalValueException exception = assertThrows(IllegalValueException.class,
            serializableJobApplication::toModelType);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void toModelType_missingStatus_throwsIllegalValueException() {
        SerializableJobApplication serializableJobApplication = new SerializableJobApplication(
            VALID_COMPANY_NAME, VALID_ROLE, VALID_DEADLINE.toString(), null, new ArrayList<>());

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Status");
        IllegalValueException exception = assertThrows(IllegalValueException.class,
            serializableJobApplication::toModelType);
        assertEquals(expectedMessage, exception.getMessage());
    }
}
