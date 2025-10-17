package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.model.jobapplication.UserPrefs;

public class DataStorageManagerTest {

    private static final JobApplication GOOGLE_APPLICATION = new JobApplication(
            "Google", "Software Engineer", LocalDateTime.of(2024, 12, 31, 23, 59),
            JobApplication.Status.APPLIED, new HashSet<>());

    private static final JobApplication MICROSOFT_APPLICATION = new JobApplication(
            "Microsoft", "Product Manager", LocalDateTime.of(2024, 11, 15, 17, 30),
            JobApplication.Status.INPROGRESS, new HashSet<>());

    private static final JobApplication APPLE_APPLICATION = new JobApplication(
            "Apple", "iOS Developer", LocalDateTime.of(2024, 10, 20, 12, 0),
            JobApplication.Status.REJECTED, new HashSet<>());

    @TempDir
    public Path testFolder;

    private DataStorageManager dataStorageManager;


    private static List<JobApplication> getTypicalJobApplications() {
        return Arrays.asList(GOOGLE_APPLICATION, MICROSOFT_APPLICATION, APPLE_APPLICATION);
    }

    @BeforeEach
    public void setUp() {
        JsonJobApplicationStorage jobApplicationStorage =
                new JsonJobApplicationStorage(getTempFilePath("jobapps.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs.json"));
        dataStorageManager = new DataStorageManager(jobApplicationStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        dataStorageManager.saveUserPrefs(original);
        UserPrefs retrieved = dataStorageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void jobApplicationsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonJobApplicationStorge} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonJobApplicationStorgeTest} class.
         */
        List<JobApplication> original = getTypicalJobApplications();
        dataStorageManager.saveJobApplicationData(original);
        List<JobApplication> retrieved = dataStorageManager.readDataFile();
        assertEquals(original, retrieved);
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(dataStorageManager.getDataFilePath());
    }
}
