package com.audiostock.service;

import com.audiostock.entities.Track;
import com.audiostock.repos.TrackRepo;
import com.audiostock.service.exceptions.TrackNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TrackService {

    private TrackRepo trackRepo;

    public TrackService(TrackRepo trackRepo){
        this.trackRepo = trackRepo;
    }

    public Track getTrackById(Long id) throws TrackNotFoundException {
        return trackRepo.findById(id).orElseThrow(() -> new TrackNotFoundException(String.valueOf(id)));
    }

    public List<Track> getCatalogue(int page, int size, String sorting, String mood, String genre, Long lbpm, Long hbpm){
        return trackRepo.findAllByGenreAndMoodAndBpmIsGreaterThanAndBpmIsLessThan(PageRequest.of(page, size).withSort(Sort.by(sorting)), genre, mood, lbpm, hbpm).stream().collect(Collectors.toList());
    }

    public List<Track> getCatalogue(int page, int size) {
        return getCatalogue(page, size,"name", "", "",0L,999L);
    }

    public List<Track> getAll(){
        return StreamSupport.stream(trackRepo.findAll().spliterator(),false).collect(Collectors.toList());
    }
    public Long addTrack(Track track){
        trackRepo.save(track);
        return track.getId();
    }
}