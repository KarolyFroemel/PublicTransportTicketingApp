package ptc.springframework.publictransportrest.exception;

public class TicketTypeNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -8552762545348888976L;

    public TicketTypeNotFoundException() {
        super();
    }

    public TicketTypeNotFoundException(String message) {
        super(message);
    }

    public TicketTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketTypeNotFoundException(Throwable cause) {
        super(cause);
    }

    protected TicketTypeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
