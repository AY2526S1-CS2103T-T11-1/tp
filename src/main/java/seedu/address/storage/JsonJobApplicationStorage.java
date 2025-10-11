package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.jobapplication.JobApplication;

/**
 * A class to access Job Application data stored as a csv file on the hard disk.
 */
public class JsonJobApplicationStorage implements JobApplicationStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonJobApplicationStorage.class);

    private Path filePath;

    public JsonJobApplicationStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getDataFilePath() {
        return filePath;
    }

    @Override
    public List<JobApplication> readDataFile() throws DataLoadingException {
        return readDataFile(filePath);
    }

    /**
     * Similar to {@link #readDataFile()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public List<JobApplication> readDataFile(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableJobApplicationList> loadedApplications = JsonUtil.readJsonFile(
                filePath, JsonSerializableJobApplicationList.class);

        if (!loadedApplications.isPresent()) {
            return new ArrayList<>();
        }

        try {
            return loadedApplications.get().toModelType();
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveJobApplicationData(List<JobApplication> jobApplications) throws IOException {
        saveJobApplicationData(jobApplications, filePath);
    }

    /**
     * Similar to {@link #saveJobApplicationData(List)}}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveJobApplicationData(List<JobApplication> jobApplications, Path filePath) throws IOException {
        requireNonNull(jobApplications);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        List<SerializableJobApplication> serializableJobApplications = new ArrayList<>();
        for (JobApplication application: jobApplications) {
            serializableJobApplications.add(new SerializableJobApplication(application));
        }
        JsonUtil.saveJsonFile(new JsonSerializableJobApplicationList(serializableJobApplications), filePath);
    }

}
