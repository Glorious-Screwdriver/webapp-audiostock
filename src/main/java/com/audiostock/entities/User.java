package com.audiostock.entities;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String login;

    private String password;

    @OneToOne
    private Status status;

    private String firstname;

    private String middlename;

    private String lastname;

    private Long balance;

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

    @OneToMany
    private Set<Logon> logons;

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

    public Set<Logon> getLogons() {
        return logons;
    }

    public void setLogons(Set<Logon> logons) {
        this.logons = logons;
    }
}
