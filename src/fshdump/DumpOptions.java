package fshdump;

/**
 * This class defines options for the FSHDump file format required
 * for {@link iDumpWriter} interface.
 */
final public class DumpOptions {

    public String startTag;
    public String endTag;
    public String separator;

    public DumpOptions() {
        startTag = "<";
        endTag = ">";
        separator = ";";
    }

    public DumpOptions(String startTag, String endTag,
                       String separator) {
        this.startTag = startTag;
        this.endTag = endTag;
        this.separator = separator;
    }

    public String escapeData(String data) {
        data = data.replace(startTag, "\\" + startTag)
                   .replace(endTag, "\\" + endTag)
                   .replace(separator, "\\" + separator);
        return data;
    }

}
