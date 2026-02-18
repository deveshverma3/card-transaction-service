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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;

    private final OperationTypeRepository operationTypeRepository;

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    @Transactional
    public TransactionResponse create(TransactionRequest request) {

        OperationType operationType = operationTypeRepository.findByOperationTypeId(request.getOperationTypeId())
                .orElseThrow(() -> new EntityNotFoundException("OperationType", request.getOperationTypeId()));

        accountRepository.findById(request.getAccountId()).orElseThrow(() -> new EntityNotFoundException("Account", request.getAccountId()));

        request.setAmount(normalizeAmount(request.getAmount(), operationType));
        Transaction transaction = transactionMapper.toTransaction(request);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return transactionMapper.toTransactionResponse(savedTransaction);

    }

    private BigDecimal normalizeAmount(BigDecimal amount, OperationType type) {
        return amount.abs().multiply(type.getChargeModifier());
    }
}
