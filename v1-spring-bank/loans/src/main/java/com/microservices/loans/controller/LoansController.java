package com.microservices.loans.controller;

import com.microservices.loans.constants.LoansConstants;
import com.microservices.loans.dto.LoansDto;
import com.microservices.loans.dto.payload.ResponseDto;
import com.microservices.loans.service.ILoansService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
public class LoansController {
    private final ILoansService iLoansService;

    public LoansController(ILoansService iLoansService) {
        this.iLoansService = iLoansService;
    }

    @PostMapping("/create-loan")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam String mobileNumber) {
        iLoansService.createLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
    }

    @GetMapping("/fetch-loan")
    public ResponseEntity<LoansDto> fetchLoan(@RequestParam String mobileNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(iLoansService.fetchLoan(mobileNumber));
    }

    @PostMapping("/update-loan")
    public ResponseEntity<ResponseDto> updateLoan(@RequestBody LoansDto loansDto) {
        boolean isUpdate = iLoansService.updateLoan(loansDto);
        return ResponseEntity.status(isUpdate ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(new ResponseDto(
                        isUpdate ? LoansConstants.STATUS_200 : LoansConstants.STATUS_417,
                        isUpdate ? LoansConstants.MESSAGE_200 : LoansConstants.MESSAGE_417_UPDATE));
    }

    @PostMapping("/delete-loan")
    public ResponseEntity<ResponseDto> deleteLoan(@RequestParam String mobileNumber) {
        boolean isDelete = iLoansService.deleteLoan(mobileNumber);
        return ResponseEntity.status(isDelete ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(new ResponseDto(
                        isDelete ? LoansConstants.STATUS_200 : LoansConstants.STATUS_417,
                        isDelete ? LoansConstants.MESSAGE_200 : LoansConstants.MESSAGE_417_DELETE));
    }
}
