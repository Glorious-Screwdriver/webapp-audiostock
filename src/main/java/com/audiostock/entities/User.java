package com.audiostock.entities;

import net.bytebuddy.implementation.bind.annotation.Default;

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
    private String login;

    @Column(nullable = false, length = 30)
    private String password;

    @OneToOne
    private Status status;

    @Column(length = 30)
    private String firstname;

    @Column(length = 30)
    private String middlename;

    @Column(length = 30)
    private String lastname;

    @Column(length = 7)
    private Long balance = 0L;

    @Column(length = 100)
    private String biography;

    @Lob
    private Blob avatar;

    @OneToMany
    private Set<Track> releases;

    @OneToMany
    private Set<Track> cart;

    @OneToMany
    private Set<Track> favorites;

    @OneToMany
    private Set<Track> purchased;

    public User() {
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

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Blob getAvatar() {
        return avatar;
    }

    public void setAvatar(Blob avatar) {
        this.avatar = avatar;
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
}
