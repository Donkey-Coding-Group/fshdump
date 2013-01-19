package fshdump.util.formatting;

import java.util.ArrayList;

/**
 * Implements a column of text cells.
 */
abstract public class CellCollection implements Formatter {

    /**
     * This list contains all cells for the column. Public access
     * is offered to have full control over the cells in the column.
     */
    public ArrayList<Cell> cells = new ArrayList<Cell>();

    /**
     * Initialize a {@link CellCollection} object.
     */
    public CellCollection() {
    }

    /**
     * Sets the maxWidth of each cell in the {@link #cells} list.
     * @param maxWidth the maxWidth passed to each cell.
     */
    public void setMaxWidth(int maxWidth) {
        for (Cell cell: cells) {
            cell.maxWidth = maxWidth;
        }
    }

    /**
     * Sets the maxLines of each cell in the {@link #cells} list.
     * @param maxLines the maxLines passed to each cell.
     */
    public void setMaxLines(int maxLines) {
        for (Cell cell: cells) {
            cell.maxLines = maxLines;
        }
    }

    /**
     * Add a new Cell to the CellCollection.
     * @param cell The Cell to add.
     */
    public void add(Cell cell) {
        cells.add(cell);
    }

    /**
     * Add new Cell to the CellCollection at the specified index.
     * @param index The index.
     * @param cell The Cell to add.
     */
    public void add(int index, Cell cell) {
        cells.add(index, cell);
    }

    /**
     * @return the CompiledRow object that can be used to render the
     * text from the CellCollection.
     */
    public CompiledRow compileToRow(TextSeparator separator) {
        return new CompiledRow(cells.toArray(new Cell[0]), separator);
    }

    /**
     * @return CompiledColumn object that can be used to render the
     * text from the CellCollection.
     */
    public CompiledColumn compileToColumn(TextSeparator separator) {
        return new CompiledColumn(cells.toArray(new Cell[0]), separator);
    }

}
