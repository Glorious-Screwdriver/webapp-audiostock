package com.audiostock.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Status {
    @Id
    private Long id;

    private String name;

    public Status() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
