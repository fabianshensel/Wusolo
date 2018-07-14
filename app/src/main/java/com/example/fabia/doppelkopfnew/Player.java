package com.example.fabia.doppelkopfnew;

import android.media.Image;

public class Player {
    private String name;
    private String comment;
    private Image image;
    private PlayerStats stats;

    public Player(String name, String comment, Image image, PlayerStats stats) {
        this.name = name;
        this.comment = comment;
        this.image = image;
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public PlayerStats getStats() {
        return stats;
    }

    public void setStats(PlayerStats stats) {
        this.stats = stats;
    }
}
