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
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UploadRequestService {

private final UploadRequestRepo requestRepo;
    private final TrackRepo trackRepo;
    private final UserService userService;

    public UploadRequestService(UploadRequestRepo requestRepo, TrackRepo trackRepo, UserService userService) {
        this.requestRepo = requestRepo;
        this.trackRepo = trackRepo;
        this.userService = userService;
    }

    // Getters

    public UploadRequest getRequest(Long id) throws UploadRequestNotFoundException {
        return requestRepo.findById(id)
                .orElseThrow(() -> new UploadRequestNotFoundException(String.valueOf(id)));
    }

    public List<UploadRequest> getRequestsByModerator(User moderator) {
        return requestRepo.findAllByModeratorAndSolutionNullOrderByCreationDate(moderator);
    }

    // Representation

    /**
     * Возвращает все треки автора, у которых еще нет решения
     * @param author Автор треков
     * @return Список треков без решения
     */
    public List<Track> getRequestedTracksByAuthor(User author) {
        return getTracksFromRequests(requestRepo.findAllByAuthorAndSolutionNull(author));
    }

    /**
     * Возвращает все отклоненные треки автора
     * @param author Автор треков
     * @return Список отклоненных треков
     */
    public List<Track> getDeclinedTracksByAuthor(User author) {
        return getTracksFromRequests(requestRepo.findAllByAuthorAndSolutionFalse(author));
    }

    private List<Track> getTracksFromRequests(List<UploadRequest> requests) {
        return requests.stream()
                .map(UploadRequest::getTrack)
                .collect(Collectors.toList());
    }

    // Processing

    public void createRequest(User author, Track track){
        UploadRequest request = new UploadRequest(author, track);

        request.setCreationDate(LocalDateTime.now());

        // Назначение случайному модератору
        List<User> moderators = userService.getAllModerators();
        if (moderators.isEmpty()) throw new IllegalStateException("No moderators exist");

        Random r = new Random();
        final User moderator = moderators.get(r.nextInt(moderators.size()));
        request.setModerator(moderator);

        requestRepo.save(request);
    }

    public void approveRequest(Long id) throws UploadRequestNotFoundException {
        final UploadRequest uploadRequest = getRequest(id);

        uploadRequest.setSolution(true);
        uploadRequest.setReviewDate(LocalDateTime.now());

        final Track track = uploadRequest.getTrack();
        track.setActive(true);

        trackRepo.save(track);
        requestRepo.save(uploadRequest);
        userService.addRelease(uploadRequest.getAuthor(), track);
    }

    public void declineRequest(Long id, String rejectionReason) throws UploadRequestNotFoundException {
        final UploadRequest uploadRequest = getRequest(id);

        uploadRequest.setSolution(false);
        uploadRequest.setRejectionReason(rejectionReason);
        uploadRequest.setReviewDate(LocalDateTime.now());

        requestRepo.save(uploadRequest);
    }

}
