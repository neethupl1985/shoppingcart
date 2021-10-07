package com.disco.shoppingCart.inventory.model;


import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class InventoryResponse {

    private HttpStatus status;
    @JsonRawValue
    private String requestBody;
}
