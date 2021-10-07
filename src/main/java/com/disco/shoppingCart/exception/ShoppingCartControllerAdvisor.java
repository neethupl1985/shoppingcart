package com.disco.shoppingCart.exception;

import com.disco.shoppingCart.utils.MdcUtil;
import com.disco.shoppingCart.utils.StatsUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@Slf4j
@ControllerAdvice
public class ShoppingCartControllerAdvisor {
    private final ObjectMapper objectMapper;

    public ShoppingCartControllerAdvisor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler({DataAccessException.class, SQLException.class})
    public ResponseEntity<Object> handle(ShoppingCartExceptionHandler exception) throws JsonProcessingException {

        String msg = objectMapper.writeValueAsString(createErrorResponse(exception.getType(),
                exception.getTitle(),
                exception.getDetail()));
        MdcUtil.put(StatsUtil.OPERATION_STATUS.toString(), exception.getType());
        return ResponseEntity.status(exception.getStatusCode()).contentType(MediaType.APPLICATION_JSON).body(msg);
    }

    public static BaseErrorResponse createErrorResponse(final String type, final String title, final Object detail) {
        return BaseErrorResponse.builder()
                .type(type)
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
        private final Object detail;

        @JsonIgnore
        private final int statusCode;
    }
}
