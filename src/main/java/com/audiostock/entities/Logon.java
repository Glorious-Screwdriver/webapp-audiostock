package com.audiostock.entities;

import javax.persistence.*;

@Entity
public class Logon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long ip;

    @ManyToOne
    private User user;

    public Logon() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIp() {
        return ip;
    }

    public void setIp(Long ip) {
        this.ip = ip;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
