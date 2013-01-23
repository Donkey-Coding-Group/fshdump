
import java.awt.*;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import fshdump.FileSystemFeed;
import fshdump.iHierarchyFeed;

public class FeedView extends JFrame implements TreeSelectionListener, TreeExpansionListener {

    private class MutableNode extends DefaultMutableTreeNode {
        iHierarchyFeed feed;
        boolean isFolder = false;
        boolean built = false;
        public MutableNode(String name, boolean isFolder) {
            super(name);
            feed = null;
            this.isFolder = isFolder;
            tearDown();
        }
        public MutableNode(boolean isFolder) {
            super();
            this.isFolder = isFolder;
            tearDown();
        }
        public MutableNode(iHierarchyFeed feed) {
            super();
            setFeed(feed);
            isFolder = feed.canHaveChildren();
            tearDown();
        }
        public void setFeed(iHierarchyFeed feed) {
            setUserObject(feed.getData());
            this.feed = feed;
            isFolder = feed.canHaveChildren();
            tearDown();
            built = false;
        }
        public boolean build() {
            if (built) return false;
            removeAllChildren();
            iHierarchyFeed[] feeds = feed.getChildFeeds();
            if (feeds != null) {
                for (iHierarchyFeed subFeed: feeds) {
                    add(new MutableNode(subFeed));
                }
            }
            built = true;
            return true;
        }
        public void tearDown() {
            built = false;
            removeAllChildren();
            if (isFolder) {
                add(new DefaultMutableTreeNode());
            }
        }
        public void onActive() {
        }

        public void nodeCollapsed(TreeExpansionEvent event) {

        }
        public void nodeExpanded(TreeExpansionEvent event) {
            if (build()) {
                ((DefaultTreeModel) ((JTree) event.getSource()).getModel()).reload();
            }
        }
    }

    private JTree tree;
    private JScrollPane treeView;
    private MutableNode topNode;

    public FeedView() {
        super();
        topNode = new MutableNode("<Root>", false);
        tree = new JTree(topNode);
        tree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(this);
        tree.addTreeExpansionListener(this);
        treeView = new JScrollPane(tree);
        treeView.setMinimumSize(new Dimension(100, 50));
        add(treeView);
    }

    public void setFeed(iHierarchyFeed feed) {
        topNode.setFeed(feed);
        topNode.removeAllChildren();
        topNode.build();
    }

    // {TreeSelectionListener} Overrides

    @Override
    public void valueChanged(TreeSelectionEvent event) {
        TreePath path = event.getPath();
        MutableNode node = (MutableNode) path.getLastPathComponent();
        node.onActive();
    }

    // {TreeExpansionListener} Overrides

    public void treeCollapsed(TreeExpansionEvent event) {
        MutableNode node = (MutableNode) event.getPath().getLastPathComponent();
        topNode.nodeCollapsed(event);
    }
    public void treeExpanded(TreeExpansionEvent event) {
        MutableNode node = (MutableNode) event.getPath().getLastPathComponent();
        node.nodeExpanded(event);
    }

}
