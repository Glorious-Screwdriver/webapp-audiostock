package com.audiostock.service;

import com.audiostock.entities.Logon;
import com.audiostock.entities.UserEntity;
import com.audiostock.repos.LogonRepo;
import com.audiostock.repos.UserRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogonService {

    UserRepo userRepo;
    LogonRepo logonRepo;

    public LogonService(UserRepo userRepo, LogonRepo logonRepo) {
        this.userRepo = userRepo;
        this.logonRepo = logonRepo;
    }

    void logon(UserEntity user, Byte[] ip) {
        logonRepo.save(new Logon(user, ip, LocalDateTime.now()));
    }

    List<Logon> getLogonsByUser(UserEntity user) {
        return logonRepo.findAllByUser(user, Sort.by("date_and_time"));
    }

}
