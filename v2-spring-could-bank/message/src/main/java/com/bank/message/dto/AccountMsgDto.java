package com.bank.message.dto;

import java.math.BigDecimal;

public record AccountMsgDto(Long accountNumber, String name, String email, String mobileNumber) {
}
