package fshdump;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import fshdump.exc.FileFormatError;
import fshdump.exc.ParseError;
import fshdump.exc.UnexpectedEOIError;
import fshdump.util.Scanner;

public class DumpParser implements iDumpParser {

    /**
     * When this is set to <code>true</code>, the parser will skip
     * preceding and trailing whitespaces of elements. Default is
     * <code>false</code> (whitespaces are then included in the
     * element data).
     */
    public boolean skipWhitespace = false;

    /**
     * The {@link DumpOptions} required for parsing.
     */
    public DumpOptions options;

    /**
     * Reference to the internal {@link Scanner} object used by the
     * parser while parsing.
     */
    private Scanner scanner = null;

    /**
     * Initialize a new DumpParser.
     */
    public DumpParser(DumpOptions options) {
        assert options != null;
        this.options = options;
    }

    /**
     * Initialize a new DumpParser.
     * @param skipWhitespace see {@link #skipWhitespace}
     */
    public DumpParser(DumpOptions options, boolean skipWhitespace) {
        assert options != null;
        this.options = options;
        this.skipWhitespace = skipWhitespace;
    }

    /**
     * Returns true when the parser is currently initialized with input
     * data to be parsed. {@link #parseDump(java.io.Reader, DumpResult)}
     * will automatically initialize and de-initialize the parser.
     */
    public boolean isInitialized() {
        return scanner != null;
    }

    /**
     * Initialize the parser with input data.
     * @param reader The {@link Reader} object providing the data to
     *               be parsed.
     */
    public void initialize(Reader reader) throws IOException {
        assert reader != null;
        if (isInitialized()) {
            throw new RuntimeException("DumpParser already initialized.");
        }
        scanner = new Scanner(reader, false);
        scanner.read();
    }

    /**
     * De-initialize the parser. Allows it to be initialize with
     * {@link #initialize(java.io.Reader)} afterwards.
     * @throws RuntimeException when the parse is not initialized at the
     * point the method is called.
     */
    public void deInitialize() throws IOException {
        if (!isInitialized()) {
            throw new RuntimeException("DumpParser is not initialized.");
        }
        scanner.close();
        scanner = null;
    }

    // Parsing Methods

    /**
     * From the position of the input, read the name of an element.
     * @return The name of the element (may be empty).
     */
    public String readElementName() throws IOException, ParseError {
        if (skipWhitespace) {
            scanner.skip(Scanner.WHITESPACE);
        }

        String midWh = "";
        StringBuilder bld = new StringBuilder();
        while (scanner.current >= 0 &&
               scanner.current != options.separator &&
               scanner.current != options.startTag &&
               scanner.current != options.endTag) {
            if (!midWh.isEmpty()) {
                bld.append(midWh);
            }
            if (scanner.current == '\\') {
                scanner.read();
            }
            bld.append((char) scanner.current);
            scanner.read();
            midWh = scanner.readAlong(Scanner.WHITESPACE);
        }

        // Check if the gathering of characters was because of a
        // valid delimiter.
        if (scanner.current < 0) {
            throw new UnexpectedEOIError("Expected start-tag or separator.");
        }
        else if (scanner.current != options.separator &&
                 scanner.current != options.startTag) {
            throw new FileFormatError("Unexpected character '%c' (%d) at %s", scanner.current,
                                      scanner.current, scanner.getCursor());
        }


        return bld.toString();
    }

    /**
     * From the position of the input, read an element and its optional
     * sub-elements recursively.
     * @param result This objects stats are incremented for each element
     *               found.
     * @return The feed object.
     * @throws IOException From underlying I/O calls.
     * @throws ParseError When the input data is invalid.
     */
    public DataFeedElement readElement(DumpResult result) throws IOException, ParseError {
        DataFeedElement element = new DataFeedElement(readElementName(), false);

        if (scanner.current == options.startTag) {
            result.folderFeeds++;
            element.setHasChildren();
            scanner.read();
            if (skipWhitespace) {
                scanner.skip(Scanner.WHITESPACE);
            }
            while (scanner.current >= 0 &&
                   scanner.current != options.endTag) {
                DataFeedElement child = readElement(result);
                element.add(child);
                if (skipWhitespace) {
                    scanner.skip(Scanner.WHITESPACE);
                }
            }
            if (scanner.current < 0) {
                throw new UnexpectedEOIError("Expected %c (%d) character.", scanner.current,
                                             scanner.current);
            }
            scanner.read();
        }
        else if (scanner.current == options.separator) {
            result.endFeeds++;
            scanner.read();
        }
        else {
            throw new RuntimeException("Algorithmic error, should not reach this statement.");
        }

        // Skip whitespace that possibly follows the data (allowed in strict
        // mode as well).
        scanner.skip(Scanner.WHITESPACE);

        return element;
    }

    // {iDumpParser} Overrides

    @Override
    public DataFeedElement[] parseDump(Reader reader, DumpResult result) throws IOException, ParseError {
        initialize(reader);

        // Read in all top-level elements.
        ArrayList<DataFeedElement> list = new ArrayList<DataFeedElement>();
        while (scanner.current >= 0) {
            DataFeedElement e = readElement(result);
            if (e != null) list.add(e);
        }

        deInitialize();
        return list.toArray(new DataFeedElement[list.size()]);
    }

}
