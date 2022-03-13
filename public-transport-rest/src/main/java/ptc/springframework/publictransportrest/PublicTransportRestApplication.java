package ptc.springframework.publictransportrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class PublicTransportRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublicTransportRestApplication.class, args);
    }

}
