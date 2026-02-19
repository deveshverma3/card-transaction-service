package com.io.cardtransactions.repository;

import com.io.cardtransactions.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, BigInteger> {

    Transaction findByTransactionId(BigInteger transactionId);

}
