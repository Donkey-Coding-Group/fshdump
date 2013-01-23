package fshdump;

import java.io.IOException;
import java.io.Reader;

/**
 * Interface for parsing dump files.
 */
public interface iDumpParser {

    public DataFeedElement[] parseDump(Reader input, DumpOptions options) throws IOException, DamagedFileException;

}
