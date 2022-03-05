package ptc.springframework.publictransportrest.exceptions.error;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public enum ServiceErrorCode implements ErrorCode {

    SERVICE_NOT_FOUND(1);

    private final int code;
    private final NumberFormat format = new DecimalFormat("SERVICE000");

    ServiceErrorCode(int code) {this.code = code;}

    @Override
    public String getCode() {
        return format.format(this.code);
    }
}
