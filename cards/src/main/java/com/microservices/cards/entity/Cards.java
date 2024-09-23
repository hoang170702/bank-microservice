package com.microservices.cards.entity;


import com.microservices.cards.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "cards")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Cards extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mobileNumber;

    private String cardNumber;

    private String cardType;

    private int totalLimit;

    private int amountUsed;

    private int availableAmount;
}
