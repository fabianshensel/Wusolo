package com.example.fabia.doppelkopfnew;

import java.util.ArrayList;

public class PlayerController {
    private ArrayList<Player> playerList;

    public PlayerController(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    public void fillList(){
        playerList.add(new Player("Fabian Hensel","Ich spiele super gerne Doppelkopf",null,new PlayerStats(1,99)));
        playerList.add(new Player("Torben Glass","Halt dein Maul",null,new PlayerStats(99,1)));
        playerList.add(new Player("Jeremy Tuller","Ich hab dann mal Recht studiert",null,new PlayerStats(0,0)));
        playerList.add(new Player("Simon Wusolo","Wo ist die Milch hin?",null,new PlayerStats(99,99)));
    }
}
