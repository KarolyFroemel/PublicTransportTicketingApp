package ptc.springframework.publictransportweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketTypeModel {

    private String name;
    private String Description;
    private Long price;

}
