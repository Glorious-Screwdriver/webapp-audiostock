package com.audiostock.service;

import com.audiostock.entities.Logon;
import com.audiostock.entities.User;
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

    public void logon(User user, Byte[] ipv4) {
        logonRepo.save(new Logon(user, ipv4, LocalDateTime.now()));
    }

    public void logon(User user, String ipv6) {
        logonRepo.save(new Logon(user, ipv6, LocalDateTime.now()));
    }

    public List<Logon> getLogonsByUser(User user) {
        return logonRepo.findAllByUser(user, Sort.by("date_and_time"));
    }

}
