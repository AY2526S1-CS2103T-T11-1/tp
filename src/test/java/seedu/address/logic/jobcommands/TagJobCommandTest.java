package seedu.address.logic.jobcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.jobcommands.exceptions.JobCommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.model.jobapplication.JobBook;
import seedu.address.model.jobapplication.Model;
import seedu.address.model.jobapplication.ReadOnlyJobBook;
import seedu.address.model.jobapplication.ReadOnlyUserPrefs;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for TagJobCommand.
 */
public class TagJobCommandTest {


    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TagJobCommand(null, null));
    }

    @Test
    public void constructor_validCommand() throws ParseException, JobCommandException {

        Index indexOne = ParserUtil.parseIndex("1");

        Tag swe = new Tag("SWE");
        Tag summer = new Tag("summer");
        Tag sixMonths = new Tag("6-months");


        Set<Tag> tags = new HashSet<>();
        tags.add(swe);
        tags.add(summer);
        tags.add(sixMonths);

        TagJobCommand commandOne = new TagJobCommand(indexOne, tags);

        JobApplication application = new JobApplication("Google", "SWE",
                LocalDateTime.now(), JobApplication.Status.APPLIED, new HashSet<>());

        CommandResult result = commandOne.execute(new ModelStubWithJobApplication(application));

        assertEquals(result.getFeedbackToUser(), TagJobCommand.MESSAGE_TAG_APPLICATION_SUCCESS);

    }



    @Test
    public void execute_exceedMaxTags() throws ParseException {

        Index indexOne = ParserUtil.parseIndex("1");

        Tag swe = new Tag("SWE");
        Tag summer = new Tag("summer");
        Tag sixMonths = new Tag("6-months");
        Tag threeMonths = new Tag("3-months");

        Set<Tag> tags = new HashSet<>();
        tags.add(swe);
        tags.add(summer);
        tags.add(sixMonths);
        tags.add(threeMonths);

        TagJobCommand commandOne = new TagJobCommand(indexOne, tags);

        JobApplication application = new JobApplication("Google", "SWE",
                LocalDateTime.now(), JobApplication.Status.APPLIED, new HashSet<>());

        assertThrows(JobCommandException.class, () -> commandOne.execute(new ModelStubWithJobApplication(application)));
    }

    @Test
    public void toStringMethod() throws ParseException {

        Index indexOne = ParserUtil.parseIndex("1");

        Tag swe = new Tag("SWE");
        Tag summer = new Tag("summer");

        Set<Tag> tags = new HashSet<>();
        tags.add(swe);
        tags.add(summer);

        TagJobCommand commandOne = new TagJobCommand(indexOne, tags);

        String expected = TagJobCommand.class.getCanonicalName()
                + "{targetIndex="
                + indexOne.getZeroBased()
                + ", tags="
                + tags.toString()
                + "}";

        assertEquals(commandOne.toString(), expected);

    }

    @Test
    public void equals() throws ParseException {

        Index indexOne = ParserUtil.parseIndex("1");
        Index indexTwo = ParserUtil.parseIndex("2");

        Tag swe = new Tag("SWE");
        Tag summer = new Tag("summer");
        Tag sixMonths = new Tag("6-months");


        Set<Tag> tagOne = new HashSet<Tag>();
        tagOne.add(swe);
        tagOne.add(summer);

        Set<Tag> tagTwo = new HashSet<Tag>();
        tagOne.add(swe);
        tagOne.add(sixMonths);

        TagJobCommand commandOne = new TagJobCommand(indexOne, tagOne);
        TagJobCommand commandTwo = new TagJobCommand(indexOne, tagOne);


        // Commands have the same fields
        assertEquals(commandOne, commandTwo);

        commandTwo = new TagJobCommand(indexTwo, tagOne);

        // Commands have a difference in index
        assertNotEquals(commandOne, commandTwo);

        commandTwo = new TagJobCommand(indexOne, tagTwo);

        // Commands have a difference in tags
        assertNotEquals(commandOne, commandTwo);

    }

    /**
     * Represents a default stub to default all methods to failing
     */
    private static class ModelStub implements Model {

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getJobBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setJobBookFilePath(Path jobBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setJobBook(ReadOnlyJobBook jobBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyJobBook getJobBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasApplication(JobApplication application) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteJobApplication(JobApplication target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addJobApplication(JobApplication application) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setJobApplication(JobApplication target, JobApplication editedJobApplication) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<JobApplication> getFilteredApplicationList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredJobApplicationList(Predicate<JobApplication> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    private static class ModelStubWithJobApplication extends ModelStub {
        private final FilteredList<JobApplication> list;

        ModelStubWithJobApplication(JobApplication application) {
            JobBook temporaryBook = new JobBook();
            temporaryBook.addApplication(application);
            list = new FilteredList<>(temporaryBook.getApplicationList());

        }

        @Override
        public ObservableList<JobApplication> getFilteredApplicationList() {
            return list;
        }

        @Override
        public void updateFilteredJobApplicationList(Predicate<JobApplication> predicate) {
        }
    }
}
