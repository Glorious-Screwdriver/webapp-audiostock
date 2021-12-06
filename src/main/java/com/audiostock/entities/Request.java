package com.audiostock.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
public class Request {
    @Id
    private Long id;

    @OneToOne
    private User author;

    @OneToOne
    private Track track;

    @OneToOne
    private User moderator;

    private boolean solution;
    private String rejectionReason;
    private Date creationDate;
    private Date reviewDate;
}
