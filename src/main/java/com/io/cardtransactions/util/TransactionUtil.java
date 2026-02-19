package com.io.cardtransactions.util;

import com.io.cardtransactions.domain.OperationType;

import java.math.BigDecimal;

public class TransactionUtil {

    public static BigDecimal normalizeAmount(BigDecimal amount, OperationType type) {
        return amount.abs().multiply(type.getChargeModifier());
    }
}
