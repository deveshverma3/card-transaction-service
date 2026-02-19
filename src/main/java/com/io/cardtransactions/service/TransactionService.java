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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

        log.debug("Starting transaction creation for accountId={}, operationTypeId={}", request.getAccountId(), request.getOperationTypeId());

        accountRepository.findById(request.getAccountId()).orElseThrow(() -> {
            log.warn("Account not found with id={}", request.getAccountId());
            return new EntityNotFoundException("Account", "accountId", request.getAccountId()
            );
        });


        OperationType operationType = operationTypeRepository.findByOperationTypeId(request.getOperationTypeId()).orElseThrow(() -> {
            log.warn("OperationType not found with id={}", request.getOperationTypeId());
            return new EntityNotFoundException("OperationType", "operationTypeId", request.getOperationTypeId()
            );
        });

        log.debug("Applying charge modifier {} to amount {}", operationType.getChargeModifier(), request.getAmount());

        request.setAmount(normalizeAmount(request.getAmount(), operationType));
        Transaction transaction = transactionMapper.toTransaction(request);

        Transaction saved = transactionRepository.save(transaction);
        saved.setOperationType(operationType);

        log.info("Transaction created successfully with id={} for accountId={}", saved.getTransactionId(), request.getAccountId());

        return transactionMapper.toTransactionResponse(saved);

    }
}
