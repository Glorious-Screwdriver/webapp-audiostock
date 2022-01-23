package com.audiostock.service;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.repos.TrackRepo;
import com.audiostock.service.exceptions.TrackNotFoundException;
import com.audiostock.service.reports.TrackUploadReport;
import com.audiostock.service.util.FileUploadUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public List<Track> getCatalog(int page, int size) {
        return getCatalog(page, size, "name", "", "", 0L, 999L);
    }

    public List<Track> getCatalog(int page, int size, String sorting,
                                  String mood, String genre, Long lbpm, Long hbpm) {
        return trackRepo.findAllByGenreAndMoodAndBpmIsGreaterThanAndBpmIsLessThan(
                PageRequest.of(page, size).withSort(Sort.by(sorting)), genre, mood, lbpm, hbpm
        ).stream().collect(Collectors.toList());
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

        // Загрузка оригинального трека
        try {
            FileUploadUtil.saveFile("tracks", track.getId() + ".mp3", original);
        } catch (IOException e) {
            trackRepo.delete(track);
            return new TrackUploadReport(false, "Ошибка при загрузке оригинального трека!");
        }

        // Загрузка превью трека
        try {
            FileUploadUtil.saveFile("previews", track.getId() + ".mp3", preview);
        } catch (IOException e) {
            trackRepo.delete(track);
            return new TrackUploadReport(false, "Ошибка при загрузке превью трека!");
        }

        // Загрузка файла обложки
        try {
            FileUploadUtil.saveFile("covers", track.getId() + ".jpg", cover);
        } catch (IOException e) {
            trackRepo.delete(track);
            return new TrackUploadReport(false, "Ошибка при загрузке обложки!");
        }

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
            return true;
        } else {
            return false;
        }
    }

    public boolean deactivateTrack(Track track) {
        if (track.isActive()) {
            track.setActive(false);
            return true;
        } else {
            return false;
        }
    }

}