package ptc.springframework.publictransportrest.exceptions;

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
