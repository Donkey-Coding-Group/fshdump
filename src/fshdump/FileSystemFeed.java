package fshdump;

import java.io.*;

public class FileSystemFeed implements DataFeed {

    File file;

    public FileSystemFeed(File file) {
        this.file = file;
    }

    // {DataFeed} Overrides

    @Override
    public boolean ownsContent() {
        return file.isDirectory();
    }

    @Override
    public FileSystemFeed[] listContent() {
        File[] subFiles = file.listFiles();
        if (subFiles == null) {
            return new FileSystemFeed[0];
        }
        FileSystemFeed[] feeds = new FileSystemFeed[subFiles.length];
        for (int i=0; i < subFiles.length; i++) {
            feeds[i] = new FileSystemFeed(subFiles[i]);
        }
        return feeds;
    }

    @Override
    public String getData() {
        return file.getName();
    }

}
