package seedu.address.model.jobapplication;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Job Application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class JobApplication {

    /**
     * Represents the status of a job application.
     */
    public enum Status {
        APPLIED,
        INPROGRESS,
        REJECTED
    }

    // Limiter on number of tags
    public static final int MAX_TAGS = 3;

    // Identity fields
    private String companyName;
    private String role;

    // Data fields
    private LocalDateTime deadline;
    private Status status;
    private Set<Tag> tags;

    /**
     * Constructs a JobApplication.
     * Every field must be present and not null.
     *
     * @param companyName The name of the company.
     * @param role The job role applied for.
     * @param deadline The application deadline.
     * @param status The current status of the application.
     */
    public JobApplication(String companyName, String role, LocalDateTime deadline, Status status, Set<Tag> tags) {
        requireAllNonNull(companyName, role, deadline, status);
        this.companyName = companyName;
        this.role = role;
        this.deadline = deadline;
        this.status = status;
        this.tags = tags;
    }

    /**
     * Returns the company name.
     *
     * @return The name of the company.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Returns the job role.
     *
     * @return The job role applied for.
     */
    public String getRole() {
        return role;
    }

    /**
     * Returns the application deadline.
     *
     * @return The deadline for the job application.
     */
    public LocalDateTime getDeadline() {
        return deadline;
    }

    /**
     * Returns the application status.
     *
     * @return The current status of the application.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Returns the list of tags
     *
     * @return The list of tags
     */
    public Set<Tag> getTags() {
        return tags;
    }

    /**
     * Sets the list of tags
     */
    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Returns true if both job applications have the same company name and role.
     * This defines a weaker notion of equality between two job applications.
     *
     * @param otherJobApplication The other job application to compare with.
     * @return True if both job applications have the same company name and role.
     */
    public boolean isSameJobApplication(JobApplication otherJobApplication) {
        if (otherJobApplication == this) {
            return true;
        }

        return otherJobApplication != null
                && otherJobApplication.companyName.equals(this.companyName)
                && otherJobApplication.role.equals(this.role);
    }

    /**
     * Returns true if both job applications have the same identity and data fields.
     * This defines a stronger notion of equality between two job applications.
     *
     * @param other The other object to compare with.
     * @return True if both job applications are equal in all fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof JobApplication)) {
            return false;
        }

        JobApplication otherJobApplication = (JobApplication) other;
        return companyName.equals(otherJobApplication.companyName)
            && role.equals(otherJobApplication.role)
            && deadline.equals(otherJobApplication.deadline)
            && status.equals(otherJobApplication.status);
    }

    /**
     * Returns the hash code for this job application.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(companyName, role, deadline, status);
    }

    /**
     * Returns a string representation of this job application.
     *
     * @return A string containing all job application details.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("companyName", companyName)
            .add("role", role)
            .add("deadline", deadline)
            .add("status", status)
            .toString();
    }
}
