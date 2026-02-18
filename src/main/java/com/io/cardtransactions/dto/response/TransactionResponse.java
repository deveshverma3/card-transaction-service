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
    private Long accountId;
    Short operationTypeId;
    private BigDecimal amount;
    private LocalDateTime eventDate;
}
