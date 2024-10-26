package com.springbuds.accounts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Entity
@Getter @Setter
@ToString @NoArgsConstructor
public class Accounts extends BaseEntity {

    @Id
    private Long accountNumber;

    private Long customerId;

    private String accountType;

    private String branchAddress;

    @Builder
    public Accounts(LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy, Long accountNumber, Long customerId, String accountType, String branchAddress) {
        super(createdAt, createdBy, updatedAt, updatedBy);
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.accountType = accountType;
        this.branchAddress = branchAddress;
    }
}
