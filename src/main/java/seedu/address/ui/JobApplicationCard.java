package seedu.address.ui;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.jobapplication.JobApplication;

/**
 * An UI component that displays information of a {@code JobApplication}.
 */
public class JobApplicationCard extends UiPart<Region> {

    private static final String FXML = "JobApplicationListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final JobApplication jobApplication;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label companyName;
    @FXML
    private Label role;
    @FXML
    private Label deadline;
    @FXML
    private Label status;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code JobApplicationCard} with the given {@code JobApplication} and index to display.
     */
    public JobApplicationCard(JobApplication jobApplication, int displayedIndex) {
        super(FXML);
        this.jobApplication = jobApplication;
        id.setText(displayedIndex + ". ");
        companyName.setText(jobApplication.getCompanyName());
        role.setText(jobApplication.getRole());
        deadline.setText(jobApplication.getDeadline().format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")));
        status.setText(jobApplication.getStatus().toString());
        jobApplication.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
