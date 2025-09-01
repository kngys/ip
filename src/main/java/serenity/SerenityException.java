package serenity;

/**
 * Represents a custom exception in the Serenity class.
 */
public class SerenityException extends Exception {

    /**
     * Constructs a SerenityException.
     *
     * @param msg The error message of the exception.
     */
    public SerenityException(String msg) {
        super(msg);
    }
}
