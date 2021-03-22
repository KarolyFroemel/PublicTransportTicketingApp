package ptc.springframework.publictransportweb.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler /*extends ResponseEntityExceptionHandler */{

    @ExceptionHandler(WebClientException.class)
    protected ResponseEntity<Object> handleWebClientException(WebClientException ex) {
        log.error("WebClientException caught, message: {}!", ex.getMessage(), ex);
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        Error error = new Error(ex.getMessage(), details);
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ModelAndView handleWebClientResponseException(WebClientResponseException ex, RedirectAttributes redirectAttributes) throws IOException {
        log.error("Error from WebClient - Status {}, Body {}", ex.getRawStatusCode(), ex.getResponseBodyAsString(), ex);
        ModelAndView modelAndView = new ModelAndView("error");
        ObjectMapper objectMapper = new ObjectMapper();
        Error error = objectMapper.readValue(ex.getResponseBodyAsByteArray(), Error.class);
        modelAndView.addObject("responseStatusCode", ex.getStatusCode());
        modelAndView.addObject("message", error.getMessage());
        modelAndView.addObject("details", error.getDetails());
        return modelAndView;
    }

}
