package com.io.cardtransactions.service;

import com.io.cardtransactions.domain.OperationType;
import com.io.cardtransactions.domain.Transaction;
import com.io.cardtransactions.dto.request.TransactionRequest;
import com.io.cardtransactions.dto.response.TransactionResponse;
import com.io.cardtransactions.exception.EntityNotFoundException;
import com.io.cardtransactions.mapper.TransactionMapper;
import com.io.cardtransactions.repository.AccountRepository;
import com.io.cardtransactions.repository.OperationTypeRepository;
import com.io.cardtransactions.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    private TransactionRequest request;
    private OperationType operationType;
    private Transaction transaction;
    private TransactionResponse response;

    @BeforeEach
    void setUp() {
        request = new TransactionRequest();
        request.setAccountId(BigInteger.valueOf(1));
        request.setOperationTypeId((short) 2);
        request.setAmount(BigDecimal.valueOf(100));

        operationType = new OperationType();
        operationType.setOperationTypeId((short) 2);
        operationType.setDescription("Purchase");
        operationType.setChargeModifier(BigDecimal.valueOf(-1));

        transaction = new Transaction();
        transaction.setTransactionId(BigInteger.valueOf(10));
        transaction.setAmount(request.getAmount());

        response = new TransactionResponse();
        response.setTransactionId(BigInteger.valueOf(10));
        response.setAccountId(BigInteger.valueOf(1));
        response.setOperationType("Purchase");
        response.setAmount(BigDecimal.valueOf(100));
    }

    @Test
    void testCreateTransactionSuccessfully() {
        when(accountRepository.findById(request.getAccountId())).thenReturn(Optional.of(mock(com.io.cardtransactions.domain.Account.class)));

        when(operationTypeRepository.findByOperationTypeId(request.getOperationTypeId())).thenReturn(Optional.of(operationType));

        when(transactionMapper.toTransaction(request)).thenReturn(transaction);

        when(transactionRepository.save(transaction)).thenReturn(transaction);

        when(transactionMapper.toTransactionResponse(transaction)).thenReturn(response);

        TransactionResponse result = transactionService.create(request);

        assertEquals(request.getAmount().multiply(operationType.getChargeModifier()), transaction.getAmount());

        verify(transactionMapper).toTransaction(request);
        verify(transactionRepository).save(transaction);
        verify(transactionMapper).toTransactionResponse(transaction);

        assertEquals(response.getTransactionId(), result.getTransactionId());
        assertEquals("Purchase", result.getOperationType());
    }

    @Test
    void testCreateTransactionAccountNotFound() {
        when(accountRepository.findById(request.getAccountId())).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> transactionService.create(request));

        assertTrue(ex.getMessage().contains("Account"));
        assertTrue(ex.getMessage().contains(request.getAccountId().toString()));

        verify(accountRepository).findById(request.getAccountId());
        verifyNoInteractions(operationTypeRepository, transactionRepository, transactionMapper);
    }

    @Test
    void testCreateTransactionOperationTypeNotFound() {
        when(accountRepository.findById(request.getAccountId())).thenReturn(Optional.of(mock(com.io.cardtransactions.domain.Account.class)));
        when(operationTypeRepository.findByOperationTypeId(request.getOperationTypeId())).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> transactionService.create(request));

        assertTrue(ex.getMessage().contains("OperationType"));
        assertTrue(ex.getMessage().contains(request.getOperationTypeId().toString()));

        verify(accountRepository).findById(request.getAccountId());
        verify(operationTypeRepository).findByOperationTypeId(request.getOperationTypeId());
        verifyNoInteractions(transactionRepository, transactionMapper);
    }

}