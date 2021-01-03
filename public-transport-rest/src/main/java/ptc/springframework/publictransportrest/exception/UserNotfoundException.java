package ptc.springframework.publictransportrest.exception;

public class UserNotfoundException extends RuntimeException {
    private static final long serialVersionUID = -6325178470386102086L;

    public UserNotfoundException() {
        super();
    }

    public UserNotfoundException(String message) {
        super(message);
    }

    public UserNotfoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotfoundException(Throwable cause) {
        super(cause);
    }
}
