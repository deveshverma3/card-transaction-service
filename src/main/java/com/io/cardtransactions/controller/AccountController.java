package com.io.cardtransactions.controller;

import com.io.cardtransactions.dto.request.AccountRequest;
import com.io.cardtransactions.dto.response.AccountResponse;
import com.io.cardtransactions.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/accounts")
@RequiredArgsConstructor
@Tag(name = "Accounts Controller", description = "Operations related to accounts")
public class AccountController {

    private final AccountService accountService;

    @Operation(
            summary = "Create account",
            description = "Creates a new account with the provided document number"
    )
    @ApiResponse(responseCode = "201", description = "Account successfully created")
    @PostMapping
    ResponseEntity<AccountResponse> create(@Valid @RequestBody AccountRequest accountRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.create(accountRequest));
    }

    @Operation(
            summary = "Get account by ID",
            description = "Retrieves account information by account ID"
    )
    @ApiResponse(responseCode = "200", description = "Account found")
    @ApiResponse(responseCode = "404", description = "Account not found")
    @GetMapping("/{accountId}")
    ResponseEntity<AccountResponse> getById(@PathVariable Long accountId) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getById(accountId));
    }
}
