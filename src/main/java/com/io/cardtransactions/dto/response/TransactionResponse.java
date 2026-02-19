package com.io.cardtransactions.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionResponse {
    @JsonProperty(value = "transaction_id")
    BigInteger transactionId;
    @JsonProperty(value = "account_id")
    private BigInteger accountId;
    @JsonProperty(value = "operation_type")
    String operationType;
    private BigDecimal amount;
    @JsonProperty(value = "created_at")
    private LocalDateTime createdAt;
}
