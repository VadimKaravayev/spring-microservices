package com.springbuds.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Schema(name = "Customer")
@Builder
@Data
public class CustomerDto{

    @Schema(description = "Name of a customer", example = "John Doe")
    @NotEmpty(message = "Name must not be empty")
    @Size(min = 3, max = 30, message = "Size of the name must be between 3 and 30 characters")
    private String name;

    @Schema(example = "john@doe.com")
    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Provide a valid email")
    private String email;

    @Schema(example = "5551112233")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must 10 digits")
    @NotEmpty
    private String mobileNumber;

    private AccountsDto accounts;
}
