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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomerService implements ICustomerService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final CardFeignClient cardFeignClient;
    private final LoanFeignClient loanFeignClient;


    public CustomerService(AccountsRepository accountsRepository, CustomerRepository customerRepository,
                           CardFeignClient cardFeignClient, LoanFeignClient loanFeignClient
    ) {
        this.accountsRepository = accountsRepository;
        this.customerRepository = customerRepository;
        this.cardFeignClient = cardFeignClient;
        this.loanFeignClient = loanFeignClient;
    }

    @Override
    public CustomerDetailsDto customerDetailsDto(String phoneNumber) {
        ModelMapper modelMapper = new ModelMapper();
        try {
            Customer customer = customerRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new ResourceNotFoundException("customer", "phone number", phoneNumber));
            Accounts accounts = accountsRepository.findByCustomer_Id(customer.getId()).orElseThrow(() -> new ResourceNotFoundException("account", "customer", customer.getId().toString()));
            ResponseEntity<CardsDto> cardsDtoResponseEntity = cardFeignClient.fetchCard(phoneNumber);
            ResponseEntity<LoansDto> loansDtoResponseEntity = loanFeignClient.fetchLoan(phoneNumber);

            CustomerDetailsDto customerDetailsDto = modelMapper.map(customer, CustomerDetailsDto.class);
            customerDetailsDto.setAccountsDto(modelMapper.map(accounts, AccountsDto.class));
            customerDetailsDto.setLoansDto(modelMapper.map(loansDtoResponseEntity.getBody(), LoansDto.class));
            customerDetailsDto.setCardsDto(modelMapper.map(cardsDtoResponseEntity.getBody(), CardsDto.class));

            return customerDetailsDto;
        } catch (Exception e) {
            // Handle exceptions here
            e.printStackTrace();
        }
        return (CustomerDetailsDto) Collections.EMPTY_LIST;
    }
}
