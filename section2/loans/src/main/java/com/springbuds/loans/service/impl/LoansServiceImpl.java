package com.springbuds.loans.service.impl;

import com.springbuds.loans.constants.LoansConstants;
import com.springbuds.loans.dto.LoansDto;
import com.springbuds.loans.entity.Loans;
import com.springbuds.loans.exception.LoanAlreadyExistsException;
import com.springbuds.loans.exception.ResourceNotFoundException;
import com.springbuds.loans.mapper.LoansMapper;
import com.springbuds.loans.repository.LoansRepository;
import com.springbuds.loans.service.LoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements LoansService {

    private LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        loansRepository.findByMobileNumber(mobileNumber).ifPresentOrElse(
                loans -> {
                    throw new LoanAlreadyExistsException("Loan already exists with number: " + loans.getMobileNumber());
                },
                () -> loansRepository.save(createNewLoan(mobileNumber)));
    }

    private Loans createNewLoan(String mobileNumber) {
        return Loans.builder()
                .loanNumber(Long.toString(100000000000L + new Random().nextInt(900000000)))
                .mobileNumber(mobileNumber)
                .loanType(LoansConstants.HOME_LOAN)
                .totalLoan(LoansConstants.NEW_LOAN_LIMIT)
                .amountPaid(0)
                .outstandingAmount(LoansConstants.NEW_LOAN_LIMIT)
                .build();
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        return loansRepository.findByMobileNumber(mobileNumber)
                .map(loans -> LoansMapper.mapToLoansDto(loans, LoansDto.builder().build()))
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
    }

    @Override
    public void updateLoan(LoansDto loansDto) {
        loansRepository.findByLoanNumber(loansDto.getLoanNumber())
                .map(loans -> LoansMapper.mapToLoans(loansDto, loans))
                .ifPresentOrElse(
                        loansRepository::save,
                        () -> {
                            throw new ResourceNotFoundException("Loan", "mobileNumber", loansDto.getMobileNumber());
                        });
    }

    @Override
    public void deleteLoan(String mobileNumber) {
        loansRepository.findByMobileNumber(mobileNumber).ifPresentOrElse(
                loans -> loansRepository.deleteById(loans.getLoanId()),
                () -> {
                    throw new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber);
                }
        );
    }
}
