package fshdump;

import java.io.IOException;
import java.io.Reader;
import fshdump.exc.ParseError;

/**
 * Interface for parsing dump files.
 */
public interface iDumpParser {

    public DataFeedElement[] parseDump(Reader input, DumpResult result) throws IOException, ParseError;

}
