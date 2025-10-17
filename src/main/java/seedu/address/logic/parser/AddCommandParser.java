package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.jobcommands.AddJobCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddJobCommand object
 */
public class AddCommandParser implements JobParser<AddJobCommand> {

    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Parses the given {@code String} of arguments in the context of the AddJobCommand
     * and returns an AddJobCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddJobCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ROLE, PREFIX_STATUS, PREFIX_DEADLINE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ROLE, PREFIX_STATUS, PREFIX_DEADLINE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddJobCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_ROLE, PREFIX_STATUS, PREFIX_DEADLINE);

        try {
            String companyName = argMultimap.getValue(PREFIX_NAME).get();
            String role = argMultimap.getValue(PREFIX_ROLE).get();
            String deadlineStr = argMultimap.getValue(PREFIX_DEADLINE).get();
            String statusStr = argMultimap.getValue(PREFIX_STATUS).get();
            Set<Tag> tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            if (tags.size() > JobApplication.MAX_TAGS) {
                throw new IllegalArgumentException("Maximum number of tags per application is: "
                        + JobApplication.MAX_TAGS);
            }

            LocalDateTime deadline = LocalDateTime.parse(deadlineStr, DATETIME_FORMATTER);
            JobApplication.Status status = JobApplication.Status.valueOf(statusStr.toUpperCase());

            JobApplication application = new JobApplication(companyName, role, deadline, status, tags);
            return new AddJobCommand(application);
        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid deadline format. Expected format: yyyy-MM-ddTHH:mm", e);
        } catch (IllegalArgumentException e) {
            throw new ParseException("Invalid status. Valid values are: APPLIED, INPROGRESS, REJECTED", e);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
