package com.audiostock.entities;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 6)
    private Long id;

    @Column(unique = true, nullable = false, length = 16)
    private String login; // also known as username

    @Column(nullable = false)
    private String password;

    @ManyToOne(optional = false)
    private Status status;

    @Column(length = 30)
    private String firstname;

    @Column(length = 30)
    private String middlename;

    @Column(length = 30)
    private String lastname;

    @Column(length = 7)
    private Long balance = 0L;

    @OneToOne
    private PaymentInfo paymentInfo;

    @Column(length = 500)
    private String biography;

    @OneToMany
    private Set<Track> releases;

    @ManyToMany
    private Set<Track> cart;

    @ManyToMany
    private Set<Track> favorites;

    @ManyToMany
    private Set<Track> purchased;

    private boolean banned = false;

    public User() {
    }

    public User(String login, String password, Status status) {
        this.login = login;
        this.password = password;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Set<Track> getReleases() {
        return releases;
    }

    public void setReleases(Set<Track> releases) {
        this.releases = releases;
    }

    public Set<Track> getCart() {
        return cart;
    }

    public void setCart(Set<Track> cart) {
        this.cart = cart;
    }

    public Set<Track> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<Track> favorites) {
        this.favorites = favorites;
    }

    public Set<Track> getPurchased() {
        return purchased;
    }

    public void setPurchased(Set<Track> purchased) {
        this.purchased = purchased;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == User.class) return false;
        User user = (User) obj;
        return id.equals(user.getId());
    }
}
