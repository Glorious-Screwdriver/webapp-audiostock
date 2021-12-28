package com.audiostock.repos;

import com.audiostock.entities.Track;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TrackRepo extends PagingAndSortingRepository<Track, Long> {
    Iterable<Track> findAll();

    Page<Track> findAllByGenreAndMoodAndBpmIsGreaterThanAndBpmIsLessThan(Pageable pageable, String genre, String mood, Long lbpm, Long hbpm);
}