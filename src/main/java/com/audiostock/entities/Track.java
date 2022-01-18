package com.audiostock.entities;

import javax.persistence.*;

@Entity
public class Track {
    @Id
    @Column(length = 7)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private User author;

    private boolean active = false;

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

    public Track(User author,
                 String name,
                 String description,
                 Long price,
                 String genre,
                 String mood,
                 Long bpm
    ) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.genre = genre;
        this.mood = mood;
        this.bpm = bpm;
        this.price = price;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
