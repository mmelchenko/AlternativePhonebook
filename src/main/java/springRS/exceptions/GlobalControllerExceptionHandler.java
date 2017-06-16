package springRS.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(UnknownResourceException.class)
    public void handleConflict(HttpServletRequest request) {
        String url = request.getRequestURI();
        System.out.println("Unknown resource exception: " + url);
    }
}
