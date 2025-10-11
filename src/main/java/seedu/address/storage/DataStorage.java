package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.model.jobapplication.ReadOnlyUserPrefs;
import seedu.address.model.jobapplication.UserPrefs;

/**
 * API of the Storage component for HustleHub.
 */
public interface DataStorage extends JobApplicationStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getDataFilePath();

    @Override
    List<JobApplication> readDataFile() throws DataLoadingException;

    @Override
    void saveJobApplicationData(List<JobApplication> jobApplicationList) throws IOException;

}

