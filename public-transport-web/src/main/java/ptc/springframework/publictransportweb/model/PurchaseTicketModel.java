package ptc.springframework.publictransportweb.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PurchaseTicketModel {
  private UUID userId;

  private String ticketName;

  private String validFrom;
}

