package fshdump;

/**
 * This interface is used to obtain hierarchically structured
 * information written in the FSHDump file format.
 */
public interface iHierarchyFeed {

    /**
     * Return the String value of the element written to the
     * FSHDump file.
     * @return The string to write.
     */
    public String getData();

    /**
     * Return an array of child elements for the hierarchy node.
     * @return An array of {@link iHierarchyFeed} objects or null
     * if the node does not have sub-elements (eg. files in a
     * file-system).
     */
    public iHierarchyFeed[] getChildFeeds();

}

