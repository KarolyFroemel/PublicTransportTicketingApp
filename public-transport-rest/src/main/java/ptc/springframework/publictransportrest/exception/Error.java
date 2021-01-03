package ptc.springframework.publictransportrest.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class Error {
    public Error(String message, List<String> details) {
        super();
        this.message = message;
        this.details = details;
    }

    private String message;

    private List<String> details;

}
