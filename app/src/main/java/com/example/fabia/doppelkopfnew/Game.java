package com.example.fabia.doppelkopfnew;

import android.os.Binder;
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

    private Player player0 = new Player("player0");
    private Player player1 = new Player("player1");
    private Player player2 = new Player("player2");
    private Player player3 = new Player("player3");

    private String player0Name;
    private String player1Name;
    private String player2Name;
    private String player3Name;

    private Player winner;
    private Player loser;

    private String loserName;
    private String winnerName;

    private String address;


    private int gewinnPunktZahl;
    private int verlorenPunktzahl;


    String rightDate;

    private ArrayList<RoundStats> roundStats;

    public Game(String name, Player player0, Player player1, Player player2, Player player3, ArrayList<RoundStats> roundStats,String address, String rightDate) {
        this.name = name;
        this.player0 = player0;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.roundStats = roundStats;
        this.address = address;
        this.rightDate = rightDate;
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

    private  void generateWinnerAndLoser() {
        int pointsForPlayer0 = 0;
        int pointsForPlayer1 = 0;
        int pointsForPlayer2 = 0;
        int pointsForPlayer3 = 0;

        //Ermittle Endpunktzahlen
        if (!roundStats.isEmpty() && roundStats.size() != 0) {
            for (RoundStats roundStat : roundStats) {

                int points = roundStat.getPoints();
                if (roundStat.isIsplayer0win()) {
                    pointsForPlayer0 += points;
                } else {
                    pointsForPlayer0 -= points;
                }
                if (roundStat.isIsplayer1win()) {
                    pointsForPlayer1 += points;
                }else {
                    pointsForPlayer1 -= points;
                }
                if (roundStat.isIsplayer2win()) {
                    pointsForPlayer2 += points;
                }else {
                    pointsForPlayer2 -= points;
                }
                if (roundStat.isIsplayer3win()) {
                    pointsForPlayer3 += points;
                }else {
                    pointsForPlayer3 -= points;
                }


            }

            int[] points = {pointsForPlayer0, pointsForPlayer1, pointsForPlayer2, pointsForPlayer3};
            Arrays.sort(points);

            gewinnPunktZahl = points[3];
            verlorenPunktzahl = points[0];

            //Ermittle Genwinner
            if (points[3] == pointsForPlayer0) {
                winner = player0;
            } else if (points[3] == pointsForPlayer1) {
                winner = player1;
            } else if (points[3] == pointsForPlayer2) {
                winner = player2;
            } else if (points[3] == pointsForPlayer3) {
                winner = player3;
            }
            //Ermittle Verlierer
            if (points[0] == pointsForPlayer0) {
                loser = player0;
            } else if (points[0] == pointsForPlayer1) {
                loser = player1;
            } else if (points[0] == pointsForPlayer2) {
                loser = player2;
            } else if (points[0] == pointsForPlayer3) {
                loser = player3;
            }
        } else {
            loser = new Player("Loser");
            winner = new Player("Winner");
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private void generateStrings(){
        player1Name = player1.getName();
        player2Name = player2.getName();
        player3Name = player3.getName();
        player0Name = player0.getName();
        winnerName = winner.getName();
        loserName = loser.getName();
    }

    public Game(Parcel in){
        this.name = in.readString();
        this.verlorenPunktzahl = in.readInt();
        this.gewinnPunktZahl = in.readInt();
        this.player0Name = in.readString();
        this.player1Name = in.readString();
        this.player2Name = in.readString();
        this.player3Name = in.readString();
        this.winnerName = in.readString();
        this.loserName = in.readString();
        this.rightDate = in.readString();
        this.address = in.readString();


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
        generateWinnerAndLoser();
        generateStrings();
                {
                        dest.writeString(name);
                        dest.writeInt(verlorenPunktzahl);
                        dest.writeInt(gewinnPunktZahl);
                        dest.writeString(player0Name);
                        dest.writeString(player1Name);
                        dest.writeString(player2Name);
                        dest.writeString(player3Name);
                        dest.writeString(winnerName);
                        dest.writeString(loserName);
                        dest.writeString(rightDate);
                        dest.writeString(address);

                }
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

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getPlayer3() {
        return player3;
    }

    public ArrayList<RoundStats> getRoundStats() {
        return roundStats;
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

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void setLoser(Player loser) {
        this.loser = loser;
    }

    public String getLoserName() {
        return loserName;
    }

    public String getWinnerName() {
        return winnerName;
    }


    public String getRightDate() {
        return rightDate;
    }

    public String getAddress() {
        return address;
    }
}


