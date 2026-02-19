package com.io.cardtransactions.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
public class TransactionRequest {
    @NotNull(message = "Account ID is required")
    @JsonProperty(value = "account_id")
    @Schema(
            description = "Unique identifier of the account",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private BigInteger accountId;

    @NotNull(message = "Operation Type ID is required")
    @JsonProperty(value = "operation_type_id")
    @Schema(
            description = "Type of operation (1: Normal Purchase, 2: Purchase with installments, 3: Withdrawal, 4: Credit Voucher)",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Short operationTypeId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    @Schema(
            description = "The transaction amount (should be sent as a positive value; the system will normalize the sign based on the operation type)",
            example = "50.00",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private BigDecimal amount;
}
