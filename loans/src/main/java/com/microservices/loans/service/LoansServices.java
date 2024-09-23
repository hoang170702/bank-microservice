package com.microservices.loans.service;

import com.microservices.loans.dto.LoansDto;

public class LoansServices implements ILoansService {
    @Override
    public void createLoan(String mobileNumber) {

    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        return null;
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        return false;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        return false;
    }
}
