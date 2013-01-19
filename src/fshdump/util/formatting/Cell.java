package fshdump.util.formatting;

/**
 * This class represents a single cell in a table containing formatted
 * and aligned text. A single cell manages the inserted text to ensure
 * the text does not overflow the specified maximum width and line
 * number. Overflowing text will be replaced by an ellipsis.
 *
 * {@code
 *     Cell cell = new Cell("Sample text for a single cell.", 20);
 * }
 */
public class Cell implements Formatter {

    /**
     * The text the cell is formatting.
     */
    public String text = null;

    /**
     * The maximum number of characters the cell is allowed to put in
     * one row. A negative value allows the cell to infinitely put
     * characters on the same row.
     */
    public int maxWidth = -1;

    /**
     * The maximum number of lines the cell is allowed to put. A
     * negative value allows the cell to put as many lines as required.
     */
    public int maxLines = -1;

    /**
     * This string represents the ellipsis that is inserted when the
     * formatted text does not fit in the cell (exceeds the width and
     * height).
     */
    public String ellipsis = "...";

    /**
     * Initialize an empty cell.
     */
    public Cell() {
    }

    /**
     * Initialize a cell with the passed text.
     * @param text The text or null.
     */
    public Cell(String text) {
        this.text = text;
    }

    /**
     * Initialize a cell with the passed text and maximum width.
     * @param text The text or null.
     * @param maxWidth The maximum width.
     */
    public Cell(String text, int maxWidth) {
        this.text = text;
        this.maxWidth = maxWidth;
    }

    /**
     * Initialize a cell with the passed text and maximum width and
     * height.
     * @param text The text or null.
     * @param maxWidth The maximum width.
     * @param maxLines THe maximum height;
     */
    public Cell(String text, int maxWidth, int maxLines) {
        this.text = text;
        this.maxWidth = maxWidth;
        this.maxLines = maxLines;
    }

    // {Formatter} Overrides

    @Override
    public CompiledFormatter compileFormatter() {
        String text = this.text;
        if (text == null) text = "";
        return new CompiledCell(text, ellipsis, maxWidth, maxLines);
    }

}
