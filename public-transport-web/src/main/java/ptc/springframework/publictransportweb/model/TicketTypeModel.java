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
public class TicketTypeModel {

    private UUID id;
    private String name;
    private String Description;
    private Integer price;
    private Integer expirationTime;
    private String imgSource;
}
