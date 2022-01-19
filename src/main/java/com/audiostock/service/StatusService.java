package com.audiostock.service;

import com.audiostock.entities.Status;
import com.audiostock.repos.StatusRepo;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    final private StatusRepo statusRepo;

    public StatusService(StatusRepo statusRepo) {
        this.statusRepo = statusRepo;
    }

    // Getters

    public Status getConsumer() {
        return getStatus(1L);
    }

    public Status getAuthor() {
        return getStatus(2L);
    }

    public Status getModerator() {
        return getStatus(10L);
    }

    private Status getStatus(Long statusId) {
        return statusRepo.findById(statusId).orElseThrow(() -> new IllegalStateException("No such status in db"));
    }

}
