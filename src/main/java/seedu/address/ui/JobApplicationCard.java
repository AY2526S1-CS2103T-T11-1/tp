package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.jobapplication.JobApplication;

/**
 * An UI component that displays information of a {@code Person}.
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
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code JobApplicationCard} with the given {@code Person} and index to display.
     */
    public JobApplicationCard(JobApplication jobApplication, int displayedIndex) {
        super(FXML);
        this.jobApplication = jobApplication;
        id.setText(displayedIndex + ". ");
        name.setText(jobApplication.getCompanyName().toString());
        phone.setText(jobApplication.getRole().toString());
        address.setText(jobApplication.getDeadline().toString());
        email.setText(jobApplication.getStatus().toString());

    }
}
