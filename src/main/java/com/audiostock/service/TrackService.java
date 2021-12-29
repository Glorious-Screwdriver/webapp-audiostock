package com.audiostock.service;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.repos.TrackRepo;
import com.audiostock.service.exceptions.TrackNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrackService {

    private final TrackRepo trackRepo;

    public TrackService(TrackRepo trackRepo) {
        this.trackRepo = trackRepo;
    }

    // Representation

    public Track getTrackById(Long id) throws TrackNotFoundException {
        return trackRepo.findById(id).orElseThrow(() -> new TrackNotFoundException(String.valueOf(id)));
    }

    public List<Track> getCatalog(int page, int size, String sorting, String mood, String genre, Long lbpm, Long hbpm) {
        return trackRepo.findAllByGenreAndMoodAndBpmIsGreaterThanAndBpmIsLessThan(
                PageRequest.of(page, size).withSort(Sort.by(sorting)), genre, mood, lbpm, hbpm
        ).stream().collect(Collectors.toList());
    }

    public List<Track> getCatalog(int page, int size) {
        return getCatalog(page, size, "name", "", "", 0L, 999L);
    }

    public List<Track> getAll() {
        return trackRepo.findAllByActiveTrue();
    }

    // Properties

    public boolean isInCart(Track track, User user) {
        return user.getCart().contains(track);
    }

    public boolean isInFavorite(Track track, User user) {
        return user.getFavorites().contains(track);
    }

    // Persistence

    public Long addTrack(Track track) {
        trackRepo.save(track);
        return track.getId();
    }

}