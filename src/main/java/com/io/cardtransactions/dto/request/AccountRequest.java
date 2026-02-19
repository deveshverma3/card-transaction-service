package com.io.cardtransactions.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request object for creating a new account")
public class AccountRequest {


    @NotBlank(message = "Document number is required")
    @Pattern(
            regexp = "^[A-Za-z0-9]{11,34}$",
            message = "Account identifier must be 11 to 34 alphanumeric characters"
    )
    @JsonProperty(value = "document_number")
    @Schema(
            description = "The unique document number associated with the account holder (e.g. 12345678900), Can be alphanumeric",
            example = "12345678900",
            minLength = 11,
            maxLength = 20,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String documentNumber;
}