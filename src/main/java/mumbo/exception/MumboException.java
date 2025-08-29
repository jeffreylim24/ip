package mumbo.exception;

/**
 * A simple runtime exception specifically from Mumbo
 */
public class MumboException extends RuntimeException {
    public MumboException(String message) {
        super(message);
    }
}
