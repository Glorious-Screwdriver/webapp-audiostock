package com.audiostock.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PaymentInfo {
    @Id
    private Long id;

    @Column(length = 30, nullable = false)
    private String cardOwner;

    @Column(nullable = false)
    private int cardNumber;

    @Column(length = 5, nullable = false)
    private String expireDate; // формат: 04/22

    @Column(nullable = false)
    private int cvv;

    @Column(nullable = false)
    private int postalCode;

    @Column(length = 100, nullable = false)
    private String address;

    public PaymentInfo() {
    }

    public PaymentInfo(String cardOwner, int cardNumber, String expireDate, int cvv, int postalCode, String address) {
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

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
