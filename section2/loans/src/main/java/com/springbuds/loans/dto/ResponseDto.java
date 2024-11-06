package com.springbuds.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(name = "Response", description = "Response Data")
@Data
@Builder
public class ResponseDto {
    private int code;
    private String message;
}
