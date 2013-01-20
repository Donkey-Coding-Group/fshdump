package fshdump;

import java.io.File;

/**
 * This class implements the {@link iHierarchyFeed} interface providing
 * data from the local file-system.
 */
public class FileSystemFeed implements iHierarchyFeed {

    /**
     * Internal {@link File} object.
     */
    public File fileObject;

    /**
     * Construct a new {@link FileSystemFeed} from a filename.
     * @param fileName The file/directory name.
     */
    public FileSystemFeed(String fileName) {
        fileObject = new File(fileName);
    }

    /**
     * Construct a new {@link FileSystemFeed} from a {@link File}
     * object.
     * @param fileObject The {@link File} object.
     */
    public FileSystemFeed(File fileObject) {
        this.fileObject = fileObject;
    }

    // iHierarchyFeed -- Overrides

    @Override
    public String getData() {
        return fileObject.getName();
    }

    @Override
    public iHierarchyFeed[] getChildFeeds() {
        // Return null when this object represents a file
        // on the file system.
        if (!fileObject.isDirectory()) {
            return null;
        }

        // List up the directory.
        File[] files = fileObject.listFiles();
        if (files == null) {
            return new iHierarchyFeed[0];
        }

        // Create the array of feeds.
        iHierarchyFeed[] feeds = new iHierarchyFeed[files.length];
        for (int i=0; i < files.length; i++) {
            feeds[i] = new FileSystemFeed(files[i]);
        }

        return feeds;
    }

}
