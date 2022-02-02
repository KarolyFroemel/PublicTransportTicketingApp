package ptc.springframework.publictransportrest.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import ptc.springframework.publictransportrest.exceptions.error.ErrorCode;

@Getter
@AllArgsConstructor
public abstract class BaseException extends RuntimeException {
    protected ErrorCode errorCode;
    protected String message;
    protected String detailedMessage;
    protected HttpStatus httpStatus;
}
