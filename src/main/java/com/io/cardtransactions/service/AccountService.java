package com.io.cardtransactions.service;

import com.io.cardtransactions.dto.request.AccountRequest;
import com.io.cardtransactions.dto.response.AccountResponse;
import com.io.cardtransactions.exception.EntityAlreadyExistsException;
import com.io.cardtransactions.exception.EntityNotFoundException;
import com.io.cardtransactions.mapper.AccountMapper;
import com.io.cardtransactions.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountResponse create(AccountRequest accountRequest) {

        log.debug("Checking if account exists with documentNumber={}", accountRequest.getDocumentNumber());

        if (accountRepository.existsByDocumentNumber(accountRequest.getDocumentNumber())) {
            throw new EntityAlreadyExistsException("Account", "documentNumber", accountRequest.getDocumentNumber());
        }

        var savedAccount = accountRepository.save(accountMapper.toAccount(accountRequest));

        log.info("Account created successfully with id={}", savedAccount.getAccountId());

        return accountMapper.toAccountResponse(savedAccount);
    }

    public AccountResponse getById(BigInteger accountId) {

        var account = accountRepository.findById(accountId).orElseThrow(() -> new EntityNotFoundException("Account", "accountId", accountId));

        return accountMapper.toAccountResponse(account);
    }
}