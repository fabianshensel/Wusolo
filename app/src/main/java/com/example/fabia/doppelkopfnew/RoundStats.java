package com.example.fabia.doppelkopfnew;

public class RoundStats {

    //Index of Player how won round
    private boolean isplayer0win;
    private boolean isplayer1win;
    private boolean isplayer2win;
    private boolean isplayer3win;

    private boolean isSoloWin;

    private int bockRoundsleft;
    private int points;

    public RoundStats(boolean isplayer0win, boolean isplayer1win, boolean isplayer2win, boolean isplayer3win, boolean isSoloWin, int points, int bockRoundsleft) {
        this.isplayer0win = isplayer0win;
        this.isplayer1win = isplayer1win;
        this.isplayer2win = isplayer2win;
        this.isplayer3win = isplayer3win;
        this.isSoloWin = isSoloWin;
        this.points = points;
        this.bockRoundsleft = bockRoundsleft;
    }

    public boolean isBockRound(){
        if(bockRoundsleft > 0){
            return true;
        }
        return false;
    }

    public boolean isIsplayer0win() {
        return isplayer0win;
    }

    public void setIsplayer0win(boolean isplayer0win) {
        this.isplayer0win = isplayer0win;
    }

    public boolean isIsplayer1win() {
        return isplayer1win;
    }

    public void setIsplayer1win(boolean isplayer1win) {
        this.isplayer1win = isplayer1win;
    }

    public boolean isIsplayer2win() {
        return isplayer2win;
    }

    public void setIsplayer2win(boolean isplayer2win) {
        this.isplayer2win = isplayer2win;
    }

    public boolean isIsplayer3win() {
        return isplayer3win;
    }

    public void setIsplayer3win(boolean isplayer3win) {
        this.isplayer3win = isplayer3win;
    }

    public boolean isSoloWin() {
        return isSoloWin;
    }

    public void setSoloWin(boolean soloWin) {
        isSoloWin = soloWin;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getBockRoundsleft() {
        return bockRoundsleft;
    }

    public void setBockRoundsleft(int bockRoundsleft) {
        this.bockRoundsleft = bockRoundsleft;
    }
}
