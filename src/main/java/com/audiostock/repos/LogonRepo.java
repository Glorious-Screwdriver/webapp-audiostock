package com.audiostock.repos;

import com.audiostock.entities.Logon;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LogonRepo extends PagingAndSortingRepository<Logon, Long> {
}