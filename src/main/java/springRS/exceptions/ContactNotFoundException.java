package springRS.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Contact")
public class ContactNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String errorMessage;

    public ContactNotFoundException() {
        super();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public ContactNotFoundException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
