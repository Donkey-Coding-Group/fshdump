package fshdump.util.formatting;

/**
 * This interface describes an object that is able to return a
 * {@link CompiledCellInterface} object.
 */
public interface CellInterface {

    /**
     * Return the {@link CompiledCellInterface} object.
     * @return the object.
     */
    public CompiledCellInterface getCompiledCell();

}
