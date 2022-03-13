package ptc.springframework.publictransportweb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ptc.springframework.publictransportweb.model.TicketModel;
import ptc.springframework.publictransportweb.model.TicketPurchaseInfoDTO;
import ptc.springframework.publictransportweb.model.TicketTypeModel;
import ptc.springframework.publictransportweb.service.TicketService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@Controller
@RequestMapping
public class WebTicketController {

    private final TicketService ticketService;

    public WebTicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/ticket/getTicketTypes")
    public ResponseEntity<List<TicketTypeModel>> getTicketTypes(/*@RequestHeader Map<String, String> headers*/@RequestHeader("authorization") String token) {
//        System.out.println("************************************************");
//        headers.forEach((key, value) -> {
//            System.out.println(String.format("Header '%s' = %s", key, value));
//        });
        System.out.println(token);
        return ResponseEntity.ok(ticketService.getTicketTypes(token));
    }

    @GetMapping("/home")
    public String homePage(Model model){
        return "home";
    }

    @GetMapping("/ticketPurchasePage")
    public String ticketPurchasePage(Model model) {
        model.addAttribute("tickets", ticketService.getTicketTypes(""));
        model.addAttribute("ticketPurchaseDTO", new TicketPurchaseInfoDTO());
        return "ticketPurchase";
    }

    @PostMapping("/ticketPurchasePage")
    public String ticketPurchasePageSave(Model model, TicketPurchaseInfoDTO ticketPurchaseDTO) {
        ticketService.purchaseTicket(ticketPurchaseDTO);
        model.addAttribute("message", "Successful ticket purchase!");
        return "successfulTicketOperation";
    }

    @GetMapping("/ticketValidationPage")
    public String ticketValidationPage(Model model) {
        //TODO: meg kell a user id, de meg nem tudom hogyan
        List<TicketModel> userTickets = ticketService.getUserTicketsToValidate(UUID.fromString("516ce0cc-4b70-11eb-ae93-0242ac130002"));
        model.addAttribute("tickets", userTickets);
        return "ticketValidation";
    }

    @PostMapping("/ticketValidationPage/{ticketId}")
    public String ticketValidation(Model model, @PathVariable("ticketId") UUID ticketId) {
        ticketService.validateTicket(ticketId);
        model.addAttribute("message", "Successful ticket validation!");
        return "successfulTicketOperation";
    }

    @GetMapping("/ticketRefundPage")
    public String ticketRefundPage(Model model) {
        List<TicketModel> userTickets = ticketService.getUserTicketsToRefund(UUID.fromString("516ce0cc-4b70-11eb-ae93-0242ac130002"));
        model.addAttribute("tickets", userTickets);
        return "ticketRefund";
    }

    @PostMapping("/ticketRefundPage/{ticketId}")
    public String ticketRefund(Model model, @PathVariable("ticketId") UUID ticketId) {
        ticketService.refundTicket(UUID.fromString("516ce0cc-4b70-11eb-ae93-0242ac130002"), ticketId);
        model.addAttribute("message", "Successful ticket refund!");
        return "successfulTicketOperation";
    }

    @GetMapping("/userTicketsPage")
    public String userTicketsPage(Model model) {
        List<TicketModel> userTickets = ticketService.getUserTickets(UUID.fromString("516ce0cc-4b70-11eb-ae93-0242ac130002"));
        model.addAttribute("tickets", userTickets);
        return "userTicketList";
    }
}
