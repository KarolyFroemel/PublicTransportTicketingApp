package ptc.springframework.publictransportrest.exceptions.error;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public enum TicketTypeErrorCode implements ErrorCode {

    TICKET_TYPE_NOT_FOUND(1);

    private final int code;
    private final NumberFormat format = new DecimalFormat("TICKET_TYPE000");

    TicketTypeErrorCode(int code) {this.code = code;}

    @Override
    public String getCode() {
        return format.format(this.code);
    }
}
