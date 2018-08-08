package com.example.fabia.doppelkopfnew;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Game implements Parcelable{

    private String name;

    private Player player0;
    private Player player1;
    private Player player2;
    private Player player3;

    private Player winner;
    private Player loser;

    private int gewinnPunktZahl;
    private int verlorenPunktzahl;

    SimpleDateFormat simpleDate;

    DateTimeFormatter dtf;

    private ArrayList<RoundStats> roundStats;

    public Game(String name, Player player0, Player player1, Player player2, Player player3, ArrayList<RoundStats> roundStats) {
        this.name = name;
        this.player0 = player0;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        LocalDateTime now = LocalDateTime.now();
        dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        dtf.format(now);
    }

    /*
    returns -1 if player not in game
     */
    public int getIndexOfPlayer(String name){
        if(player0.getName().equals(name)){
            return 0;
        }
        if(player1.getName().equals(name)){
            return 1;
        }
        if(player2.getName().equals(name)){
            return 2;
        }
        if(player3.getName().equals(name)){
            return 3;
        }
        return -1;
    }

    public void generateWinnerAndLoser() {
        int pointsForPlayer0 = 0;
        int pointsForPlayer1 = 0;
        int pointsForPlayer2 = 0;
        int pointsForPlayer3 = 0;

    //Ermittle Endpunktzahlen
        for (RoundStats roundStat : roundStats) {

            int points = roundStat.getPoints();
            if (roundStat.isIsplayer0win()) {
                pointsForPlayer0 += points;
            }
            if (roundStat.isIsplayer1win()) {
                pointsForPlayer1 += points;
            }
            if (roundStat.isIsplayer2win()) {
                pointsForPlayer2 += points;
            }
            if (roundStat.isIsplayer3win()) {
                pointsForPlayer3 += points;
            }
        }

        int[] points = {pointsForPlayer0, pointsForPlayer1, pointsForPlayer2, pointsForPlayer3};
        Arrays.sort(points);

        gewinnPunktZahl = points[0];
        verlorenPunktzahl = points[3];

        //Ermittle Genwinner
        if (points[0] == pointsForPlayer0) {
            winner = player0;
        } else if (points[0] == pointsForPlayer1) {
            winner = player1;
        } else if (points[0] == pointsForPlayer2) {
            winner = player2;
        } else if (points[0] == pointsForPlayer3) {
            winner = player3;
        }
        //Ermittle Verlierer
        if (points[3] == pointsForPlayer0) {
            loser = player0;
        } else if (points[3] == pointsForPlayer1) {
            loser = player1;
        } else if (points[3] == pointsForPlayer2) {
            loser = player2;
        } else if (points[3] == pointsForPlayer3) {
            loser = player3;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Game(Parcel in){
        String[] data = new String[9];
        in.readStringArray(data);
        this.name = data[0];
        this.verlorenPunktzahl = Integer.parseInt(data[1]);
        this.gewinnPunktZahl = Integer.parseInt(data[2]);
        this.player0.setName(data[3]);
        this.player1.setName(data[4]);
        this.player2.setName(data[5]);
        this.player3.setName(data[6]);
        this.winner.setName(data[7]);
        this.loser.setName(data[8]);
        //this.simpleDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(data[9]);


        //noch kein plan wie das laufen soll
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest,int flags){
        String verlorenString = String.valueOf(verlorenPunktzahl);
        String gewonnenString = String.valueOf(gewinnPunktZahl);
        String s[] = new String[]
                {
                        this.name,
                        verlorenString,
                        gewonnenString,
                        this.player0.getName(),
                        this.player1.getName(),
                        this.player2.getName(),
                        this.player3.getName(),
                        this.loser.getName(),
                        this.winner.getName(),

                };
        dest.writeStringArray(s);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getPlayer0() {
        return player0;
    }

    public void setPlayer0(Player player0) {
        this.player0 = player0;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getPlayer3() {
        return player3;
    }

    public void setPlayer3(Player player3) {
        this.player3 = player3;
    }

    public ArrayList<RoundStats> getRoundStats() {
        return roundStats;
    }

    public void setRoundStats(ArrayList<RoundStats> roundStats) {
        this.roundStats = roundStats;
    }

    public int getGesamtAnzahlRunden(){
        return roundStats.size();
    }

    public DateTimeFormatter getDate() {
    return dtf;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getLoser() {
        return loser;
    }

    public int getGewinnPunktZahl() {
        return gewinnPunktZahl;
    }

    public int getVerlorenPunktzahl() {
        return verlorenPunktzahl;
    }
}

