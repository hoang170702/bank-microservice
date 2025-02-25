package com.microservices.accounts.service.accounts;

import com.microservices.accounts.constants.AccountConstants;
import com.microservices.accounts.dto.AccountsDto;
import com.microservices.accounts.dto.CustomerDto;
import com.microservices.accounts.entity.Accounts;
import com.microservices.accounts.entity.Customer;
import com.microservices.accounts.exception.CustomerAlreadyExistsException;
import com.microservices.accounts.exception.ResourceNotFoundException;
import com.microservices.accounts.mapper.AccountMapper;
import com.microservices.accounts.mapper.CustomerMapper;
import com.microservices.accounts.repository.AccountsRepository;
import com.microservices.accounts.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountService implements IAccountService {
    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public AccountService(AccountsRepository accountsRepository, CustomerRepository customerRepository) {
        this.accountsRepository = accountsRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        if (customerRepository.findByPhoneNumber(customer.getPhoneNumber()).isPresent())
            throw new CustomerAlreadyExistsException("Customer already exists with given phone number: " + customer.getPhoneNumber());
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));

    }

    @Override
    public Optional<CustomerDto> fetchAccount(String phoneNumber) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new ResourceNotFoundException("customer", "phone number", phoneNumber));
        Accounts accounts = accountsRepository.findByCustomer_Id(customer.getId()).orElseThrow(() -> new ResourceNotFoundException("account", "customer", customer.getId().toString()));
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return Optional.of(customerDto);
    }

    @Transactional
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdate = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    (() -> new ResourceNotFoundException(
                            "account",
                            "account number",
                            accountsDto.getAccountNumber().toString()))
            );
            accounts.setUpdatedAt(LocalDateTime.now());
            AccountMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomer().getId();
            Customer updatedCustomer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException(
                    "customer",
                    "id",
                    customerId.toString()
            ));
            updatedCustomer.setUpdatedAt(LocalDateTime.now());
            CustomerMapper.mapToCustomer(customerDto, updatedCustomer);
            customerRepository.save(updatedCustomer);
            isUpdate = true;
        }
        return isUpdate;
    }

    @Transactional
    @Override
    public boolean deleteAccount(String phoneNumber) {
        boolean isDelete = false;
        try {
            Customer customer = customerRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new ResourceNotFoundException(
                    "customer",
                    "phone number",
                    phoneNumber
            ));
            accountsRepository.deleteByCustomer_Id(customer.getId());
            customerRepository.deleteById(customer.getId());
            isDelete = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return isDelete;
    }


    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        Long generatedAccountNumber = generateAccountNumber();
        newAccount.setAccountNumber(generatedAccountNumber);
        newAccount.setCustomer(customer);
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);
        return newAccount;
    }

    private Long generateAccountNumber() {
        return 1000000000L + new Random().nextInt(900000000);
    }
}
