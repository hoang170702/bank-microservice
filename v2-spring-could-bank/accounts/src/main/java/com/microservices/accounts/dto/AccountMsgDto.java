package com.microservices.accounts.dto;

public record AccountMsgDto(Long accountNumber, String name, String email, String mobileNumber) {
}
