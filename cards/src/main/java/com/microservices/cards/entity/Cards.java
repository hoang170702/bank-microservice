package com.microservices.cards.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CARDS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cards {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cards_seq")
    @SequenceGenerator(name = "cards_seq", sequenceName = "CARDS_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "MOBILE_NUMBER", length = 20)
    private String mobileNumber;

    @Column(name = "CARD_NUMBER", length = 50)
    private String cardNumber;

    @Column(name = "CARD_TYPE", length = 50)
    private String cardType;

    @Column(name = "TOTAL_LIMIT")
    private int totalLimit;

    @Column(name = "AMOUNT_USED")
    private int amountUsed;

    @Column(name = "AVAILABLE_AMOUNT")
    private int availableAmount;

    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "UPDATED_AT", insertable = false)
    private LocalDateTime updatedAt;

    @Column(name = "UPDATED_BY")
    private String updatedBy;
}
