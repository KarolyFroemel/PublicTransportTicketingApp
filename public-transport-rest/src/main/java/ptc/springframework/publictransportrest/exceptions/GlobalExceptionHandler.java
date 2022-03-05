package ptc.springframework.publictransportrest.exceptions;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ptc.springframework.publictransportrest.exceptions.error.ApplicationErrorCode;

import java.util.stream.Collectors;

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
            = { BaseException.class })
    protected
    ResponseEntity<ErrorResponse> handleApplicationExceptions(BaseException ex) {
        return ResponseEntity.
                status(ex.getHttpStatus()).
                body(ErrorResponse.builder().
                        errorCode(ex.getErrorCode().getCode()).
                        message(ex.getMessage()).
                        detailedMessage(ex.getDetailedMessage()).
                        build());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getFieldErrors().stream()
                .map(fieldError -> fieldError.getObjectName()+"."+fieldError.getField()+"."+fieldError.getCode())
                .collect(Collectors.joining("|"));
        String detailMessage =  ex.getFieldErrors().stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.joining("|"));
        return ResponseEntity.
                status(HttpStatus.BAD_REQUEST).
                body(ErrorResponse.builder().
                        errorCode(ApplicationErrorCode.REQUEST_PARAMETERS_NOT_VALID.getCode()).
                        message(message).
                        detailedMessage(detailMessage).
                        build());
    }
}
