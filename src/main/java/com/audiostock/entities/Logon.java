package com.audiostock.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Logon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @Column(length = 4)
    private Byte[] ip;

    @Column(name = "date_and_time")
    private LocalDateTime dateTime;

    public Logon() {
    }

    public Logon(User user, Byte[] ip, LocalDateTime dateTime) {
        this.user = user;
        this.ip = ip;
        this.dateTime = dateTime;
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
