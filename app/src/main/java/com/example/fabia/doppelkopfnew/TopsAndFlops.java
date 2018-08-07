package com.example.fabia.doppelkopfnew;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TopsAndFlops {

    private String bestPlayer;
    private String worstPlayer;
    private int highestWin;
    private int highestNegativWin;
    private int longestGame;


    public TopsAndFlops(ArrayList<Game> games){
        ArrayList<GameStatic> gameStatics;


        for(Game game : games){
            int pointsForPlayer0 = 0;
            int pointsForPlayer1 = 0;
            int pointsForPlayer2 = 0;
            int pointsForPlayer3 = 0;
            for(RoundStats roundStats : game.getRoundStats()){
                int points = roundStats.getPoints();
                if(roundStats.isIsplayer0win()){
                    pointsForPlayer0 += points;
                }
                if(roundStats.isIsplayer1win()){
                    pointsForPlayer1 += points;
                }
                if(roundStats.isIsplayer2win()){
                    pointsForPlayer2 += points;
                }
                if(roundStats.isIsplayer3win()){
                    pointsForPlayer3 += points;
                }

            }
            //Finde gewinner und Verlierer aus jedem Spiel

            //Berechne Gesamtpunktzahl der einzelnen Spieler


        }

    }
    public String getBestPlayer() {
        return bestPlayer;
    }

    public String getWorstPlayer() {
        return worstPlayer;
    }

    public int getHighestWin() {
        return highestWin;
    }

    public int getHighestNegativWin() {
        return highestNegativWin;
    }

    public int getLongestGame() {
        return longestGame;
    }
}
