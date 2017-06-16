package springRS.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Bad request")
public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String errorMessage;

    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
