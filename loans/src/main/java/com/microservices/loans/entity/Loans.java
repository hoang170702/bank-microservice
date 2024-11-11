package com.microservices.loans.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LOANS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Loans {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loans_seq")
    @SequenceGenerator(name = "loans_seq", sequenceName = "LOANS_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "MOBILE_NUMBER", length = 20)
    private String mobileNumber;

    @Column(name = "LOAN_NUMBER", length = 50)
    private String loanNumber;

    @Column(name = "LOAN_TYPE", length = 50)
    private String loanType;

    @Column(name = "TOTAL_LOAN")
    private int totalLoan;

    @Column(name = "AMOUNT_PAID")
    private int amountPaid;

    @Column(name = "OUTSTANDING_AMOUNT")
    private int outstandingAmount;

    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "UPDATED_AT", insertable = false)
    private LocalDateTime updatedAt;

    @Column(name = "UPDATED_BY")
    private String updatedBy;
}
