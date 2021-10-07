package com.disco.shoppingCart.user.response;


import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class UserResponse {

    private HttpStatus status;
    @JsonRawValue
    private String requestBody;
}
