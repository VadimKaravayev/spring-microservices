package com.springbuds.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Schema(name = "Accounts")
@Builder
@Data
public class AccountsDto {

    @Schema(example = "1569152940")
    @NotEmpty(message = "account number must  not be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must 10 digits")
    private Long accountNumber;

    @Schema(example = "Savings")
    @NotEmpty(message = "account type must not be empty")
    private String accountType;

    @Schema(example = "123 Main Street, New York")
    @NotEmpty(message = "branch address must not be empty")
    private String branchAddress;
}
