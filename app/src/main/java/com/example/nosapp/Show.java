package com.example.nosapp;

import java.util.Date;

public class Show {
    private String showname;
    private String channel;
    private String type;
    private Date startDate;
    private Date endDate;
    private int seasons;
    private int episodes;
    private String synopsis;
    private int logo;

    public Show() {

    }


    public Show(String showname, String channel, String type,
                Date startDate, Date endDate, int seasons,
                int episodes, String synopsis, int logo) {

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

    public Show(String showname, String channel, Date startDate,
                Date endDate, int seasons, int episodes,
                String synopsis) {
        this.showname = showname;
        this.channel = channel;
        this.startDate = startDate;
        this.endDate = endDate;
        this.seasons = seasons;
        this.episodes = episodes;
        this.synopsis = synopsis;

    }

    public Show(String showname, int logo) {

        this.showname = showname;
        this.logo = logo;

    }


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
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date i) { startDate = i; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date i) { endDate = i; }
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
    public int getLogo() {
        return logo;
    }
    public void setLogo(int i) { logo= 1; }
}
