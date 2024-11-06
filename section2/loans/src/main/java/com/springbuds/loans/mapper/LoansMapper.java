package com.springbuds.loans.mapper;

import com.springbuds.loans.dto.LoansDto;
import com.springbuds.loans.entity.Loans;

public class LoansMapper {
    private LoansMapper() {
    }

    public static LoansDto mapToLoansDto(Loans loans, LoansDto loansDto) {
        loansDto.setLoanId(loans.getLoanId());
        loansDto.setLoanNumber(loans.getLoanNumber());
        loansDto.setLoanType(loans.getLoanType());
        loansDto.setAmountPaid(loans.getAmountPaid());
        loansDto.setTotalLoan(loans.getTotalLoan());
        loansDto.setMobileNumber(loans.getMobileNumber());
        loansDto.setOutstandingAmount(loans.getOutstandingAmount());
        return loansDto;

    }

    public static Loans mapToLoans(LoansDto loansDto, Loans loans) {
        loans.setLoanNumber(loansDto.getLoanNumber());
        loans.setLoanType(loansDto.getLoanType());
        loans.setAmountPaid(loansDto.getAmountPaid());
        loans.setTotalLoan(loansDto.getTotalLoan());
        loans.setMobileNumber(loansDto.getMobileNumber());
        loans.setOutstandingAmount(loansDto.getOutstandingAmount());
        return loans;
    }
}
