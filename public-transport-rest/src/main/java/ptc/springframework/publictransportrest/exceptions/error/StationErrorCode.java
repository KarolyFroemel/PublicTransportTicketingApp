package ptc.springframework.publictransportrest.exceptions.error;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public enum StationErrorCode implements ErrorCode {

    STATION_NOT_FOUND(1);

    private final int code;
    private final NumberFormat format = new DecimalFormat("STATION000");

    StationErrorCode(int code) {this.code = code;}

    @Override
    public String getCode() {
        return format.format(this.code);
    }
}
