package ua.nure.fomenko.final_project.exception;

/**
 * Created by fomenko on 25.08.2017.
 */
public class InitException extends RuntimeException {
    public InitException() {
    }

    public InitException(String message) {
        super(message);
    }

    public InitException(String message, Throwable cause) {
        super(message, cause);
    }
}
