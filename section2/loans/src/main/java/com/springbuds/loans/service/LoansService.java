package com.springbuds.loans.service;

import com.springbuds.loans.dto.LoansDto;

public interface LoansService {
    void createLoan(String mobileNumber);

    LoansDto fetchLoan(String mobileNumber);

    void updateLoan(LoansDto loansDto);

    void deleteLoan(String mobileNumber);
}
