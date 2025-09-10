package com.bank.message.function;

import com.bank.message.dto.AccountMsgDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;
import java.util.logging.Logger;

@Configuration
public class MessageFunctions {
    private static Logger logger = Logger.getLogger(MessageFunctions.class.getName());

    @Bean
    public Function<AccountMsgDto, AccountMsgDto> email() {
        return accountMsgDto -> {
            logger.info("Send email with the details"+accountMsgDto.toString());
            return accountMsgDto;
        };
    }

    @Bean
    public Function<AccountMsgDto, Long> sms() {
        return accountMsgDto -> {
            logger.info("Send sms with the details"+accountMsgDto.toString());
            return accountMsgDto.accountNumber();
        };
    }
}
