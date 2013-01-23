package fshdump.exc;

import javax.management.monitor.StringMonitor;

public class ParseError extends Exception {

    public ParseError() {
        super();
    }

    public ParseError(String message) {
        super(message);
    }

    public ParseError(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseError(Throwable cause) {
        super(cause);
    }

    public ParseError(String format, Object ... args) {
        super(String.format(format, args));
    }

}
