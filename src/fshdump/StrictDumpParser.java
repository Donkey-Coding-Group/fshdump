package fshdump;

import fshdump.util.Scanner;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

/**
 * This class implements the DumpParser to parse strict dump format.
 */
public class StrictDumpParser implements iDumpParser {

    private String readElementName(Scanner scanner, DumpOptions options) throws IOException, DamagedFileException {
        StringBuilder builder = new StringBuilder();
        while (scanner.current >= 0 &&
               scanner.current != options.startTag &&
               scanner.current != options.separator) {
            if (scanner.current == '\\') {
                scanner.read();
            }
            builder.append((char) scanner.current);
            scanner.read();
        }
        String name = builder.toString();

        if (scanner.current <= 0) {
            throw new DamagedFileException(String.format(
                    "reached unexpected EOF at %s", scanner.getCursor()));
        }

        return name;
    }

    private DataFeedElement readElement(Scanner reader, DumpOptions options) throws IOException, DamagedFileException {
        String name = readElementName(reader, options);
        if (name.isEmpty()) return null;
        DataFeedElement element = new DataFeedElement(name, false);
        if (reader.current == options.startTag) {
            element.setHasChildren();
            reader.read();
            while (reader.current >= 0 && reader.current != options.endTag) {
                DataFeedElement child = readElement(reader, options);
                if (child != null) {
                    element.add(child);
                }
            }
            if (reader.current != options.endTag) {
                throw new DamagedFileException(String.format("expected %c, got %c",
                        options.endTag, (char) reader.current));
            }
            reader.read();
        }
        else if (reader.current == options.separator) {
            reader.read();
        }
        else {
            throw new DamagedFileException(String.format("expected %c or %c, got %c",
                    options.startTag, options.separator, (char) reader.current));
        }

        return element;
    }

    // {iDumpWriter} Overrides

    @Override
    public DataFeedElement[] parseDump(Reader source, DumpOptions options) throws IOException, DamagedFileException {
        Scanner reader = new Scanner(source, false);
        reader.read();
        ArrayList<DataFeedElement> elements = new ArrayList<DataFeedElement>();
        while (reader.current >= 0) {
            DataFeedElement element = readElement(reader, options);
            if (element == null) {
                System.out.println(reader.current);
            }
            else {
                elements.add(element);
            }
        }
        return elements.toArray(new DataFeedElement[elements.size()]);
    }

}
