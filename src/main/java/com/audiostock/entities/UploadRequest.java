package com.audiostock.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UploadRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 9)
    private Long id;

    @ManyToOne
    private User author;

    @OneToOne
    private Track track;

    @OneToOne
    private User moderator;

    private Boolean solution;

    @Column(length = 50)
    private String rejectionReason;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    private LocalDateTime reviewDate;

    public UploadRequest() {
    }

    public UploadRequest(User author, Track track) {
        this.author = author;
        this.track = track;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public User getModerator() {
        return moderator;
    }

    public void setModerator(User moderator) {
        this.moderator = moderator;
    }

    public Boolean getSolution() {
        return solution;
    }

    public void setSolution(Boolean solution) {
        this.solution = solution;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }
}