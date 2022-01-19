package com.audiostock.service.reports;

import com.audiostock.entities.Track;

public class TrackUploadReport extends Report {
    Track track;

    public TrackUploadReport(boolean successful, Track track) {
        super(successful);
        this.track = track;
    }

    public TrackUploadReport(boolean successful, String message) {
        super(successful, message);
    }

    public Track getTrack() {
        return track;
    }
}
