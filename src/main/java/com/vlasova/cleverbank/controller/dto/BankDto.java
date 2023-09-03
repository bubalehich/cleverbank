package com.vlasova.cleverbank.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BankDto {
    @NotBlank
    private String bicNumber;
    @NotBlank
    private String country;
    @NotBlank
    private String name;
}
