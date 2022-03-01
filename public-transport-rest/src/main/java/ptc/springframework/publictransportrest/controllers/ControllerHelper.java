package ptc.springframework.publictransportrest.controllers;


import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ControllerHelper {
    public static final String X_PAGE = "X-Page";
    public static final String X_SIZE = "X-Size";
    public static final String X_TOTAL_PAGES = "X-Total-Pages";
    public static final String X_TOTAL_SIZE = "X-Total-Size";

    public static <T> HttpHeaders buildHeaders(Long xPage, Long xSize, Page<T> page) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PAGE, String.valueOf(xPage));
        headers.add(X_SIZE, String.valueOf(xSize));
        headers.add(X_TOTAL_PAGES, String.valueOf(page.getTotalPages()));
        headers.add(X_TOTAL_SIZE, String.valueOf(page.getTotalElements()));
        return headers;
    }

    public static <T> ResponseEntity<List<T>> buildPartialResponse(Long xPage, Long xSize, Page<T> page) {
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .headers(ControllerHelper.buildHeaders(xPage, xSize, page))
                .body(page.getContent());
    }
}
