package com.springbuds.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(name = "Error Response")
@Builder
@Data
public class ErrorResponseDto {
    private String apiPath;
    private HttpStatus status;
    private String errorMsg;
    private LocalDateTime errorTime;
}
