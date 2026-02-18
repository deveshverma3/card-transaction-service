package com.io.cardtransactions.dto.request;


import com.io.cardtransactions.dto.response.FieldErrorResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ApiError {

    private int status;
    private String error;
    private String message;
    private String path;
    private List<FieldErrorResponse> errors;
    private LocalDateTime timestamp;
}
