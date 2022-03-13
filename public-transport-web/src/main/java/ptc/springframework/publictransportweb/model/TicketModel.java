package ptc.springframework.publictransportweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketModel {

    private UUID id;
    private String name;
    private String purchaseDate;
    private String validFrom;
    private String validTo;
    private String validationDate;
}
