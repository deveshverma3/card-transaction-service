package com.io.cardtransactions.controller;

import com.io.cardtransactions.dto.request.TransactionRequest;
import com.io.cardtransactions.dto.response.TransactionResponse;
import com.io.cardtransactions.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction Controller", description = "Operations related to transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Transaction Related operation", description = "Creates a new Transaction with the provided transaction request")
    @ApiResponse(responseCode = "201", description = "Transaction created successfully")
    @PostMapping
    public ResponseEntity<TransactionResponse> create(@Valid @RequestBody TransactionRequest transactionRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.create(transactionRequest));
    }
}
