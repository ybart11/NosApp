package com.example.nosapp;

import java.util.Date;

public class Favorites {
    private int videoId;
    private String showname;
    private int logo;



    public Favorites() {

    }


    public Favorites(String showname, String channel, String type,
                     Date startDate, Date endDate, int seasons,
                     int episodes, String synopsis, int logo) {

        this.showname = showname;


    }

    public Favorites(String showname, String channel, Date startDate,
                     Date endDate, int seasons, int episodes,
                     String synopsis) {
        this.showname = showname;


    }

    public Favorites(int videoId, String showname, int logo) {

        this.videoId = videoId;
        this.showname = showname;
        this.logo = logo;

    }
    public int getShowID() {
        return videoId;
    }
    public void setShowID(int i) { videoId = 1; }
    public String getShowName() {
        return showname;
    }
    public void setShowName(String i) {
        showname = i;
    }
    public int getLogo() {
        return logo;
    }
    public void setLogo(int i) { logo= 1; }
}
