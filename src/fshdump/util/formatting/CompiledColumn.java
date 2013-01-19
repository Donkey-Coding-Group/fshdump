package fshdump.util.formatting;

import java.util.ArrayList;

/**
 * This class implements rendering a collection of CompiledFormatter
 * objects in a (vertical) column.
 */
public class CompiledColumn implements CompiledFormatter {

    /**
     * This list will contain each line compiled from the formatters
     * passed on construction.
     */
    private String[] lines;

    /**
     * This will contain the width of the column after compilation
     * (which is construction) time.
     */
    private int columnWidth;

    public CompiledColumn(Formatter[] array, TextSeparator separator) {
        CompiledFormatter[] compiledArray =  new CompiledFormatter[array.length];
        for (int i=0; i < array.length; i++) {
            compiledArray[i] = array[i].compileFormatter();
        }
        compile(compiledArray, separator);
    }

    public CompiledColumn(CompiledFormatter[] array, TextSeparator separator) {
        compile(array, separator);
    }

    public CompiledColumn(String[] lines) {
        compile(lines);
    }

    // private Methods

    private void compile(CompiledFormatter[] array, TextSeparator separator) {
        columnWidth = 0;
        for (CompiledFormatter formatter: array) {
            int width = formatter.getMaxWidth();
            if (columnWidth < width) {
                columnWidth = width;
            }
        }

        ArrayList<String> lines = new ArrayList<String>();
        if (separator != null)
            lines.add(separator.getHorizontalSeparator(columnWidth));
        for (CompiledFormatter formatter: array) {
            int count = formatter.getLineCount();
            for (int i=0; i < count; i++) {
                lines.add(formatter.getLine(i));
            }
            if (separator != null)
                lines.add(separator.getHorizontalSeparator(columnWidth));
        }

        this.lines = lines.toArray(new String[0]);
    }

    private void compile(String[] lines) {
        this.lines = lines;
        columnWidth = 0;
        for (String line: lines) {
            int lineLength = line.length();
            if (lineLength > columnWidth) {
                columnWidth = lineLength;
            }
        }
    }

    // {CompiledFormatter} Overrides

    @Override
    public String getLine(int index) {
        if (index >= lines.length) {
            return "";
        }
        else {
            return lines[index];
        }
    }

    @Override
    public int getLineCount() {
        return lines.length;
    }

    @Override
    public int getMaxWidth() {
        return columnWidth;
    }

}
