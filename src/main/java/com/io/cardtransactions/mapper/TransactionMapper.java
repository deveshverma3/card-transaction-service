package com.io.cardtransactions.mapper;

import com.io.cardtransactions.domain.Transaction;
import com.io.cardtransactions.dto.request.TransactionRequest;
import com.io.cardtransactions.dto.response.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(source = "accountId", target = "account.accountId")
    Transaction toTransaction(TransactionRequest transactionRequest);

    @Mapping(source = "account.accountId", target = "accountId")
    TransactionResponse toTransactionResponse(Transaction transaction);
}