package fshdump.util.formatting;

/**
 * Provides static methods for working with string.
 */
final public class StringUtils {

    private StringUtils() {
    }

    /**
     * Returns <code>source.substring(start);</code> but clips
     * the start value before calling the method.
     * @param source The source string.
     * @param start The start index.
     * @return The new String.
     */
    public static String substringBounds(String source, int start) {
        int length = source.length();
        if (start > length) {
            start = length;
        }
        else if (start < 0) {
            start = 0;
        }
        return source.substring(start);
    }

    /**
     * Returns <code>source.substring(start, end);</code> but clips
     * the start and end value before calling the method.
     * @param source The source string.
     * @param start The start index.
     * @param end The end index.
     * @return The new String.
     */
    public static String substringBounds(String source, int start, int end) {
        int length = source.length();
        if (start > length) {
            start = length;
        }
        else if (start < 0) {
            start = 0;
        }
        if (end > length) {
            end = length;
        }
        else if (end < 0) {
            end = 0;
        }
        return source.substring(start, end);
    }


}
