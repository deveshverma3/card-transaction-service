package com.io.cardtransactions.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    Long accountId;
    @Column(name = "document_number", nullable = false, unique = true, length = 20)
    String documentNumber;
}
