package com.example.nosapp;

public class Show {
    private String name;
    private int imageResource;

    public Show(String name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return imageResource;
    }
}
