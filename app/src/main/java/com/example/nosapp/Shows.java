package com.example.nosapp;

import java.util.Date;

public class Shows {
    private int showID;
    private String showname;
    private String channel;
    private String type;
    private String startDate;
    private String endDate;
    private int seasons;
    private int episodes;
    private String synopsis;
    private String logo;
    private int showLogo;
    private String playlistId;

    public Shows() {

    }


    public Shows(String showname, String channel, String type,
                 String startDate, String endDate, int seasons,
                 int episodes, String synopsis, String logo) {

        this.showname = showname;
        this.channel = channel;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.seasons = seasons;
        this.episodes = episodes;
        this.synopsis = synopsis;
        this.logo = logo;

    }

    public Shows(String showname, String channel, String startDate,
                 String endDate, int seasons, int episodes,
                 String synopsis) {
        this.showname = showname;
        this.channel = channel;
        this.startDate = startDate;
        this.endDate = endDate;
        this.seasons = seasons;
        this.episodes = episodes;
        this.synopsis = synopsis;

    }

    public Shows(int showID, String showname, String logo) {

        this.showID = showID;
        this.showname = showname;
        this.logo = logo;
    }

    public Shows(int showID, String showname, int logo) {

        this.showID = showID;
        this.showname = showname;
        this.showLogo = logo;
    }

    public Shows(String showname, int logo, String playlistId) {

        this.showname = showname;
        this.showLogo = logo;
        this.playlistId = playlistId;
    }

    public Shows(String logo) {
        this.logo = logo;
    }

    public Shows(String showname, String playlistid) {
        this.showname = showname;
        this.playlistId = playlistid;
    }

    public int getShowID() {
        return showID;
    }
    public void setShowID(int i) { showID = 1; }
    public String getShowName() {
        return showname;
    }
    public void setShowName(String i) {
        showname = i;
    }
    public String getChannel() {
        return channel;
    }
    public void setChannel(String i) {
        channel = i;
    }
    public String getType() {
        return type;
    }
    public void setType(String i) {
        type = i;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String i) { startDate = i; }
    public String getEndDate() { return endDate; }
    public void setEndDate(String i) { endDate = i; }
    public int getSeasons() {
        return seasons;
    }
    public void setSeasons(int i) { seasons = 1; }
    public int getEpisodes() {
        return episodes;
    }
    public void setEpisodes(int i) { episodes = 1; }
    public String getSynopsis() {
        return synopsis;
    }
    public void setSynopsis(String i) {
        synopsis = i;
    }
    public String getLogo() {
        return logo;
    }
    public void setLogo(String i) { logo = i; }

    public int getShowLogo() {
        return showLogo;
    }

    public String getPlaylistId() {
        return playlistId;
    }
}
