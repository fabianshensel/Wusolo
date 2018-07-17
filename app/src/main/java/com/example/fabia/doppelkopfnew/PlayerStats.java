package com.example.fabia.doppelkopfnew;

public class PlayerStats {
    private int winCount;
    private int lossCount;

    public PlayerStats(int winCount, int lossCount) {
        this.winCount = winCount;
        this.lossCount = lossCount;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getLossCount() {
        return lossCount;
    }

    public void setLossCount(int lossCount) {
        this.lossCount = lossCount;
    }
}
