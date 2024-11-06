package com.springbuds.loans.controller;

import com.springbuds.loans.constants.LoansConstants;
import com.springbuds.loans.dto.ErrorResponseDto;
import com.springbuds.loans.dto.LoansDto;
import com.springbuds.loans.dto.ResponseDto;
import com.springbuds.loans.service.LoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "CRUD rest api for Loans microservice",
        description = "CRUD rest api for Loans microservice"
)
@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class LoansController {

    private final LoansService loansService;

    @Operation(summary = "Creates loan by a mobile number")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Status Created"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam
                                                  @Pattern(regexp="(^[0-9]{10}$)", message = "Mobile number must be 10 digits")
                                                  String mobileNumber) {
        loansService.createLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .code(HttpStatus.CREATED.value())
                        .message(LoansConstants.MESSAGE_201).build());
    }

    @Operation(summary = "Gets loan details by a phone number")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status Ok"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam
                                                     @Pattern(regexp="(^[0-9]{10}$)", message = "Mobile number must be 10 digits")
                                                     String mobileNumber) {
        LoansDto loansDto = loansService.fetchLoan(mobileNumber);
        return ResponseEntity.ok(loansDto);
    }

    @Operation(summary = "Updates a loan")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "No Content"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<Void> updateLoanDetails(@Valid @RequestBody LoansDto dto) {
        loansService.updateLoan(dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletes a loan by a phone number")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Status Ok"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoanDetails(@RequestParam
                                                         @Pattern(regexp="(^[0-9]{10}$)", message = "Mobile number must be 10 digits")
                                                         String mobileNumber) {
        loansService.deleteLoan(mobileNumber);
        return ResponseEntity.ok(ResponseDto.builder()
                        .message(LoansConstants.MESSAGE_DELETED)
                        .code(HttpStatus.OK.value())
                .build());
    }
}
