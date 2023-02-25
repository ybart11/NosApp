package com.example.nosapp;

public class FavoriteClip {
    private String videoID;
    private String showName;
    private String clipName;

    public FavoriteClip(String videoID, String showName, String clipName) {
        this.videoID = videoID;
        this.showName = showName;
        this.clipName = clipName;

    }

    public String getvideoID() {
        return videoID;
    }

    public String getshowName() {
        return showName;
    }

    public String getclipName() {
        return clipName;
    }
}
