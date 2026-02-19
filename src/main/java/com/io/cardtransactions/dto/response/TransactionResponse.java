package com.io.cardtransactions.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionResponse {
    BigInteger transactionId;
    private BigInteger accountId;
    String operationType;
    private BigDecimal amount;
    private LocalDateTime createdAt;
}
