package ptc.springframework.publictransportrest.exceptions;

import org.springframework.http.HttpStatus;
import ptc.springframework.publictransportrest.exceptions.error.ErrorCode;

public class StationException extends BaseException {

    public StationException(ErrorCode errorCode, String message, String detailedMessage, HttpStatus httpStatus) {
        super(errorCode, message, detailedMessage, httpStatus);
    }
}
