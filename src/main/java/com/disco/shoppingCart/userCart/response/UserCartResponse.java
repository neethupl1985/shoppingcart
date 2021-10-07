package com.disco.shoppingCart.userCart.response;


import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class UserCartResponse {

    private HttpStatus status;
    @JsonRawValue
    private String requestBody;
}
