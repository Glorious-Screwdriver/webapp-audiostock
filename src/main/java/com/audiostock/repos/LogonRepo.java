package com.audiostock.repos;

import com.audiostock.entities.Logon;
import com.audiostock.entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface LogonRepo extends PagingAndSortingRepository<Logon, Long> {
    List<Logon> findAllByUser(User user);
}