package com.io.cardtransactions.service;

import com.io.cardtransactions.domain.Account;
import com.io.cardtransactions.domain.OperationType;
import com.io.cardtransactions.domain.Transaction;
import com.io.cardtransactions.dto.request.TransactionRequest;
import com.io.cardtransactions.dto.response.TransactionResponse;
import com.io.cardtransactions.exception.EntityNotFoundException;
import com.io.cardtransactions.mapper.TransactionMapper;
import com.io.cardtransactions.repository.AccountRepository;
import com.io.cardtransactions.repository.OperationTypeRepository;
import com.io.cardtransactions.repository.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    @DisplayName("Should normalize amount to positive when operation type modifier is +1")
    void create_ShouldNormalizeAmountToPositive() {

        TransactionRequest request = new TransactionRequest();
        request.setAccountId(BigInteger.valueOf(123));
        request.setOperationTypeId((short) 4);
        request.setAmount(new BigDecimal("100.00"));

        Account account = new Account();

        OperationType operationType = new OperationType();
        operationType.setOperationTypeId((short) 4);
        operationType.setChargeModifier(BigDecimal.ONE);

        when(accountRepository.findById(request.getAccountId())).thenReturn(Optional.of(account));

        when(operationTypeRepository.findByOperationTypeId(request.getOperationTypeId())).thenReturn(Optional.of(operationType));

        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(transactionMapper.toTransactionResponse(any(Transaction.class))).thenReturn(new TransactionResponse());

        transactionService.create(request);

        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);

        verify(transactionRepository).save(captor.capture());

        Transaction savedTransaction = captor.getValue();

        assertEquals(new BigDecimal("100.00"), savedTransaction.getAmount());
    }

    @Test
    @DisplayName("Should normalize amount to negative when operation type modifier is -1")
    void create_ShouldNormalizeAmountToNegative() {

        TransactionRequest request = new TransactionRequest();
        request.setAccountId(BigInteger.valueOf(123));
        request.setOperationTypeId((short) 1);
        request.setAmount(new BigDecimal("100.00"));

        Account account = new Account();

        OperationType operationType = new OperationType();
        operationType.setOperationTypeId((short) 1);
        operationType.setChargeModifier(new BigDecimal("-1"));

        when(accountRepository.findById(request.getAccountId())).thenReturn(Optional.of(account));

        when(operationTypeRepository.findByOperationTypeId(request.getOperationTypeId())).thenReturn(Optional.of(operationType));

        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(transactionMapper.toTransactionResponse(any(Transaction.class))).thenReturn(new TransactionResponse());

        transactionService.create(request);

        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);

        verify(transactionRepository).save(captor.capture());

        Transaction savedTransaction = captor.getValue();

        assertEquals(new BigDecimal("-100.00"), savedTransaction.getAmount());
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when Account is missing")
    void create_ShouldThrowException_WhenAccountNotFound() {

        TransactionRequest request = new TransactionRequest();
        request.setAccountId(BigInteger.valueOf(999));

        when(accountRepository.findById(any())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                transactionService.create(request)
        );

        assertTrue(exception.getMessage().contains("Account"));
        verify(transactionRepository, never()).save(any());
    }
}