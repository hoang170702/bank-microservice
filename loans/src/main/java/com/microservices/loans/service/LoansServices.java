package com.microservices.loans.service;

import com.microservices.loans.constants.LoansConstants;
import com.microservices.loans.dto.LoansDto;
import com.microservices.loans.entity.Loans;
import com.microservices.loans.exception.LoanAlreadyExistsException;
import com.microservices.loans.exception.ResourceNotFoundException;
import com.microservices.loans.mapper.LoansMapper;
import com.microservices.loans.repository.LoansRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class LoansServices implements ILoansService {

    private final LoansRepository loansRepository;

    @Autowired
    public LoansServices(LoansRepository loansRepository) {
        this.loansRepository = loansRepository;
    }

    @Transactional
    @Override
    public void createLoan(String mobileNumber) {
        // Implement logic to create a loan
        Optional<Loans> loan = loansRepository.findByMobileNumber(mobileNumber);
        if (loan.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber " + mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    private Loans createNewLoan(String mobileNumber) {
        Loans loan = new Loans();
        loan.setLoanNumber(Long.toString(randomLoansNumbers()));
        loan.setLoanType(LoansConstants.HOME_LOAN);
        loan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        loan.setMobileNumber(mobileNumber);
        loan.setAmountPaid(0);
        loan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return loan;
    }

    private Long randomLoansNumbers() {
        return 100000000000L + new Random().nextInt(900000000);
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Optional<Loans> loan = loansRepository.findByMobileNumber(mobileNumber);
        return loan.map(loans -> LoansMapper.mapToLoansDto(loans, new LoansDto()))
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "phone number", mobileNumber));
    }

    @Transactional
    @Override
    public boolean updateLoan(LoansDto loansDto) {
        boolean isUpdate = false;
        Loans loan = loansRepository.findByMobileNumber(loansDto.getMobileNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "phone number", loansDto.getMobileNumber()));
        if (loan != null) {
            Loans updateLoan = LoansMapper.mapToLoans(loansDto, loan);
            updateLoan.setUpdatedAt(LocalDateTime.now());
            loansRepository.save(updateLoan);
            isUpdate = true;
        }
        return isUpdate;
    }

    @Transactional
    @Override
    public boolean deleteLoan(String mobileNumber) {
        boolean isDelete = false;
        Loans loan = loansRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "phone number", mobileNumber));
        if (loan != null) {
            loansRepository.deleteByMobileNumber(mobileNumber);
            isDelete = true;
        }
        return isDelete;
    }
}
