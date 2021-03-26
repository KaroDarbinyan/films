package am.imdb.films.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Sorry, this file is corrupted or does not exist")
public class FileNotExistException extends NotFoundException {

    public FileNotExistException() {
    }

    public FileNotExistException(String message) {
        super(message);
    }

    public FileNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotExistException(Throwable cause) {
        super(cause);
    }

    public FileNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
