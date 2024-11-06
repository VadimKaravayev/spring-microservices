package com.springbuds.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

@Schema(name = "Loans", description = "Schema to hold Loan information")
@Data
@Builder
public class LoansDto {
    private Long loanId;

    @Pattern(regexp="(^[0-9]{10}$)", message = "Mobile number must be 10 digits")
    @Schema(description = "Customer's mobile number", example = "4561233778")
    private String mobileNumber;

    @Pattern(regexp="(^[0-9]{12}$)", message = "Loan number must be 12 digits")
    @Schema(description = "Customer's loan number", example = "100704964390")
    private String loanNumber;

    @NotEmpty(message = "Loan type must not be empty")
    @Schema(description = "Type of a loan", example = "Home Loan")
    private String loanType;

    @Positive(message = "Total loan must be greater than 0")
    @Schema(description = "Total loan amount", example = "1000")
    private int totalLoan;

    @PositiveOrZero(message = "Amount paid must be 0 or greater")
    @Schema(description = "Total loan amount paid", example = "1000")
    private int amountPaid;

    @PositiveOrZero(message = "outstanding amount must be zero or greater")
    @Schema(description = "Total outstanding amount against a loan", example = "99000")
    private int outstandingAmount;
}
