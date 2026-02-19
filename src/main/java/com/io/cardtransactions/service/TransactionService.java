package com.io.cardtransactions.service;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.io.cardtransactions.util.TransactionUtil.normalizeAmount;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final AccountRepository accountRepository;
    private final OperationTypeRepository operationTypeRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Transactional
    public TransactionResponse create(TransactionRequest request) {

        var account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Account", "accountId", request.getAccountId()));

        var operationType = operationTypeRepository
                .findByOperationTypeId(request.getOperationTypeId())
                .orElseThrow(() ->
                        new EntityNotFoundException("OperationType", "operationTypeId", request.getOperationTypeId()));

        BigDecimal normalized = normalizeAmount(request.getAmount(), operationType);

        var transaction = new Transaction(account, operationType, normalized);

        var saved = transactionRepository.save(transaction);

        return transactionMapper.toTransactionResponse(saved);
    }
}
