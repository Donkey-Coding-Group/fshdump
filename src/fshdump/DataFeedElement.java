package fshdump;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * This class implements the {@link iHierarchyFeed} interface with
 * fixed data.
 */
public class DataFeedElement implements iHierarchyFeed {

    private String data;
    private ArrayList<DataFeedElement> childFeeds;

    public DataFeedElement(String data, boolean hasChildFeeds) {
        this.data = data;
        if (hasChildFeeds) {
            childFeeds = new ArrayList<DataFeedElement>();
        }
        else {
            childFeeds = null;
        }
    }

    public void add(DataFeedElement element) {
        assert element != null;
        childFeeds.add(element);
    }

    public void add(int index, DataFeedElement element) {
        assert element != null;
        childFeeds.add(index, element);
    }

    public void deleteHasChildren() {
        if (childFeeds != null) {
            childFeeds.clear();
            childFeeds = null;
        }
    }

    public void setHasChildren() {
        if (childFeeds == null)
            childFeeds = new ArrayList<DataFeedElement>();
    }

    public void formatNice(Writer writer, int depth) throws IOException {
        String indent = "";
        for (int i=0; i < depth; i++) {
            indent += "\t";
        }
        writer.write(indent + getData());
        if (childFeeds != null) {
            writer.write("/\n");
            for (DataFeedElement child: childFeeds) {
                child.formatNice(writer, depth + 1);
            }
        }
        writer.write("\n");
    }

    // {iHierarchyFeed} Override

    @Override
    public String getData() {
        return data;
    }

    @Override
    public DataFeedElement[] getChildFeeds() {
        if (childFeeds != null) {
            return childFeeds.toArray(new DataFeedElement[0]);
        }
        return null;
    }

}
