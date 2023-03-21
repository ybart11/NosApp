package com.example.nosapp;

public class Actors {
    private String actorID;
    private String showname;
    private int order;
    private String characterName;
    private String actorName;

    Actors(){

    }

    Actors(int order,String characterName,String actorName){
        this.order=order;
        this.characterName=characterName;
        this.actorName=actorName;

    }
}
