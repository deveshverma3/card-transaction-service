package com.io.cardtransactions.service;

import com.io.cardtransactions.domain.Account;
import com.io.cardtransactions.dto.request.AccountRequest;
import com.io.cardtransactions.dto.response.AccountResponse;
import com.io.cardtransactions.exception.DataIntegrityViolationException;
import com.io.cardtransactions.exception.EntityNotFoundException;
import com.io.cardtransactions.mapper.AccountMapper;
import com.io.cardtransactions.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountService accountService;

    private AccountRequest accountRequest;
    private Account account;
    private AccountResponse accountResponse;

    @BeforeEach
    void setUp() {
        accountRequest = new AccountRequest();
        accountRequest.setDocumentNumber("123456789");

        account = new Account();
        account.setAccountId(BigInteger.valueOf(1));
        account.setDocumentNumber("123456789");

        accountResponse = new AccountResponse();
        accountResponse.setAccountId(BigInteger.valueOf(1));
        accountResponse.setDocumentNumber("123456789");
    }

    @Test
    void testCreateAccountSuccessfully() {
        when(accountRepository.existsByDocumentNumber(accountRequest.getDocumentNumber())).thenReturn(false);
        when(accountMapper.toAccount(accountRequest)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.toAccountResponse(account)).thenReturn(accountResponse);

        AccountResponse result = accountService.create(accountRequest);

        assertNotNull(result);
        assertEquals(accountResponse.getAccountId(), result.getAccountId());
        verify(accountRepository).existsByDocumentNumber(accountRequest.getDocumentNumber());
        verify(accountRepository).save(account);
    }

    @Test
    void testCreateAccountAlreadyExists() {
        when(accountRepository.existsByDocumentNumber(accountRequest.getDocumentNumber())).thenReturn(true);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class,
                () -> accountService.create(accountRequest));

        assertTrue(exception.getMessage().contains("Account already exists"));
        verify(accountRepository, never()).save(any());
    }

    @Test
    void testGetByIdSuccessfully() {
        when(accountRepository.findById(BigInteger.valueOf(1))).thenReturn(Optional.of(account));
        when(accountMapper.toAccountResponse(account)).thenReturn(accountResponse);

        AccountResponse result = accountService.getById(BigInteger.valueOf(1));

        assertNotNull(result);
        assertEquals(BigInteger.valueOf(1), result.getAccountId());
        verify(accountRepository).findById(BigInteger.valueOf(1));
    }

    @Test
    void testGetByIdNotFound() {
        when(accountRepository.findById(BigInteger.valueOf(1))).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> accountService.getById(BigInteger.valueOf(1)));

        assertTrue(exception.getMessage().contains("Account not found"));
        verify(accountRepository).findById(BigInteger.valueOf(1));
    }
}