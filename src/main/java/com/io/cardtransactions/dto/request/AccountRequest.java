package com.io.cardtransactions.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request object for creating a new account")
public class AccountRequest {

    @NotBlank(message = "Document number is required")
    @Size(min = 11, max = 20, message = "Document number must be between 11 and 20 characters")
    @JsonProperty(value = "document_number")
    @Schema(
            description = "The unique document number associated with the account holder (e.g., CPF, CNPJ, or SSN)",
            example = "12345678900",
            minLength = 11,
            maxLength = 20,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String documentNumber;
}