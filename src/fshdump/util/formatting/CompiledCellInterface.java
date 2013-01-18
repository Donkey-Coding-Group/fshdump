package fshdump.util.formatting;

/**
 * This class defines the interface for a compiled cell. It is used
 * to finally render the formatted text.
 */
public interface CompiledCellInterface {

    /**
     * This method returns the maximum number of characters a line
     * returned by {@link #getLine(int)} will contain.
     * @return The maximum width.
     */
    public int getMaxWidth();

    /**
     * This method returns the number of lines it will be able to
     * return from {@link #getLine(int)}.
     * @return The number of lines.
     */
    public int getLineCount();

    /**
     * This method returns the line at the specified index. The length
     * of the returned string must not exceed the value returned by
     * {@link #getMaxWidth()}. If the passed index exceeds the value
     * returned by {@link #getLineCount()}, an empty string must be
     * returned.
     * @param index The index of the line to retrieve.
     * @return The line at the specified index.
     */
    public String getLine(int index);

}
