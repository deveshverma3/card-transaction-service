package com.io.cardtransactions.util;

import com.io.cardtransactions.domain.OperationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionUtilTest {

    @Test
    @DisplayName("Should normalize negative amount to positive and apply positive modifier")
    void normalizeAmount_ShouldConvertNegativeToPositive() {

        BigDecimal amount = new BigDecimal("-100.00");

        OperationType type = mock(OperationType.class);
        when(type.getChargeModifier()).thenReturn(new BigDecimal("1"));

        BigDecimal result = TransactionUtil.normalizeAmount(amount, type);

        assertEquals(new BigDecimal("100.00"), result);
    }

    @Test
    @DisplayName("Should keep amount positive and apply negative modifier")
    void normalizeAmount_ShouldApplyNegativeModifier() {

        BigDecimal amount = new BigDecimal("100.00");

        OperationType type = mock(OperationType.class);
        when(type.getChargeModifier()).thenReturn(new BigDecimal("-1"));

        BigDecimal result = TransactionUtil.normalizeAmount(amount, type);

        assertEquals(new BigDecimal("-100.00"), result);
    }

    @Test
    @DisplayName("Should return zero when amount is zero")
    void normalizeAmount_ShouldReturnZero() {

        BigDecimal amount = BigDecimal.ZERO;

        OperationType type = mock(OperationType.class);
        when(type.getChargeModifier()).thenReturn(new BigDecimal("1"));

        BigDecimal result = TransactionUtil.normalizeAmount(amount, type);

        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    @DisplayName("Should handle large amounts correctly")
    void normalizeAmount_ShouldHandleLargeNumbers() {

        BigDecimal amount = new BigDecimal("-999999999999.99");

        OperationType type = mock(OperationType.class);
        when(type.getChargeModifier()).thenReturn(new BigDecimal("1"));

        BigDecimal result = TransactionUtil.normalizeAmount(amount, type);

        assertEquals(new BigDecimal("999999999999.99"), result);
    }

    @Test
    @DisplayName("Should throw NullPointerException when amount is null")
    void normalizeAmount_ShouldThrowException_WhenAmountIsNull() {

        OperationType type = mock(OperationType.class);
        when(type.getChargeModifier()).thenReturn(new BigDecimal("1"));

        assertThrows(NullPointerException.class, () ->
                TransactionUtil.normalizeAmount(null, type)
        );
    }

    @Test
    @DisplayName("Should throw NullPointerException when operationType is null")
    void normalizeAmount_ShouldThrowException_WhenOperationTypeIsNull() {

        BigDecimal amount = new BigDecimal("100");

        assertThrows(NullPointerException.class, () ->
                TransactionUtil.normalizeAmount(amount, null)
        );
    }

    @Test
    @DisplayName("Should throw NullPointerException when chargeModifier is null")
    void normalizeAmount_ShouldThrowException_WhenChargeModifierIsNull() {

        BigDecimal amount = new BigDecimal("100");

        OperationType type = mock(OperationType.class);
        when(type.getChargeModifier()).thenReturn(null);

        assertThrows(NullPointerException.class, () ->
                TransactionUtil.normalizeAmount(amount, type)
        );
    }
}