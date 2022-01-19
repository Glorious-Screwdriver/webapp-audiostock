package com.audiostock.service;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.repos.TrackRepo;
import com.audiostock.service.exceptions.TrackNotFoundException;
import com.audiostock.service.reports.TrackUploadReport;
import com.audiostock.service.util.FileUploadUtil;
import com.audiostock.service.util.Utils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrackService {

    final private TrackRepo trackRepo;

    public TrackService(TrackRepo trackRepo) {
        this.trackRepo = trackRepo;
    }

    // Representation

    public Track getTrackById(Long id) throws TrackNotFoundException {
        return trackRepo.findById(id).orElseThrow(() -> new TrackNotFoundException(String.valueOf(id)));
    }

    public List<Track> getCatalog(int page, int size, String sorting, String mood, String genre, Long lbpm, Long hbpm) {
        return trackRepo.findAllByGenreAndMoodAndBpmIsGreaterThanAndBpmIsLessThan(
                PageRequest.of(page, size).withSort(Sort.by(sorting)), genre, mood, lbpm, hbpm
        ).stream().collect(Collectors.toList());
    }

    public List<Track> getCatalog(int page, int size) {
        return getCatalog(page, size, "name", "", "", 0L, 999L);
    }

    public List<Track> getAll() {
        return trackRepo.findAllByActiveTrue();
    }

    // Properties

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

    public TrackUploadReport uploadTrack(User author,
                                         String name,
                                         String description,
                                         Long price,
                                         String genre,
                                         String mood,
                                         Long bpm,
                                         MultipartFile audio,
                                         MultipartFile cover) {
        Track track = new Track(author, name, description, price, genre, mood, bpm);

        // Загрузка файла трека
        try {
            FileUploadUtil.saveFile(
                    "tracks/" + author.getId(),
                    String.valueOf(track.getId()) + Utils.getFileExtension(audio.getOriginalFilename()),
                    audio
            );
        } catch (IOException e) {
            return new TrackUploadReport(false, "Ошибка при загрузке трека!");
        }

        // Загрузка файла обложки
        try {
            FileUploadUtil.saveFile(
                    "covers/" + author.getId(),
                    String.valueOf(track.getId()) + Utils.getFileExtension(cover.getOriginalFilename()),
                    cover
            );
        } catch (IOException e) {
            return new TrackUploadReport(false, "Ошибка при загрузке обложки!");
        }

        trackRepo.save(track);
        return new TrackUploadReport(true, track);
    }

    public void editTrack(Track track) {
        trackRepo.save(track);
    }

}