package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.model.jobapplication.ReadOnlyUserPrefs;
import seedu.address.model.jobapplication.UserPrefs;

/**
 * Manages storage of Job Application data in local storage.
 */
public class DataStorageManager implements DataStorage {

    private static final Logger logger = LogsCenter.getLogger(DataStorageManager.class);
    private final JobApplicationStorage jobApplicationStorage;
    private final UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code JobApplicationStorage} and {@code UserPrefStorage}.
     */
    public DataStorageManager(JobApplicationStorage jobApplicationStorage, UserPrefsStorage userPrefsStorage) {
        this.jobApplicationStorage = jobApplicationStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ HustleHub methods ==============================

    @Override
    public Path getDataFilePath() {
        return jobApplicationStorage.getDataFilePath();
    }

    @Override
    public List<JobApplication> readDataFile() throws DataLoadingException {
        return readDataFile(jobApplicationStorage.getDataFilePath());
    }

    @Override
    public List<JobApplication> readDataFile(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return jobApplicationStorage.readDataFile(filePath);
    }

    @Override
    public void saveJobApplicationData(List<JobApplication> jobApplications) throws IOException {
        saveJobApplicationData(jobApplications, jobApplicationStorage.getDataFilePath());
    }

    @Override
    public void saveJobApplicationData(List<JobApplication> jobApplications, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        jobApplicationStorage.saveJobApplicationData(jobApplications, filePath);
    }

}
