package ptc.springframework.publictransportrest.exceptions.error;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public enum ApplicationErrorCode implements ErrorCode {
    REQUEST_PARAMETERS_NOT_VALID(1);

    private final int code;
    private final NumberFormat format = new DecimalFormat("APPLICATION000");

    ApplicationErrorCode(int code) {this.code = code;}

    @Override
    public String getCode() {
        return format.format(this.code);
    }
}
