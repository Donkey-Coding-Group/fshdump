package fshdump.util.formatting;

/**
 * This interface describes an object that is able to return a
 * {@link CompiledFormatter} object.
 */
public interface Formatter {

    /**
     * Return the {@link CompiledFormatter} object.
     * @return the object.
     */
    public CompiledFormatter getCompiledCell();

}
