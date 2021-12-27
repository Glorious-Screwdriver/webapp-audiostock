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
    private UserEntity author;

    @OneToOne
    private Track track;

    @OneToOne
    private UserEntity moderator;

    private boolean solution;

    @Column(length = 50)
    private String rejectionReason;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    private LocalDateTime reviewDate;

    public UploadRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public UserEntity getModerator() {
        return moderator;
    }

    public void setModerator(UserEntity moderator) {
        this.moderator = moderator;
    }

    public boolean isSolution() {
        return solution;
    }

    public void setSolution(boolean solution) {
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