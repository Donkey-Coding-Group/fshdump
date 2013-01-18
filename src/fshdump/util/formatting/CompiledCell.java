package fshdump.util.formatting;

import java.util.ArrayList;
import static fshdump.util.formatting.StringUtils.substringBounds;

final class CompiledCell implements CompiledCellInterface {

    /**
     * This array contains the wrapped lines.
     */
    private ArrayList<String> lines = new ArrayList<String>();

    /**
     * This value contains the length of the longest string in the
     * {@link #lines} array.
     */
    private int maxLinesLength;

    /**
     * The ellipsis being used to replace overflowing text.
     */
    private String ellipsis;

    /**
     * This is the maximum number of characters allowed in one line.
     */
    private int maxWidth;

    /**
     * This is the maximum number of line passed on construction, which
     * is not necessarily equal to the actual number of lines in the
     * cell.
     */
    private int maxLines;

    /**
     * At compile time (constructor), this variable is increased
     * with each line added.
     */
    private int lineCount;

    /**
     * Initialize the CompiledCell instance. The object is in a
     * compiled state and cannot be modified after it was
     * initialized.
     * @param text The text to wrap.
     * @param maxWidth The maximum width.
     * @param maxLines the maximum height.
     */
    public CompiledCell(String text, String ellipsis, int maxWidth, int maxLines) {
        this.ellipsis = ellipsis;
        this.maxWidth = maxWidth;
        this.maxLines = maxLines;
        this.lineCount = 0;
        this.maxLinesLength = 0;
        this.lines = new ArrayList<String>();

        if (maxWidth == 0) return;
        if (maxLines == 0) return;

        // Create a list of words so we can wrap them accordingly.
        String[] words = text.split("\\s+");

        // Iterate over each word and push it to the current line.
        String currentLine = "";
        for (String word: words) {
            currentLine = pushWord(currentLine, word);
            if (lineCount >= maxLines) break;
        }

        // Add the last line to the liens of the cell.
        currentLine = currentLine.trim();
        if (!currentLine.isEmpty()) {
            if (maxLines >= 0 && lineCount >= maxLines) {
                throw new UnknownError("Algorithmic error, see source code");
            }
            else
                pushLine(currentLine);
        }

    }

    // private Methods

    /**
     * Pushes the passed word onto the current line or into the next
     * line, based on the maximum width and height. It may also be
     * partially replaced by the {@link #ellipsis} if it overflows
     * the width and height of the cell.
     * @param currentLine The current line.
     * @param word The word to push.
     * @return The new value of the current line.
     */
    private String pushWord(String currentLine, String word) {
        int currentLineLength = currentLine.length();
        int wordLength = word.length();

        if (maxWidth >= 0 && currentLineLength + wordLength > maxWidth) {
            // Check if we're about to exceed all limits of the cell.
            if (maxLines >= 0 && lineCount == maxLines - 1) {
                // And add an ellipsis if this is the case.
                int length = maxWidth - ellipsis.length();
                if (length <= 0) {
                    currentLine = substringBounds(ellipsis, 0, maxWidth);
                }
                else {
                    currentLine = substringBounds(currentLine, 0, length);
                    currentLine += ellipsis;
                }

                pushLine(currentLine);
                currentLine = "";
            }

            // Okay, we have at least one line left after the current
            // line was added.
            else {
                // If the wordLength exceeds the actual maximum width
                // of a line, we'll force it into the line breaking
                // it where it is necessary to do.
                if (wordLength > maxWidth) {
                    int difference = maxWidth - currentLineLength;
                    currentLine += substringBounds(word, 0, difference) + " ";
                    word = substringBounds(word, difference);
                    pushLine(currentLine);

                    // Add the rest of the word, on multiple lines if
                    // necessary.
                    while (word.length() > maxWidth) {
                        if (maxLines >= 0 && lineCount == maxLines - 1) {
                            // We're at the list line we can push a
                            // word on to.
                            return pushWord(word, "");
                        }

                        pushLine(substringBounds(word, 0, maxWidth));
                        word = substringBounds(word, maxWidth);
                    }

                    // The rest of the word that does fit into the
                    // line will be set to the currentLine.
                    currentLine = word + " ";
                }

                // If the word itself does not exceed the maximum line
                // length, it will be put into the next line.
                else {
                    String trimmedLine = currentLine.trim();
                    pushLine(trimmedLine);
                    currentLine = word + " ";
                }
            }
        }

        // The word fits into the current line.
        else {
            currentLine += word + " ";
        }

        return currentLine;
    }

    /**
     * Adds a line in its final state to the list of lines. Increases
     * the lineCount respectively and adjusts {@link #maxLinesLength}.
     */
    private void pushLine(String line) {
        line = line.trim();
        lines.add(line);
        lineCount++;
        int lineLength = line.length();
        if (lineLength > maxLinesLength) {
            maxLinesLength = lineLength;
        }
    }

    // {CompiledCellInterface} Overrides

    @Override
    public int getMaxWidth() {
        return maxLinesLength;
    }

    @Override
    public int getLineCount() {
        return lineCount;
    }

    @Override
    public String getLine(int index) {
        if (index > lineCount) {
            return "";
        }
        else {
            return lines.get(index);
        }
    }

}