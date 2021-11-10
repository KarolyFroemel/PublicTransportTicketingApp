package ptc.springframework.publictransportrest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping({ "/hello" })
    public String firstPage() {
        return "Hello World";
    }

    @RequestMapping({ "/helloadmin" })
    public String admin() {
        return "Hello World";
    }

    @RequestMapping({ "/hellopassenger" })
    public String passenger() {
        return "Hello World";
    }

    @RequestMapping({ "/hellovalidator" })
    public String validator() {
        return "Hello World";
    }

}
