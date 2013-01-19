package fshdump;

import java.io.IOException;

public interface DumpWriter {

    public void writeFeed(DataFeed feed, FeedInfo info) throws IOException;

}
