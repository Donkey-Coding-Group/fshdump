package fshdump.exc;

public class UnexpectedEOIError extends ParseError {

    public UnexpectedEOIError() {
        super("Reached end of input unexpectedly.");
    }

    public UnexpectedEOIError(String format, Object ... args) {
        super(format, args);
    }

}
