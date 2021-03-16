package ptc.springframework.publictransportweb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ptc.springframework.publictransportweb.model.TicketPurchaseInfoDTO;
import ptc.springframework.publictransportweb.model.TicketTypeModel;
import ptc.springframework.publictransportweb.service.TicketService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping
public class WebTicketController {

    private final TicketService ticketService;

    public WebTicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/ticket/getTicketTypes")
    public ResponseEntity<List<TicketTypeModel>> getTicketTypes(){
        return ResponseEntity.ok(ticketService.getTicketTypes());
    }

    @GetMapping("/home")
    public String homePage(Model model){
        return "home";
    }

    @GetMapping("/ticketPurchasePage")
    public String ticketPurchasePage(Model model){
        model.addAttribute("tickets", ticketService.getTicketTypes());
        model.addAttribute("ticketPurchaseDTO", new TicketPurchaseInfoDTO());
        return "ticketPurchaseNew";
    }

    @PostMapping("/ticketPurchasePage")
    public String ticketPurchasePageSave(TicketPurchaseInfoDTO ticketPurchaseDTO){
        //TODO: change it when authentication created
        ticketPurchaseDTO.setUserId(UUID.fromString("516ce0cc-4b70-11eb-ae93-0242ac130002"));
        ticketService.purchaseTicket(ticketPurchaseDTO);
        return "successfulTicketPurchase";
    }
}
