package com.audiostock.service;

import com.audiostock.entities.Track;
import com.audiostock.entities.UploadRequest;
import com.audiostock.entities.User;
import com.audiostock.repos.TrackRepo;
import com.audiostock.repos.UploadRequestRepo;
import com.audiostock.service.exceptions.UploadRequestNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UploadRequestService {

    private final UploadRequestRepo requestRepo;
    private final TrackRepo trackRepo;

    public UploadRequestService(UploadRequestRepo requestRepo, TrackRepo trackRepo) {
        this.requestRepo = requestRepo;
        this.trackRepo = trackRepo;
    }

    public void addRequest(Track track){
        requestRepo.save(new UploadRequest());
    }

    public void approveRequest(Long id) throws UploadRequestNotFoundException {
        final UploadRequest uploadRequest = getRequest(id);

        uploadRequest.setSolution(true);
        uploadRequest.setReviewDate(LocalDateTime.now());

        final Track track = uploadRequest.getTrack();
        track.setActive(true);

        trackRepo.save(track);
        requestRepo.save(uploadRequest);
    }

    public void declineRequest(Long id, String rejectionReason) throws UploadRequestNotFoundException {
        final UploadRequest uploadRequest = getRequest(id);

        uploadRequest.setSolution(false);
        uploadRequest.setRejectionReason(rejectionReason);
        uploadRequest.setReviewDate(LocalDateTime.now());

        trackRepo.delete(uploadRequest.getTrack());
        requestRepo.save(uploadRequest);
    }

    public UploadRequest getRequest(Long id) throws UploadRequestNotFoundException {
        return requestRepo.findById(id)
                .orElseThrow(() -> new UploadRequestNotFoundException(String.valueOf(id)));
    }

    public List<UploadRequest> getRequests(){
        return requestRepo.findAll();
    }

    public List<UploadRequest> getRequestsByModerator(User moderator) {
        return requestRepo.findAllByModeratorAndSolutionNullOrderByCreationDate(moderator);
    }

}
