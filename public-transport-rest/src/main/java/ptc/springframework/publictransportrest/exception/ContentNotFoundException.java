package ptc.springframework.publictransportrest.exception;

import lombok.Getter;

@Getter
public class ContentNotFoundException extends RuntimeException {

    private String message;
    private String detailedMessage;

    public ContentNotFoundException(String message, String detailedMessage) {
        this.message = message;
        this.detailedMessage = detailedMessage;

    }
}
