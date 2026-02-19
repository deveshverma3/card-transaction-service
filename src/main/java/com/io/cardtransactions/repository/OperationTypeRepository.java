package com.io.cardtransactions.repository;

import com.io.cardtransactions.domain.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperationTypeRepository extends JpaRepository<OperationType, Short> {

    Optional<OperationType> findByOperationTypeId(Short operationTypeId);

}
