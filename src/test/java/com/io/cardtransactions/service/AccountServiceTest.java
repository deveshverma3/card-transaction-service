package com.io.cardtransactions.service;

import com.io.cardtransactions.domain.Account;
import com.io.cardtransactions.dto.request.AccountRequest;
import com.io.cardtransactions.dto.response.AccountResponse;
import com.io.cardtransactions.exception.EntityNotFoundException;
import com.io.cardtransactions.mapper.AccountMapper;
import com.io.cardtransactions.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountService accountService;

    @Test
    @DisplayName("Should create account successfully when document does not exist")
    void shouldCreateAccountSuccessfully() {
        AccountRequest request = new AccountRequest();
        request.setDocumentNumber("12345678900");

        Account account = new Account();
        account.setDocumentNumber("12345678900");

        AccountResponse expectedResponse = new AccountResponse();
        expectedResponse.setAccountId(1L);
        expectedResponse.setDocumentNumber("12345678900");

        when(accountRepository.existsByDocumentNumber("12345678900")).thenReturn(false);
        when(accountMapper.toAccount(request)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.toAccountResponse(account)).thenReturn(expectedResponse);

        AccountResponse response = accountService.create(request);

        assertNotNull(response);
        assertEquals("12345678900", response.getDocumentNumber());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when creating account with existing document")
    void shouldThrowExceptionWhenDocumentExists() {
        AccountRequest request = new AccountRequest();
        request.setDocumentNumber("12345678900");

        when(accountRepository.existsByDocumentNumber("12345678900")).thenReturn(true);

        assertThrows(EntityNotFoundException.class, () -> accountService.create(request));
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    @DisplayName("Should return account response when finding by valid ID")
    void shouldReturnAccountWhenIdExists() {
        Long accountId = 1L;
        Account account = new Account();
        account.setAccountId(accountId);

        AccountResponse expectedResponse = new AccountResponse();
        expectedResponse.setAccountId(accountId);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountMapper.toAccountResponse(account)).thenReturn(expectedResponse);

        AccountResponse response = accountService.getById(accountId);

        assertNotNull(response);
        assertEquals(accountId, response.getAccountId());
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when finding by non-existent ID")
    void shouldThrowExceptionWhenIdDoesNotExist() {
        Long accountId = 99L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.getById(accountId));
    }
}
