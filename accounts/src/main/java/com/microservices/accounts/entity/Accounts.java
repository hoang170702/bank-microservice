package com.microservices.accounts.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;

    @Column(name = "ACCOUNT_TYPE", nullable = false)
    private String accountType;

    @Column(name = "BRANCH_ADDRESS")
    private String branchAddress;

    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "UPDATED_AT", insertable = false)
    private LocalDateTime updatedAt;

    @Column(name = "UPDATED_BY")
    private String updatedBy;
}
