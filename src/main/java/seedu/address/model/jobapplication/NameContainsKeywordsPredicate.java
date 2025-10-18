package seedu.address.model.jobapplication;

import java.util.List;
import java.util.stream.Collectors;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code JobApplication}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<JobApplication> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords.stream()
                                .filter(w -> !w.isEmpty())
                                .collect(Collectors.toList());
    }

    @Override
    public boolean test(JobApplication jobApplication) {
        String company = jobApplication.getCompanyName();
        return keywords.stream().anyMatch(
            keyword -> StringUtil.containsWordIgnoreCase(company, keyword)
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (NameContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
