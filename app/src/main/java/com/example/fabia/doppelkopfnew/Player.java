package com.example.fabia.doppelkopfnew;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {
    private String name;
    private String comment;
    private Image image;
    private PlayerStats stats;
    private String imagepath;


    //benötigt um bei SpielerAuswahl im Spinner bei Gameerstellung den Namen anzuuzeigen
    @Override
    public String toString() {
        return this.name;
    }

    public Player(String name, String comment, Image image, PlayerStats stats, String imagepath) {
        this.name = name;
        this.comment = comment;
        this.image = image;
        this.stats = stats;
        this.imagepath = imagepath;
    }

    /*
        PARCEABLE IMPLEMENTATION
     */

    public Player(Parcel in){
        String[] data = new String[5];
        in.readStringArray(data);
        this.name = data[0];
        this.comment = data[1];
        this.imagepath = data[2];
        this.stats = new PlayerStats(Integer.valueOf(data[3]),Integer.valueOf(data[4]));
        //noch kein plan wie das laufen soll
        this.image = null;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest,int flags){
        String s[] = new String[]
                {
                        this.name,
                        this.comment,
                        this.imagepath,
                        String.valueOf(this.stats.getWinCount()),
                        String.valueOf(this.stats.getLossCount())
                };
        dest.writeStringArray(s);
    }

    @Override
    public int describeContents(){
        return 0;
    }
     /*
        PARCEABLE IMPLEMENTATION ENDE
     */

    //Getter und Setter
    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
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
