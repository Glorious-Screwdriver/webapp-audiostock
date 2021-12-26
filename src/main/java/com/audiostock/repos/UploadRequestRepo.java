package com.audiostock.repos;

import com.audiostock.entities.UploadRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UploadRequestRepo extends PagingAndSortingRepository<UploadRequest, Long> {
    Optional<UploadRequest> findById(Long id);
    List<UploadRequest> findAll();
}