package ptc.springframework.publictransportweb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ptc.springframework.publictransportweb.model.TicketType;
import ptc.springframework.publictransportweb.service.TicketService;

import java.util.List;

@Controller()
@RequestMapping("/ticket")
public class WebTicketController {

    private final TicketService ticketService;

    public WebTicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping({"", "/", "index", "index.html"})
    public String getHomePage(Model model){
        model.addAttribute("tickets", ticketService.getTicketTypes());
        return "index";
    }

    @GetMapping("/getTicketTypes")
    public ResponseEntity<List<TicketType>> getTicketTypes(){
        return ResponseEntity.ok(ticketService.getTicketTypes());
    }

}
