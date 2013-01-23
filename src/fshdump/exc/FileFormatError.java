package fshdump.exc;

public class FileFormatError extends ParseError {

    public FileFormatError(String format, Object ... args) {
        super(format, args);
    }

}
