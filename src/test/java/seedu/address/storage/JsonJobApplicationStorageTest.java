package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.jobapplication.JobApplication;

public class JsonJobApplicationStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonJobApplicationStorageTest");

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

    private static List<JobApplication> getTypicalJobApplications() {
        return Arrays.asList(GOOGLE_APPLICATION, MICROSOFT_APPLICATION, APPLE_APPLICATION);
    }

    private List<JobApplication> readJobApplications(String filePath) throws Exception {
        return new JsonJobApplicationStorage(Paths.get(filePath)).readDataFile(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void readDataFile_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readJobApplications(null));
    }

    @Test
    public void readDataFile_missingFile_emptyResult() throws Exception {
        List<JobApplication> result = readJobApplications("NonExistentFile.json");
        assertTrue(result.isEmpty());
    }

    @Test
    public void readDataFile_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readJobApplications("notJsonFormatJobApplications.json"));
    }

    @Test
    public void readDataFile_invalidJobApplication_throwsDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readJobApplications("invalidJobApplications.json"));
    }

    @Test
    public void readDataFile_invalidAndValidJobApplication_throwsDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readJobApplications("invalidAndValidJobApplications.json"));
    }

    @Test
    public void readAndSaveJobApplications_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempJobApplications.json");
        List<JobApplication> original = getTypicalJobApplications();
        JsonJobApplicationStorage jsonJobApplicationStorage = new JsonJobApplicationStorage(filePath);

        // Save in new file and read back
        jsonJobApplicationStorage.saveJobApplicationData(original, filePath);
        List<JobApplication> readBack = jsonJobApplicationStorage.readDataFile(filePath);
        assertEquals(original, readBack);

        // Modify data, overwrite existing file, and read back
        List<JobApplication> modified = new ArrayList<>(original);
        modified.add(new JobApplication("Netflix", "Data Scientist",
                LocalDateTime.of(2024, 9, 30, 18, 0), JobApplication.Status.APPLIED, new HashSet<>()));
        modified.remove(0); // Remove Google application

        jsonJobApplicationStorage.saveJobApplicationData(modified, filePath);
        readBack = jsonJobApplicationStorage.readDataFile(filePath);
        assertEquals(modified, readBack);

        // Save and read without specifying file path
        List<JobApplication> finalList = new ArrayList<>(modified);
        finalList.add(new JobApplication("Amazon", "Cloud Engineer",
                LocalDateTime.of(2024, 8, 15, 14, 30), JobApplication.Status.INPROGRESS, new HashSet<>()));

        jsonJobApplicationStorage.saveJobApplicationData(finalList); // file path not specified
        readBack = jsonJobApplicationStorage.readDataFile(); // file path not specified
        assertEquals(finalList, readBack);
    }

    @Test
    public void saveJobApplicationData_nullJobApplications_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveJobApplications(null, "SomeFile.json"));
    }

    /**
     * Saves {@code jobApplications} at the specified {@code filePath}.
     */
    private void saveJobApplications(List<JobApplication> jobApplications, String filePath) {
        try {
            new JsonJobApplicationStorage(Paths.get(filePath))
                    .saveJobApplicationData(jobApplications, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveJobApplicationData_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveJobApplications(new ArrayList<>(), null));
    }

    @Test
    public void getDataFilePath_returnsCorrectPath() {
        Path expectedPath = Paths.get("test", "data.json");
        JsonJobApplicationStorage storage = new JsonJobApplicationStorage(expectedPath);

        assertEquals(expectedPath, storage.getDataFilePath());
    }

    @Test
    public void readDataFile_validJsonFile_success() throws Exception {
        Path filePath = testFolder.resolve("ValidJobApplications.json");
        JsonJobApplicationStorage storage = new JsonJobApplicationStorage(filePath);

        List<JobApplication> applications = getTypicalJobApplications();
        storage.saveJobApplicationData(applications, filePath);

        List<JobApplication> readBack = storage.readDataFile(filePath);
        assertEquals(applications.size(), readBack.size());

        for (int i = 0; i < applications.size(); i++) {
            JobApplication expected = applications.get(i);
            JobApplication actual = readBack.get(i);

            assertEquals(expected, actual);
        }
    }

}
