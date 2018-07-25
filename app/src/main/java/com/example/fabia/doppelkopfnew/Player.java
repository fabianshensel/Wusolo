package com.example.fabia.doppelkopfnew;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {
    private String name;
    private String comment;
    private String imagepath;


    //benötigt um bei SpielerAuswahl im Spinner bei Gameerstellung den Namen anzuuzeigen
    @Override
    public String toString() {
        return this.name;
    }

    public Player(String name, String comment, String imagepath) {
        this.name = name;
        this.comment = comment;
        this.imagepath = imagepath;
    }

    /*
        PARCEABLE IMPLEMENTATION
     */

    public Player(Parcel in){
        String[] data = new String[3];
        in.readStringArray(data);
        this.name = data[0];
        this.comment = data[1];
        this.imagepath = data[2];
        //noch kein plan wie das laufen soll
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
                        this.imagepath
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
}
