package com.microservices.accounts.controller;

import com.microservices.accounts.constants.AccountConstants;
import com.microservices.accounts.dto.CustomerDto;
import com.microservices.accounts.dto.payload.ResponseDto;
import com.microservices.accounts.service.accounts.IAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountsController {

    private final IAccountService iAccountService;

    @Autowired
    public AccountsController(IAccountService iAccountService) {
        this.iAccountService = iAccountService;
    }

    @PostMapping("/create-account")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        iAccountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
    }

    @GetMapping("/fetch-account-details")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam String phoneNumber) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iAccountService.fetchAccount(phoneNumber).get());
    }

    @PutMapping("/update-account")
    public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdate = iAccountService.updateAccount(customerDto);
        return ResponseEntity
                .status(isUpdate ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(
                        new ResponseDto(
                                isUpdate ? AccountConstants.STATUS_200 : AccountConstants.STATUS_500,
                                isUpdate ? AccountConstants.MESSAGE_200 : AccountConstants.MESSAGE_500)
                );
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam String phoneNumber) {
        boolean isDelete = iAccountService.deleteAccount(phoneNumber);
        return ResponseEntity
                .status(isDelete ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(
                        new ResponseDto(
                                isDelete ? AccountConstants.STATUS_200 : AccountConstants.STATUS_500,
                                isDelete ? AccountConstants.MESSAGE_200 : AccountConstants.MESSAGE_500)
                );
    }
}
