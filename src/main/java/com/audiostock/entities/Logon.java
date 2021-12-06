package com.audiostock.entities;

import javax.persistence.*;

@Entity
public class Logon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 4)
    private Byte[] ip;

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

    public Byte[] getIp() {
        return ip;
    }

    public void setIp(Byte[] ip) {
        this.ip = ip;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
