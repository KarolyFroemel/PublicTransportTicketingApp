package ptc.springframework.publictransportrest.exceptions;

import org.springframework.http.HttpStatus;
import ptc.springframework.publictransportrest.exceptions.error.ErrorCode;

public class ServiceException extends BaseException {

    public ServiceException(ErrorCode errorCode, String message, String detailedMessage, HttpStatus httpStatus) {
        super(errorCode, message, detailedMessage, httpStatus);
    }
}
