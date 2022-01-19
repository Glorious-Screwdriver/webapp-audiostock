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
    private Byte[] ipv4;

    @Column(length = 39)
    private String ipv6;

    @Column(name = "date_and_time")
    private LocalDateTime dateTime;

    public Logon() {
    }

    public Logon(User user, Byte[] ipv4, LocalDateTime dateTime) {
        this.user = user;
        this.ipv4 = ipv4;
        this.dateTime = dateTime;
    }

    public Logon(User user, String ipv6, LocalDateTime dateTime) {
        this.user = user;
        this.ipv6 = ipv6;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte[] getIpv4() {
        return ipv4;
    }

    public void setIpv4(Byte[] ipv4) {
        this.ipv4 = ipv4;
    }

    public String getIpv6() {
        return ipv6;
    }

    public void setIpv6(String ipv6) {
        this.ipv6 = ipv6;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
