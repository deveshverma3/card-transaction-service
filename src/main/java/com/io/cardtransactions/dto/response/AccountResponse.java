package com.io.cardtransactions.dto.response;

import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class AccountResponse {
     private BigInteger accountId;
     private String documentNumber;
     private LocalDateTime createdAt;
}
