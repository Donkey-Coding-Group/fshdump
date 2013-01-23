package fshdump.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * This class implements scanning an input source character by
 * character, making it more easy to do parsing operations.
 */
public class Scanner {

    private Cursor cursor = new Cursor();
    private Reader reader = null;
    private boolean callClose = false;
    public  int current = -1;

    public Scanner(Reader reader, boolean callClose) {
        assert reader != null;
        this.reader = reader;
        this.callClose = callClose;
    }

    public Scanner(String data) {
        assert data != null;
        reader = new StringReader(data);
        callClose = true;
    }

    public Reader getReader() {
        return reader;
    }

    public Cursor getCursor() {
        return cursor.clone();
    }

    public int read() throws IOException {
        current = reader.read();
        cursor.input(current);
        return current;
    }

    public int skip(String charSet) throws IOException {
        int count = 0;
        while (current >= 0 && charSet.indexOf((char) current) >= 0) {
            count++;
            read();
        }
        return count;
    }

    public String readLine() throws IOException {
        StringBuilder builder = new StringBuilder();
        while (current >= 0 && current != '\n') {
            builder.append((char) current);
            read();
        }
        return builder.toString();
    }

    public String readAlong(String charSet) throws IOException {
        StringBuilder builder = new StringBuilder();
        while (current >= 0 && charSet.indexOf((char) current) >= 0) {
            builder.append((char) current);
            read();
        }
        return builder.toString();
    }

    public String readUntil(String charSet) throws IOException {
        StringBuilder builder = new StringBuilder();
        while (current >= 0 && charSet.indexOf((char) current) < 0) {
            builder.append((char) current);
            read();
        }
        return builder.toString();
    }

    public String match(char[] sequence) throws IOException {
        if (!reader.markSupported()) {
            throw new IOException("Reader does not supported marking.");
        }
        int i;
        int length = sequence.length;
        reader.mark(length);
        for (i=0; i < length && current >= 0; i++) {
            if (current != sequence[i]) {
                break;
            }
        }
        if (i == length) {
            return new String(sequence);
        }
        else {
            reader.reset();
            return null;
        }
    }

}
