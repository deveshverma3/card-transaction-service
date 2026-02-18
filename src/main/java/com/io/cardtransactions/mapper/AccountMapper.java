package com.io.cardtransactions.mapper;

import com.io.cardtransactions.domain.Account;
import com.io.cardtransactions.dto.request.AccountRequest;
import com.io.cardtransactions.dto.response.AccountResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account toAccount(AccountRequest accountRequest);

    AccountResponse toAccountResponse(Account account);
}