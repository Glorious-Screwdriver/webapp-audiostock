package com.audiostock.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class PaymentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 30, nullable = false)
    private String cardOwner;

    @Column(nullable = false)
    private String cardNumber;

    @Column(length = 5, nullable = false)
    private LocalDate expireDate;

    @Column(nullable = false)
    private String cvv;

    @Column(nullable = false)
    private String postalCode;

    @Column(length = 100, nullable = false)
    private String address;

    public PaymentInfo() {
    }

    public PaymentInfo(String cardOwner, String cardNumber, LocalDate expireDate, String cvv, String postalCode, String address) {
        this.cardOwner = cardOwner;
        this.cardNumber = cardNumber;
        this.expireDate = expireDate;
        this.cvv = cvv;
        this.postalCode = postalCode;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
