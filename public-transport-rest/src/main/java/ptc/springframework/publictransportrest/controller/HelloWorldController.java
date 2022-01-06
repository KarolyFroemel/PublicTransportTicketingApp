package ptc.springframework.publictransportrest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class HelloWorldController {

    @RequestMapping({ "/hello" })
    public String firstPage() {
        return "Hello World";
    }

//    @RolesAllowed("ADMIN")
    @RequestMapping({ "/helloadmin" })
    public String admin() {
        return "Hello admin";
    }

//    @RolesAllowed("PASSENGER")
    @RequestMapping({ "/hellopassenger" })
    public String passenger() {
        return "Hello passenger";
    }

//    @RolesAllowed("VALIDATOR")
    @RequestMapping({ "/hellovalidator" })
    public String validator() {
        return "Hello validator";
    }

//    @RolesAllowed({"VALIDATOR", "ADMIN", "PASSENGER"})
    @RequestMapping({ "/alluser" })
    public String allUser() {
        return "Hello all users";
    }

}
