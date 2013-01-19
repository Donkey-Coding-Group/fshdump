
import java.io.*;
import fshdump.*;
import fshdump.util.formatting.*;

public class Test {

    public static void main(String[] args) throws IOException {
        String destFile = "C:/Users/niklas/Desktop/desktop.tree";
        File directory = new File(args[0]);

        BufferedOutputStream stream = new BufferedOutputStream(System.out);
        BufferedWriter writer = new BufferedWriter(new FileWriter(destFile));

        FeedInfo info =  new FeedInfo();
        DumpWriter dump = new StrictDumpWriter(writer);
        DataFeed feed = new FileSystemFeed(directory);
        dump.writeFeed(feed, info);

        Column column = new Column();
        column.add("" + info.fileCount);
        column.add("" + info.directoryCount);
        CompiledFormatter formatter = column.compileFormatter();
        for (int i=0; i < formatter.getLineCount(); i++) {
            System.out.println(formatter.getLine(i));
        }
    }

}
