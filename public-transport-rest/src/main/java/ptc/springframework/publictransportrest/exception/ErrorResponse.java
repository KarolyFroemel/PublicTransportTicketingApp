package ptc.springframework.publictransportrest.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {

    private String errorCode;

    private String message;

    private String detailedMessage;
}
