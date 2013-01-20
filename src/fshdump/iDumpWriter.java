package fshdump;

import java.io.IOException;
import java.io.Writer;

/**
 * Implementations of this interface will write {@link iHierarchyFeed}
 * objects in the FSHDump file format.
 */
public interface iDumpWriter {

    /**
     * Recursively write the passed {@link iHierarchyFeed} object to
     * the output stream.
     * @param options The options for the file format.
     * @param writer Destination output stream.
     * @param feed The {@link iHierarchyFeed} object to write.
     * @throws IOException From underlying I/O calls.
     */
    public void writeFeed(DumpOptions options, DumpResult result,
                          Writer writer, iHierarchyFeed feed)
            throws IOException;

}
