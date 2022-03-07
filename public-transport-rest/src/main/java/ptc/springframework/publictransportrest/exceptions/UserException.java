package ptc.springframework.publictransportrest.exceptions;

import org.springframework.http.HttpStatus;
import ptc.springframework.publictransportrest.exceptions.error.ErrorCode;

public class UserException extends BaseException {

    public UserException(ErrorCode errorCode, String message, String detailedMessage, HttpStatus httpStatus) {
        super(errorCode, message, detailedMessage, httpStatus);
    }
}
