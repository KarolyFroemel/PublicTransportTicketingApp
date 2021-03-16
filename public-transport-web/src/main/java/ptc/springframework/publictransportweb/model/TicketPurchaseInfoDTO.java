package ptc.springframework.publictransportweb.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TicketPurchaseInfoDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date validFrom;

    private String ticketTypeName;

    private UUID userId;


}
