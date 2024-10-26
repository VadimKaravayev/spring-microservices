package com.springbuds.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(name = "Response")
@Builder
@Data
public class ResponseDto {
    private String statusCode;
    private String statusMsg;
}
