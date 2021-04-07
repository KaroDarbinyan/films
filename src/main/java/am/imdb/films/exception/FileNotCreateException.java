package am.imdb.films.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FileNotCreateException extends NotFoundException {

    public FileNotCreateException() {
    }

    public FileNotCreateException(String message) {
        super(message);
    }

    public FileNotCreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotCreateException(Throwable cause) {
        super(cause);
    }

    public FileNotCreateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
