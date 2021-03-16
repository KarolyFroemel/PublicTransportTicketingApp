package ptc.springframework.publictransportweb.helper;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateFormatHelper {
    private String pattern = "yyyy-MM-dd";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    public String dateFormatter(Date date) {
        return simpleDateFormat.format(date);
    }
}
