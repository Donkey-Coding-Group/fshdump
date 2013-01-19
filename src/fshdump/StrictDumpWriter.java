package fshdump;

import java.io.IOException;
import java.io.Writer;

public class StrictDumpWriter implements DumpWriter {


    public String startTag = "<";
    public String endTag = ">";
    public String separator = ";";
    public Writer writer = null;

    public StrictDumpWriter(Writer writer){
        this.writer = writer;
    }

    // {DumpWriter} Overrides

    @Override
    public void writeFeed(DataFeed feed, FeedInfo info) throws IOException {
        boolean ownsContent = feed.ownsContent();
        writer.write(feed.getData());
        if (ownsContent) {
            info.directoryCount++;
            writer.write(startTag);
        }
        else {
            info.fileCount++;
            writer.write(separator);
        }

        if (ownsContent) {
            DataFeed[] subFeeds = feed.listContent();
            for (DataFeed subFeed: subFeeds) {
                writeFeed(subFeed, info);
            }
            writer.write(endTag);
        }
    }

}
