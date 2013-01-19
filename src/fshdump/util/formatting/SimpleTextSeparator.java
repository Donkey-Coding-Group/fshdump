package fshdump.util.formatting;

/**
 * A simple TextSeparator implementation.
 */
public class SimpleTextSeparator implements TextSeparator {

    private int hOffset = 0;
    private int vOffset = 0;
    public String hSep;
    public String vSep;

    public SimpleTextSeparator(String hSep, String vSep) {
        this.hSep = hSep;
        this.vSep = vSep;
    }

    // {TextSeparator} Overrides

    @Override
    public void setHorizontalOffset(int offset) {
        hOffset = offset;
    }

    @Override
    public void setVerticalOffset(int offset) {
        vOffset = offset;
    }

    @Override
    public String getHorizontalSeparator(int length) {
        String finalString = "";
        int hSepLength = hSep.length();
        for (int i=hOffset; i < length + hOffset; i++) {
            finalString += hSep.charAt(i % hSepLength);
        }
        return finalString;
    }

    @Override
    public String getVerticalSeparator(int lineIndex) {
        return String.valueOf(vSep.charAt((vOffset + lineIndex) % vSep.length()));
    }

}
