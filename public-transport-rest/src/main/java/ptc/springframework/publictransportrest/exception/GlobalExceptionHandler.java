package ptc.springframework.publictransportrest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotfoundException.class)
    protected ResponseEntity<Object> handleUserNotfoundException(Exception ex) {
        return new ResponseEntity(createError(ex, "User not found error!"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketTypeNotFoundException.class)
    protected ResponseEntity<Object> handleTicketTypeNotFoundException(Exception ex) {
        return new ResponseEntity(createError(ex, "Ticket type not found!"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    protected ResponseEntity<Object> handleAccountNotFoundException(Exception ex) {
        return new ResponseEntity(createError(ex, "Account not found error!"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketNotFoundException.class)
    protected ResponseEntity<Object> handleTicketNotFoundException(Exception ex) {
        return new ResponseEntity(createError(ex, "Ticket not found error!"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketAlreadyValidatedException.class)
    protected ResponseEntity<Object> handleTicketAlreadyValidatedException(Exception ex) {
        return new ResponseEntity(createError(ex, "Ticket already validated!"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketExpiredException.class)
    protected ResponseEntity<Object> handleTicketExpiredException(Exception ex) {
        return new ResponseEntity(createError(ex, "Ticket is expired!"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CantValidatePassException.class)
    protected ResponseEntity<Object> handleCantValidatePassException(Exception ex) {
        return new ResponseEntity(createError(ex, "Can't validate a pass!"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<Object> handleNullPointerException(Exception ex) {
        return new ResponseEntity(createError(ex, "Internal Server Error!"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Error createError(Exception ex, String message) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        Arrays.asList(ex.getStackTrace()).forEach(stackTraceElement -> details.add(stackTraceElement.toString()));
        Error error = new Error(message, details);
        return error;
    }

}
