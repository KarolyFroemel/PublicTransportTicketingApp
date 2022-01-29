package ptc.springframework.publictransportrest.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping({ "/helloadmin" })
    public String admin() {
        return "Hello admin";
    }


    @RequestMapping({ "/hellopassenger" })
    public String passenger() {
        return "Hello passenger";
    }

    @RequestMapping({ "/hellovalidator" })
    public String validator() {
        return "Hello validator";
    }

    @RequestMapping({ "/alluser" })
    public String allUser() {
        return "Hello all users";
    }
}
