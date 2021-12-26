package com.audiostock.service;

import com.audiostock.entities.Track;
import com.audiostock.repos.TrackRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrackService {
    private TrackRepo trackRepo;
    public TrackService(TrackRepo trackRepo){
        this.trackRepo = trackRepo;
    }
    public Long addTrack(Track track){
        trackRepo.save(track);
        return track.getId();
    }

    List<Track> getCatalogue(int page, int size, String sorting, String mood, String genre, Long lbpm, Long hbpm){
        return trackRepo.findAllByGenreIsNearAndMoodIsNearAndBpmIsGreaterThanAndBpmIsLessThan(PageRequest.of(page, size).withSort(Sort.by(sorting)), genre, mood, lbpm, hbpm).stream().collect(Collectors.toList());
    }

    List<Track> getCatalogue(int page, int size) {
        return getCatalogue(page, size,"name", "", "",0L,999L);
    }
}