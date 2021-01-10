package ptc.springframework.publictransportweb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ptc.springframework.publictransportweb.model.TicketType;
import ptc.springframework.publictransportweb.model.TicketPurchaseDTO;
import ptc.springframework.publictransportweb.service.TicketService;
import java.util.List;

@Controller
@RequestMapping
public class WebTicketController {

    private final TicketService ticketService;

    public WebTicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/ticket/getTicketTypes")
    public ResponseEntity<List<TicketType>> getTicketTypes(){
        return ResponseEntity.ok(ticketService.getTicketTypes());
    }

    @GetMapping("/ticketPurchasePage")
    public String ticketPurchasePage(Model model){
        model.addAttribute("tickets", ticketService.getTicketTypes());
        model.addAttribute("ticketPurchaseDTO", new TicketPurchaseDTO());
        return "buy";
    }

    @PostMapping("/ticketPurchasePage")
    public String ticketPurchasePageSave(TicketPurchaseDTO ticketPurchaseDTO, Model model){
        System.out.println("ticket type: " + ticketPurchaseDTO.getTicketTypeName());
        System.out.println("Ticket date: " + ticketPurchaseDTO.getValidFrom());
        model.addAttribute("tickets", ticketService.getTicketTypes());
        model.addAttribute("ticketPurchaseDTO", new TicketPurchaseDTO());
        return "buy";
    }
}
