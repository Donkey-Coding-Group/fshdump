package fshdump.util;

/**
 * This class implements counting the characters while reading an
 * input file.
 */
public class Cursor implements Cloneable {

    public int position;
    public int line;
    public int column;

    public Cursor() {
        position = 0;
        line = 0;
        column = 0;
    }

    public Cursor(int position, int line, int column) {
        this.position = position;
        this.line = line;
        this.column = column;
    }

    public void input(int current) {
        if (current < 0) return;
        position++;
        if (current == '\n') {
            line++;
            column = 0;
        }
        else {
            column++;
        }
    }

    // {Cloneable} Overrides

    @Override
    public Cursor clone() {
        Cursor cursor = null;
        try {
            cursor = (Cursor) super.clone();
        }
        catch (CloneNotSupportedException exc) {
            throw new Error("Cursor object could not be cloned.");
        }
        return cursor;
    }

    // {Object} Overrides

    @Override
    public String toString() {
        return String.format("Cursor(%d, %d:%d)", position, line, column);
    }

}
