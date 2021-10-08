package com.disco.shoppingcart.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ShoppingCartControllerAdvisor extends ResponseEntityExceptionHandler {

    private final ObjectMapper objectMapper;

    public ShoppingCartControllerAdvisor(ObjectMapper objectMapper) {
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        this.objectMapper = objectMapper;
    }

    @ExceptionHandler({ShoppingCartException.class})
    public ResponseEntity<Object> handle(ShoppingCartException exception) throws JsonProcessingException {
        String msg = objectMapper.writeValueAsString(createErrorResponse(
                exception.getTitle(),
                exception.getDetail()));
        log.info("{}", exception.getTitle());

        return ResponseEntity.status(exception.getStatusCode()).contentType(MediaType.APPLICATION_JSON).body(msg);
    }

    public static BaseErrorResponse createErrorResponse(final String title, final String detail) {
        return BaseErrorResponse.builder()
                .title(title)
                .detail(detail)
                .build();
    }

    @Builder
    @Getter
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
    public static class BaseErrorResponse {
        private final String type;
        private final String title;
        private final String detail;
        @JsonIgnore
        private final int statusCode;
    }
}
