package com.microservices.accounts.service.customer;

import com.microservices.accounts.dto.AccountsDto;
import com.microservices.accounts.dto.CardsDto;
import com.microservices.accounts.dto.CustomerDetailsDto;
import com.microservices.accounts.dto.LoansDto;
import com.microservices.accounts.entity.Accounts;
import com.microservices.accounts.entity.Customer;
import com.microservices.accounts.exception.ResourceNotFoundException;
import com.microservices.accounts.repository.AccountsRepository;
import com.microservices.accounts.repository.CustomerRepository;
import com.microservices.accounts.service.client.CardFeignClient;
import com.microservices.accounts.service.client.LoanFeignClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Collections;

@Service
public class CustomerService implements ICustomerService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final CardFeignClient cardFeignClient;
    private final LoanFeignClient loanFeignClient;


    @Autowired
    public CustomerService(AccountsRepository accountsRepository, CustomerRepository customerRepository,
                           CardFeignClient cardFeignClient, LoanFeignClient loanFeignClient
    ) {
        this.accountsRepository = accountsRepository;
        this.customerRepository = customerRepository;
        this.cardFeignClient = cardFeignClient;
        this.loanFeignClient = loanFeignClient;
    }

    @Override
    public CustomerDetailsDto customerDetailsDto(String correlationId, String phoneNumber) {
        ModelMapper modelMapper = new ModelMapper();
        try {
            Customer customer = customerRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new ResourceNotFoundException("customer", "phone number", phoneNumber));
            Accounts accounts = accountsRepository.findByCustomer_Id(customer.getId()).orElseThrow(() -> new ResourceNotFoundException("account", "customer", customer.getId().toString()));

            CustomerDetailsDto customerDetailsDto = modelMapper.map(customer, CustomerDetailsDto.class);
            customerDetailsDto.setAccountsDto(modelMapper.map(accounts, AccountsDto.class));

            ResponseEntity<CardsDto> cardsDtoResponseEntity = cardFeignClient.fetchCard(correlationId,phoneNumber);
            if (cardsDtoResponseEntity != null && cardsDtoResponseEntity.getBody() != null) {
                customerDetailsDto.setCardsDto(modelMapper.map(cardsDtoResponseEntity.getBody(), CardsDto.class));
            }


            ResponseEntity<LoansDto> loansDtoResponseEntity = loanFeignClient.fetchLoan(correlationId,phoneNumber);
            if (null != loansDtoResponseEntity){
                customerDetailsDto.setLoansDto(modelMapper.map(loansDtoResponseEntity.getBody(), LoansDto.class));
            }

            return customerDetailsDto;
        } catch (Exception e) {
            // Handle exceptions here
            e.printStackTrace();
        }
        return new CustomerDetailsDto();
    }
}
