package com.vlasova.cleverbank.servlet.dto;

public record ErrorResponse(Integer status, String message, String details) {
}
