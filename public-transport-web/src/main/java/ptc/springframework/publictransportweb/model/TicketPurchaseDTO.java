package ptc.springframework.publictransportweb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class TicketPurchaseDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date validFrom;

    private String ticketTypeName;
}
