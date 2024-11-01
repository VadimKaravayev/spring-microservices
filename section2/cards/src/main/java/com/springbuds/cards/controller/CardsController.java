package com.springbuds.cards.controller;

import com.springbuds.cards.constants.CardsConstants;
import com.springbuds.cards.dto.CardsDto;
import com.springbuds.cards.dto.CreateCardDto;
import com.springbuds.cards.dto.ErrorResponseDto;
import com.springbuds.cards.dto.ResponseDto;
import com.springbuds.cards.service.CardsService;
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

import static com.springbuds.cards.constants.CardsConstants.MESSAGE_201;
import static com.springbuds.cards.constants.CardsConstants.STATUS_201;

@Tag(
        name = "CRUD REST Api for Cards",
        description = "Create, Read, Update and Delete Cards"
)
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CardsController {

    private final CardsService cardsService;

    @Operation(summary = "Fetch Card details", description = "Fetch Card details")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status Ok"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "NOT FOUND"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "ETERNAL SERVER ERROR"
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestParam
                                                     @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
                                                     String mobileNumber) {
        CardsDto cardsDto = cardsService.fetchCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
    }

    @Operation(summary = "Creates a card", description = "Creates a card. Specify only a mobile number in DTO")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Http Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Http Status BAD REQUEST",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@Valid @RequestBody CreateCardDto createCardDto) {
        cardsService.createCard(createCardDto.getMobileNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(STATUS_201, MESSAGE_201));
    }

    @Operation(summary = "Updates a card", description = "Updates a card")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status ok"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Http Status BAD REQUEST",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    @PutMapping("/update")
    public ResponseEntity<CardsDto> updateCardDetails(@Valid @RequestBody CardsDto cardsDto) {
        CardsDto updatedCard = cardsService.updateCard(cardsDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCard);
    }

    @Operation(summary = "Deletes a card by a mobile number", description = "Deletes a card by a mobile number")
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Http Status ok"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "NOT FOUND"
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Http Status ok"
        )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<CardsDto> deleteCardDetails(@RequestParam
                                                      @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
                                                      String mobileNumber) {

        CardsDto cardsDto = cardsService.deleteCard(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
    }
}
