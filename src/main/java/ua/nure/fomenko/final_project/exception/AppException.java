package ua.nure.fomenko.final_project.exception;

/**
 * Created by fomenko on 25.08.2017.
 */
public class AppException extends RuntimeException {
    public AppException() {
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
