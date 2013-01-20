
import java.io.FileWriter;
import java.io.IOException;

import fshdump.DumpOptions;
import fshdump.DumpResult;
import fshdump.FileSystemFeed;
import fshdump.StrictDumpWriter;

public class WriterMain {

    public static void main(String[] args) {
        System.exit(new WriterMain().runMain(args));
    }

    // Instance Methods

    public int runMain(String[] args) {
        String directory = "C:/Users/niklas/Desktop";
        String destFile = "C:/Users/niklas/Desktop/desktop.tree";

        FileSystemFeed feed = new FileSystemFeed(directory);
        FileWriter writer;
        try {
            writer = new FileWriter(destFile);
        }
        catch (IOException exc) {
            System.err.format("Could not create FileWriter for %s.\n",
                    destFile);
            exc.printStackTrace();
            return 1;
        }

        StrictDumpWriter dumpWriter = new StrictDumpWriter();
        DumpOptions options = new DumpOptions();
        DumpResult result = new DumpResult();
        try {
            dumpWriter.writeFeed(options, result, writer, feed);
        }
        catch (IOException exc) {
            System.err.format("Error while writing dump.");
            exc.printStackTrace();
            return 2;
        }

        System.out.format("%d files\n", result.endFeeds);
        System.out.format("%d directories\n", result.folderFeeds);

        return 0;
    }

}
