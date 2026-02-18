package com.io.cardtransactions.service;

import com.io.cardtransactions.domain.Account;
import com.io.cardtransactions.domain.OperationType;
import com.io.cardtransactions.domain.Transaction;
import com.io.cardtransactions.dto.request.TransactionRequest;
import com.io.cardtransactions.mapper.TransactionMapper;
import com.io.cardtransactions.repository.AccountRepository;
import com.io.cardtransactions.repository.OperationTypeRepository;
import com.io.cardtransactions.repository.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private OperationTypeRepository operationTypeRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    @DisplayName("Should save purchase transaction with negative amount")
    void shouldSavePurchaseAsNegative() {
        TransactionRequest request = new TransactionRequest();
        request.setAccountId(1L);
        request.setOperationTypeId((short) 1);
        request.setAmount(new BigDecimal("50.00"));

        OperationType type = new OperationType();
        type.setOperationTypeId((short) 1);
        type.setChargeModifier(new BigDecimal("-1"));

        when(operationTypeRepository.findByOperationTypeId((short) 1)).thenReturn(Optional.of(type));
        when(accountRepository.findById(1L)).thenReturn(Optional.of(new Account()));

        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal("-50.00"));
        when(transactionMapper.toTransaction(any())).thenReturn(transaction);
        when(transactionRepository.save(any())).thenReturn(transaction);

        transactionService.create(request);

        assertEquals(new BigDecimal("-50.00"), request.getAmount(), "The amount in the request should be normalized to negative");
        verify(transactionRepository, times(1)).save(any());
    }
}
