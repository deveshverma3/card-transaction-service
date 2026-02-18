package com.io.cardtransactions.service;

import com.io.cardtransactions.domain.Account;
import com.io.cardtransactions.dto.request.AccountRequest;
import com.io.cardtransactions.dto.response.AccountResponse;
import com.io.cardtransactions.exception.EntityNotFoundException;
import com.io.cardtransactions.mapper.AccountMapper;
import com.io.cardtransactions.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    public AccountResponse create(AccountRequest accountRequest) {

        boolean exists = accountRepository.existsByDocumentNumber(accountRequest.getDocumentNumber());

        if (exists) throw new EntityNotFoundException("Account", accountRequest.getDocumentNumber());

        Account savedAccount = accountRepository.save(accountMapper.toAccount(accountRequest));

        return accountMapper.toAccountResponse(savedAccount);
    }

    public AccountResponse getById(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new EntityNotFoundException("Account", accountId));

        return accountMapper.toAccountResponse(account);
    }
}