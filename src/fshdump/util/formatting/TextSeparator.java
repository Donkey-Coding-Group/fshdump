package fshdump.util.formatting;

/**
 * This interface describes an object that delivers strings for
 * visually separating text.
 */
public interface TextSeparator {

    /**
     * Temporarily sets the horizontal offset which must be saved
     * locally.
     * @param offset The offset value.
     */
    public void setHorizontalOffset(int offset);

    /**
     * Temporarily sets the vertical offset which must be saved
     * locally.
     * @param offset The offset value.
     */
    public void setVerticalOffset(int offset);

    /**
     * Return a horizontal separator with the specified length.
     * @param length The length of requested separator.
     * @return A String with the specified length.
     */
    public String getHorizontalSeparator(int length);

    /**
     * Return a vertical separator which is invoked from the
     * passed
     * @param lineIndex
     * @return
     */
    public String getVerticalSeparator(int lineIndex);

}
