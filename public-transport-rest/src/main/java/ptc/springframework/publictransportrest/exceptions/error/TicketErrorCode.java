package ptc.springframework.publictransportrest.exceptions.error;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public enum TicketErrorCode implements ErrorCode {

    TICKET_NOT_FOUND(1),
    TICKET_VALID_FROM_IS_IN_THE_PAST(2),
    TICKET_IS_NOT_ENFORCEABLE(3),
    TICKET_IS_NOT_REFUNDABLE(4),
    TICKET_IS_NOT_BELONG_TO_USER(5);

    private final int code;
    private final NumberFormat format = new DecimalFormat("TICKET000");

    TicketErrorCode(int code) {this.code = code;}

    @Override
    public String getCode() {
        return format.format(this.code);
    }
}
