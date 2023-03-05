package com.example.nosapp;

import java.util.Date;

public class Favorites extends Shows {
    private int favsID;
    private int videoID;



    public Favorites() {
    }

    public Favorites(String logo) {
        super(logo);
    }

    public Favorites(int videoID) {


        this.videoID = videoID;


    }

    public int getFavsID() {
        return favsID;
    }
    public void setFavID(int i) {
        favsID = 1;
    }

    public int getShowID() {
        return videoID;
    }
    public void setShowID(int i) {
        videoID = 1;
    }

}
