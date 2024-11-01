package com.springbuds.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(
        name = "CreateCard",
        description = "Schema to hold data needed for a card creation"
)
@Data
public class CreateCardDto {

    @Schema(description = "Customer's phone number", example = "555512123434")
    @NotEmpty(message = "Mobile number must not be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;
}
