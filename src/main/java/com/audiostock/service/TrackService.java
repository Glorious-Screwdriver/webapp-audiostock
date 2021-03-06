package com.audiostock.service;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.repos.TrackRepo;
import com.audiostock.service.exceptions.TrackNotFoundException;
import com.audiostock.service.reports.TrackUploadReport;
import com.audiostock.service.util.FileUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrackService {

    final private UserService userService;

    final private TrackRepo trackRepo;

    public TrackService(UserService userService, TrackRepo trackRepo) {
        this.userService = userService;
        this.trackRepo = trackRepo;
    }

    // Getters

    public Track getTrackById(Long id) throws TrackNotFoundException {
        return trackRepo.findById(id).orElseThrow(() -> new TrackNotFoundException(String.valueOf(id)));
    }

    // Representation

    public List<Track> getAllActive() {
        return trackRepo.findAllByActiveTrue();
    }

    public List<Track> search(String name, String genre, String mood) {
        return getAllActive().stream()
                .filter(track -> name.equals("") || track.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(track -> genre.equals("") || track.getGenre().toLowerCase().contains(genre.toLowerCase()))
                .filter(track -> mood.equals("") || track.getMood().toLowerCase().contains(mood.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Presence in user lists

    public boolean isInCart(Track track, User user) {
        return user.getCart().contains(track);
    }

    public boolean isInFavorite(Track track, User user) {
        return user.getFavorites().contains(track);
    }

    public boolean isPurchased(Track track, User user) {
        return user.getPurchased().contains(track);
    }

    // Persistence

    public TrackUploadReport uploadTrack(User author, String name, String description,
                                         Long price, String genre, String mood, Long bpm,
                                         MultipartFile original, MultipartFile preview, MultipartFile cover) {
        Track track = new Track(author, name, description, price, genre, mood, bpm);
        track = trackRepo.save(track);

        // ???????????????? ?????????????????????????? ??????????
        try {
            FileUploadUtil.saveFile("tracks", track.getId() + ".mp3", original);
        } catch (IOException e) {
            trackRepo.delete(track);
            return new TrackUploadReport(false, "???????????? ?????? ???????????????? ?????????????????????????? ??????????!");
        }

        // ???????????????? ???????????? ??????????
        try {
            FileUploadUtil.saveFile("previews", track.getId() + ".mp3", preview);
        } catch (IOException e) {
            trackRepo.delete(track);
            return new TrackUploadReport(false, "???????????? ?????? ???????????????? ???????????? ??????????!");
        }

        // ???????????????? ?????????? ??????????????
        try {
            FileUploadUtil.saveFile("covers", track.getId() + ".jpg", cover);
        } catch (IOException e) {
            trackRepo.delete(track);
            return new TrackUploadReport(false, "???????????? ?????? ???????????????? ??????????????!");
        }

        userService.addRelease(author, track);
        return new TrackUploadReport(true, track);
    }

    public void editTrack(Track track) {
        trackRepo.save(track);
    }

    public boolean changeCover(Track track, MultipartFile cover) {
        try {
            FileUploadUtil.saveFile("covers", track.getId() + ".jpg", cover);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean activateTrack(Track track) {
        if (!track.isActive()) {
            track.setActive(true);
            trackRepo.save(track);
            return true;
        } else {
            return false;
        }
    }

    public boolean deactivateTrack(Track track) {
        if (track.isActive()) {
            track.setActive(false);
            trackRepo.save(track);
            return true;
        } else {
            return false;
        }
    }

}