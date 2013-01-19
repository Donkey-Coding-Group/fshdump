package fshdump.util.formatting;

public class Column extends CellCollection {

    public TextSeparator separator = null;

    // {Formatter} Overrides

    public CompiledFormatter compileFormatter() {
        return compileToColumn(separator);
    }
}
