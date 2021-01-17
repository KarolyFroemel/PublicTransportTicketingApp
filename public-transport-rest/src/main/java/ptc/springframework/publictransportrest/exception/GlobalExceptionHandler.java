package ptc.springframework.publictransportrest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotfoundException.class)
    protected ResponseEntity<Object> handleUserNotfoundException(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        Arrays.asList(ex.getStackTrace()).forEach(stackTraceElement -> details.add(stackTraceElement.toString()));
        Error error = new Error("User not found error", details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketTypeNotFoundException.class)
    protected ResponseEntity<Object> handleTicketTypeNotFoundException(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        Arrays.asList(ex.getStackTrace()).forEach(stackTraceElement -> details.add(stackTraceElement.toString()));
        Error error = new Error("Ticket type not found!", details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<Object> handleNullPointerException(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        Arrays.asList(ex.getStackTrace()).forEach(stackTraceElement -> details.add(stackTraceElement.toString()));
        Error error = new Error("Internal Server Error", details);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
