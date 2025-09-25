package com.microservices.accounts.service.accounts;

import com.microservices.accounts.constants.AccountConstants;
import com.microservices.accounts.dto.AccountMsgDto;
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
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;


@Service
public class AccountService implements IAccountService {
    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final StreamBridge streamBridge;
    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    public AccountService(AccountsRepository accountsRepository, CustomerRepository customerRepository, StreamBridge streamBridge) {
        this.accountsRepository = accountsRepository;
        this.customerRepository = customerRepository;
        this.streamBridge = streamBridge;
    }

    @Transactional
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        if (customerRepository.findByPhoneNumber(customer.getPhoneNumber()).isPresent())
            throw new CustomerAlreadyExistsException("Customer already exists with given phone number: " + customer.getPhoneNumber());
        Customer savedCustomer = customerRepository.save(customer);
        Accounts savedAccount = accountsRepository.save(createNewAccount(savedCustomer));
        sendCommunication(savedAccount, savedCustomer);
    }

    private void sendCommunication(Accounts account, Customer customer) {
        var accountsMsgDto = new AccountMsgDto(account.getAccountNumber(), customer.getName(),
                customer.getEmail(), customer.getPhoneNumber());
        log.info("Sending Communication request for the details: {}", accountsMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", accountsMsgDto);
        log.info("Is the Communication request successfully triggered ? : {}", result);
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

    @Override
    public boolean updateCommunicationSw(Long accountNumber) {
        boolean isUpdated = false;
        if(accountNumber !=null ){
            Accounts accounts = accountsRepository.findById(accountNumber).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountNumber.toString())
            );
            accounts.setCommunicationSw(true);
            accountsRepository.save(accounts);
            isUpdated = true;
        }
        return  isUpdated;
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
