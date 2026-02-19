package com.io.cardtransactions.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class AccountResponse {
    @JsonProperty(value = "account_id")
    private BigInteger accountId;
    @JsonProperty(value = "document_number")
    private String documentNumber;
    @JsonProperty(value = "created_at")
    private LocalDateTime createdAt;
}
