package com.springbuds.accounts.service.impl;

import com.springbuds.accounts.constants.AccountsConstants;
import com.springbuds.accounts.dto.AccountsDto;
import com.springbuds.accounts.dto.CustomerDto;
import com.springbuds.accounts.entity.Accounts;
import com.springbuds.accounts.entity.Customer;
import com.springbuds.accounts.exception.CustomerAlreadyExistsException;
import com.springbuds.accounts.exception.ResourceNotFoundException;
import com.springbuds.accounts.mapper.AccountsMapper;
import com.springbuds.accounts.mapper.CustomerMapper;
import com.springbuds.accounts.repository.AccountsRepository;
import com.springbuds.accounts.repository.CustomerRepository;
import com.springbuds.accounts.service.AccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements AccountsService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {

        customerRepository.findByMobileNumber(customerDto.getMobileNumber()).ifPresent((existingCustomer) -> {
            throw new CustomerAlreadyExistsException("Customer already exists, " + existingCustomer.getMobileNumber());
        });

        Optional.of(customerDto)
                .map(CustomerMapper::mapToCustomer)
                .map(customerRepository::save)
                .map(this::createNewAccount)
                .ifPresent(accountsRepository::save);
    }

    private Accounts createNewAccount(Customer customer) {
        return Accounts.builder()
                .customerId(customer.getCustomerId())
                .accountNumber(1000000000L + new Random().nextInt(900000000))
                .accountType(AccountsConstants.SAVINGS)
                .branchAddress(AccountsConstants.ADDRESS)
                .build();
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer);
        customerDto.setAccounts(AccountsMapper.mapToAccountsDto(accounts));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        AccountsDto accountsDto = customerDto.getAccounts();

        if (accountsDto == null) {
            return false;
        }
        Long accountNumber = accountsDto.getAccountNumber();

        Accounts accounts = accountsRepository.findById(accountNumber)
                .map(AccountsMapper.curryMapToAccounts(accountsDto))
                .map(accountsRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "AccountNumber", accountNumber.toString()));

        Long customerId = accounts.getCustomerId();
        customerRepository.findById(customerId)
                .map(CustomerMapper.curryMapToCustomer(customerDto))
                .map(customerRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerId.toString()));

        return true;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}
