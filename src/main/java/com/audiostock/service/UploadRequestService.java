package com.audiostock.service;

import com.audiostock.entities.Track;
import com.audiostock.entities.UploadRequest;
import com.audiostock.repos.TrackRepo;
import com.audiostock.repos.UploadRequestRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UploadRequestService {
    private final UploadRequestRepo requestRepo;
    public UploadRequestService(UploadRequestRepo requestRepo){
        this.requestRepo = requestRepo;
    }
    public void addRequest(Track track){
        requestRepo.save(new UploadRequest());
    }
    public void closeRequest(Long id){
        requestRepo.deleteById(id);
    }
    public UploadRequest getRequest(Long id){
        return requestRepo.findById(id).orElse(null);
    }
    public List<UploadRequest> getRequests(){
        return requestRepo.findAll();
    }

}
