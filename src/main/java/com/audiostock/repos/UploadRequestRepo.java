package com.audiostock.repos;

import com.audiostock.entities.UploadRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UploadRequestRepo extends PagingAndSortingRepository<UploadRequest, Long> {
    @Override
    Optional<UploadRequest> findById(Long aLong);
}