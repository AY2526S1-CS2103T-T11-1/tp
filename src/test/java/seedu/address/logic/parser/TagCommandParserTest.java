package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.JobCommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.JobCommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.jobcommands.TagJobCommand;
import seedu.address.model.tag.Tag;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the TagJobCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the TagJobCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class TagCommandParserTest {
    private TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_validArgs_returnsTagJobCommand() {
        Index targetIndex = Index.fromOneBased(1);
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("Intern"));
        tags.add(new Tag("6-month"));

        TagJobCommand expectedCommand = new TagJobCommand(targetIndex, tags);
        assertParseSuccess(parser, "1 t/Intern t/6-month", expectedCommand);
    }

    @Test
    public void parse_validArgsWithSingleTag_returnsTagJobCommand() {
        Index targetIndex = Index.fromOneBased(2);
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("Remote"));

        TagJobCommand expectedCommand = new TagJobCommand(targetIndex, tags);
        assertParseSuccess(parser, "2 t/Remote", expectedCommand);
    }

    @Test
    public void parse_validArgsWithMultipleTags_returnsTagJobCommand() {
        Index targetIndex = Index.fromOneBased(3);
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("FullTime"));
        tags.add(new Tag("OnSite"));
        tags.add(new Tag("Senior"));

        TagJobCommand expectedCommand = new TagJobCommand(targetIndex, tags);
        assertParseSuccess(parser, "3 t/FullTime t/OnSite t/Senior", expectedCommand);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagJobCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        assertParseFailure(parser, "t/Intern",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagJobCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingTags_throwsParseException() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagJobCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "0 t/Intern",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagJobCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "-1 t/Intern",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagJobCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "a t/Intern",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagJobCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTagFormat_throwsParseException() {
        // Empty tag
        assertParseFailure(parser, "1 t/",
                Tag.MESSAGE_CONSTRAINTS);

        // Tag with spaces
        assertParseFailure(parser, "1 t/Invalid Tag",
                Tag.MESSAGE_CONSTRAINTS);

        // Tag with special characters
        assertParseFailure(parser, "1 t/Tag@123",
                Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicateTags_returnsTagJobCommandWithoutDuplicates() {
        Index targetIndex = Index.fromOneBased(1);
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("Intern"));

        TagJobCommand expectedCommand = new TagJobCommand(targetIndex, tags);
        // Duplicate tags should be automatically handled by Set
        assertParseSuccess(parser, "1 t/Intern t/Intern", expectedCommand);
    }

    @Test
    public void parse_extraWhitespace_returnsTagJobCommand() {
        Index targetIndex = Index.fromOneBased(1);
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("Intern"));
        tags.add(new Tag("Summer"));

        TagJobCommand expectedCommand = new TagJobCommand(targetIndex, tags);
        // Extra whitespace should be trimmed
        assertParseSuccess(parser, "  1   t/Intern   t/Summer  ", expectedCommand);
    }

    @Test
    public void parse_mixedCaseTags_returnsTagJobCommand() {
        Index targetIndex = Index.fromOneBased(1);
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("INTERN"));
        tags.add(new Tag("summer"));
        tags.add(new Tag("Remote"));

        TagJobCommand expectedCommand = new TagJobCommand(targetIndex, tags);
        assertParseSuccess(parser, "1 t/INTERN t/summer t/Remote", expectedCommand);
    }

    @Test
    public void parse_validIndexWithNoTags_throwsParseException() {
        assertParseFailure(parser, "1 t/",
                Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_preambleWithExtraContent_throwsParseException() {
        // Preamble should only contain the index
        assertParseFailure(parser, "1 extra content t/Intern",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagJobCommand.MESSAGE_USAGE));
    }
}
