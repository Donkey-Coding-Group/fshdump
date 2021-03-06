package fshdump;

import java.io.IOException;
import java.io.Writer;

/**
 * This {@link iDumpWriter} implementation writes the strict FSHDump
 * file format.
 */
public class StrictDumpWriter implements iDumpWriter {

    // iDumpWriter -- Overrides

    @Override
    public void writeFeed(DumpOptions options, DumpResult result,
                          Writer writer, iHierarchyFeed feed)
            throws IOException {
        String data = feed.getData();
        if (data.isEmpty() && !options.allowEmptyData) {
            return;
        }

        writer.write(options.escapeData(data));
        iHierarchyFeed[] childFeeds = feed.getChildFeeds();
        if (childFeeds == null) {
            result.endFeeds++;
            writer.write(options.separator);
        }
        else {
            result.folderFeeds++;
            writer.write(options.startTag);
            for (iHierarchyFeed child: childFeeds) {
                writeFeed(options, result, writer, child);
            }
            writer.write(options.endTag);
        }
    }

}
