package com.springbuds.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

@Schema(
        name = "Cards",
        description = "Schema to hold card information"
)
@Data
@Builder
public class CardsDto {

    @Schema(description = "Customer's phone number", example = "555512123434")
    @NotEmpty(message = "Mobile number must not be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(description = "Card number", example = "123456789012")
    @NotEmpty(message = "Card number must not be empty")
    @Pattern(regexp = "(^$|[0-9]{12})", message = "Card number must be 12 digits")
    private String cardNumber;

    @Schema(description = "Card type", example = "Credit Card")
    @NotEmpty
    private String cardType;

    @Schema(description = "Total amount limit available", example = "100000")
    @Positive(message = "Total limit value must be greater than zero")
    private int totalLimit;

    @Schema(description = "Amount used")
    @PositiveOrZero(message = "amount used must be greater or equal to zero")
    private int amountUsed;

    @Schema(description = "Available amount")
    @PositiveOrZero(message = "availableAmount must be greater or equal to zero")
    private int availableAmount;
}
