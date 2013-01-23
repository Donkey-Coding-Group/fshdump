package fshdump;

/**
 * This class defines options for the FSHDump file format required
 * for {@link iDumpWriter} interface.
 */
final public class DumpOptions {

    public char startTag;
    public char endTag;
    public char separator;

    public DumpOptions() {
        startTag = '<';
        endTag = '>';
        separator = ';';
    }

    public DumpOptions(char startTag, char endTag, char separator) {
        this.startTag = startTag;
        this.endTag = endTag;
        this.separator = separator;
    }

    public String escapeData(String data) {
        data = data.replace("\\", "\\\\")
                   .replace(Character.toString(startTag), "\\" + startTag)
                   .replace(Character.toString(endTag), "\\" + endTag)
                   .replace(Character.toString(separator), "\\" + separator);
        return data;
    }

}
