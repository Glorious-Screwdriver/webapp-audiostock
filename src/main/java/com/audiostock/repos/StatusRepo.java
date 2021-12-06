package com.audiostock.repos;

import com.audiostock.entities.Status;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StatusRepo extends PagingAndSortingRepository<Status, Long> {
}