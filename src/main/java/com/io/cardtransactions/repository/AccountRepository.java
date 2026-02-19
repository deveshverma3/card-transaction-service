package com.io.cardtransactions.repository;

import com.io.cardtransactions.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, BigInteger> {

    Optional<Account> findById(BigInteger accountId);

    boolean existsByDocumentNumber(String documentNumber);
}
