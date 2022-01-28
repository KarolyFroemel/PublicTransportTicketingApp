package ptc.springframework.publictransportrest.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { NullPointerException.class })
    protected
    ResponseEntity<ErrorResponse> handleNullPointerException(RuntimeException ex) {
        return ResponseEntity.
                status(HttpStatus.INTERNAL_SERVER_ERROR).
                body(ErrorResponse.builder().
                        errorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString()).
                        message(ExceptionUtils.getMessage(ex)).
                        detailedMessage(ExceptionUtils.getStackTrace(ex)).
                        build());
    }

    @ExceptionHandler(value
            = { DataAccessException.class, DataIntegrityViolationException.class })
    protected
    ResponseEntity<ErrorResponse> handleRepositoryException(RuntimeException ex) {
        return ResponseEntity.
                status(HttpStatus.BAD_REQUEST).
                body(ErrorResponse.builder().
                        errorCode(HttpStatus.BAD_REQUEST.toString()).
                        message(ExceptionUtils.getMessage(ex)).
                        detailedMessage(ExceptionUtils.getStackTrace(ex)).
                        build());
    }

    @ExceptionHandler(value
            = { ContentNotFoundException.class })
    protected
    ResponseEntity<ErrorResponse> handleContentNotFoundExceptionException(ContentNotFoundException ex) {
        return ResponseEntity.
                status(HttpStatus.NOT_FOUND).
                body(ErrorResponse.builder().
                        errorCode(HttpStatus.NOT_FOUND.toString()).
                        message(ex.getMessage()).
                        detailedMessage(ex.getDetailedMessage()).
                        build());
    }
}
