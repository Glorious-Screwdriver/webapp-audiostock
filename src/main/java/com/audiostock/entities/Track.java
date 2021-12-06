package com.audiostock.entities;

import javax.persistence.*;
import java.sql.Blob;

@Entity
public class Track {
    @Id
    @Column(length = 7)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 30)
    private String name;

    @Lob
    private Blob cover;

    @Lob
    private Blob file;

    @Column(length = 300)
    private String description;

    @Column(length = 10)
    private String genre;

    @Column(length = 10)
    private String mood;

    @Column(length = 3)
    private Long bpm;

    @Column(nullable = false, length = 4)
    private Long price;

    public Track() {
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

    public Blob getCover() {
        return cover;
    }

    public void setCover(Blob cover) {
        this.cover = cover;
    }

    public Blob getFile() {
        return file;
    }

    public void setFile(Blob file) {
        this.file = file;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public Long getBpm() {
        return bpm;
    }

    public void setBpm(Long bpm) {
        this.bpm = bpm;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}