package com.audiostock.repos;

import com.audiostock.entities.Track;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TrackRepo extends PagingAndSortingRepository<Track, Long> {
}