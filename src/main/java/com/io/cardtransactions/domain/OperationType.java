package com.io.cardtransactions.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class OperationType {

    @Id
    @Column(name = "operation_type_id")
    Short operationTypeId;
    @Column(name = "description")
    String description;
    @Column(name = "charge_modifier")
    BigDecimal chargeModifier;
}
