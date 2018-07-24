package com.example.fabia.doppelkopfnew;

import java.util.ArrayList;

public class Game {

    private String name;

    private Player player0;
    private Player player1;
    private Player player2;
    private Player player3;

    private ArrayList<RoundStats> roundStats;

    public Game(String name, Player player0, Player player1, Player player2, Player player3) {
        this.name = name;
        this.player0 = player0;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
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
}
