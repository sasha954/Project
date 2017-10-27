package ua.nure.fomenko.final_project.exception;

/**
 * Created by fomenko on 25.08.2017.
 */
public class DBException extends RuntimeException {
    public DBException() {
    }

    public DBException(String message) {
        super(message);
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
}
