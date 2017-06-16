package springRS.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Resource is unknown.")
public class UnknownResourceException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String errorMessage;

    public UnknownResourceException() {
        super();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public UnknownResourceException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
