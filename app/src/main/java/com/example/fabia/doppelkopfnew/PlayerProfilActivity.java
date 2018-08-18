package com.example.fabia.doppelkopfnew;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PlayerProfilActivity extends AppCompatActivity {

    private ArrayList<Game> gamesWithPlayer;
    private ArrayList<String> mitGewinner = new ArrayList<>();
    private ArrayList<String> mitVerlierer = new ArrayList<>();
    private int countRoundWins = 0;
    private int countRoundLoses = 0;
    private int countTotalRoundsOfPlayer = 0;
    private int countGames = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profil);

        //Create Backbutton on Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //get Player from Intent
        Player toDisplay = (Player) getIntent().getParcelableExtra("player");

        //Get Views
        TextView nameTV = (TextView) findViewById(R.id.profilPlayerNameTextView);
        TextView commentTV = (TextView) findViewById(R.id.profilPlayerCommentTextView);
        TextView winsTV = (TextView) findViewById(R.id.profilWinsTextView);
        TextView losesTV = (TextView) findViewById(R.id.profilLosesTextView);
        ImageView imageView = findViewById(R.id.profilPictureImageView);


        //Set Views to display Player
        nameTV.setText(toDisplay.getName());
        commentTV.setText("'" + toDisplay.getComment() + "'");

        Bitmap b = PictureStorageHelper.loadImageFromStorage(toDisplay.getImagepath(), toDisplay.getName() + ".jpg");
        if (b != null) {
            imageView.setImageBitmap(b);
        }

        //Spiele holen
        GameController gameController = new GameController(new ArrayList<Game>());
        gameController.readFromJSON(this);
        this.gamesWithPlayer = gameController.getGamesWithPlayer(toDisplay.getName());

        //Stats anzeigen
        generateStats(toDisplay.getName());
        displayStats();
    }

    private String ermittleAmHaeufigstenGewonnen() {
        Map<String, Integer> winnerMap = new HashMap<>();
        String winner = "";
        String initName = "";
        int counter = 0;
        if (mitGewinner != null && !mitGewinner.isEmpty()) {

            mitGewinner.trimToSize();
            String[] winners = mitGewinner.toArray(new String[mitGewinner.size()]);
            Arrays.sort(winners);
            for (String winnerName : winners) {
                if (initName.isEmpty()) {
                    initName = winnerName;
                    counter++;
                } else if (initName.equals(winnerName)) {
                    counter++;
                } else {
                    winnerMap.put(winnerName, counter);
                    counter = 0;
                    initName = winnerName;
                    counter++;
                }
            }
        }

        int maxValueInMap = (Collections.max(winnerMap.values()));
        for (Map.Entry<String, Integer> entry : winnerMap.entrySet()) {
            if (entry.getValue() == maxValueInMap) {
                winner = entry.getKey();
            }
        }
        return winner;
    }

    private String ermittleAmHaeufigstenVerloren() {
        Map<String, Integer> loserMap = new HashMap<>();
        String loser = "";
        String initName = "";
        int counter = 0;
        if (mitVerlierer != null && !mitVerlierer.isEmpty()) {

            mitVerlierer.trimToSize();
            String[] winners = mitVerlierer.toArray(new String[mitVerlierer.size()]);
            Arrays.sort(winners);
            for (String winnerName : winners) {
                if (initName.isEmpty()) {
                    initName = winnerName;
                    counter++;
                } else if (initName.equals(winnerName)) {
                    counter++;
                } else {
                    loserMap.put(winnerName, counter);
                    counter = 0;
                    initName = winnerName;
                    counter++;
                }
            }
        }

        int maxValueInMap = (Collections.max(loserMap.values()));
        for (Map.Entry<String, Integer> entry : loserMap.entrySet()) {
            if (entry.getValue() == maxValueInMap) {
                loser = entry.getKey();
            }
        }
        return loser;

    }


    private double ermittleGewinnChance(int countRoundWins, int countRoundLoses){

        double gewinnChance = (double) countRoundWins/ (countRoundLoses+countRoundWins);

        return gewinnChance;
    }

    private void checkMitGewinner(RoundStats roundStats, Game game){
        if(roundStats.isIsplayer1win()) {
            mitGewinner.add(game.getPlayer1().getName());
        } else if( roundStats.isIsplayer2win()) {
            mitGewinner.add(game.getPlayer2().getName());
        } else if( roundStats.isIsplayer3win()){
            mitGewinner.add(game.getPlayer3().getName());
        }
    }

    private void checkMitVerlierer(RoundStats roundStats, Game game){
        if(!roundStats.isIsplayer1win()) {
            mitVerlierer.add(game.getPlayer1().getName());
        } else if( !roundStats.isIsplayer2win()) {
            mitVerlierer.add(game.getPlayer2().getName());
        } else if( !roundStats.isIsplayer3win()){
            mitVerlierer.add(game.getPlayer3().getName());
        }
    }

    private void countTotalRounds(int countRoundLoses, int countRoundWins){
        countTotalRoundsOfPlayer = countRoundLoses + countRoundWins;
    }

    private void generateStats(String playerName){
        ermittleGewinnChance(countRoundWins,countRoundLoses);
        ermittleAmHaeufigstenGewonnen();
        ermittleAmHaeufigstenVerloren();


            for(Game game : gamesWithPlayer) {
                //Für jedes Game herrausfinden welchen Index Spieler in Game hat
                int playerindex = game.getIndexOfPlayer(playerName);
                //Bei gültigem Index weiter machen
                if (playerindex != -1) {
                    countGames++;
                    //Switch mit dem Index und dann jeweis für jede Runde schauen ob player mit index gewonnen hat
                    for(RoundStats roundStats : game.getRoundStats()) {
                        switch (playerindex) {
                            case 0:
                                    if (roundStats.isIsplayer0win()) {
                                        countRoundWins++;
                                        checkMitGewinner(roundStats, game);
                                    } else {
                                        countRoundLoses++;
                                        checkMitVerlierer(roundStats, game);
                                    }
                                break;
                            case 1:
                                    if (roundStats.isIsplayer1win()) {
                                        countRoundWins++;
                                        checkMitGewinner(roundStats, game);
                                    } else {
                                        countRoundLoses++;
                                        checkMitVerlierer(roundStats, game);
                                    }
                                break;
                            case 2:
                                    if (roundStats.isIsplayer2win()) {
                                        countRoundWins++;
                                        checkMitGewinner(roundStats, game);
                                    } else {
                                        countRoundLoses++;
                                        checkMitVerlierer(roundStats, game);
                                    }
                                break;
                            case 3:
                                    if (roundStats.isIsplayer3win()) {
                                        countRoundWins++;
                                        checkMitGewinner(roundStats, game);
                                    } else {
                                        countRoundLoses++;
                                        checkMitVerlierer(roundStats, game);
                                    }
                                break;
                        } //Switch end
                    }
                } else {
                    Log.e("Spieler nicht in Game", "displayStats player not in Game");
                }
            }

        countTotalRounds(countRoundLoses,countRoundWins);
    }

    private void displayStats(){
        //Anzeige updaten
        TextView wins = findViewById(R.id.profilWinsTextView);
        TextView loses = findViewById(R.id.profilLosesTextView);
        TextView gewinnChance = findViewById(R.id.profilGewinnchanceView);
        TextView mitGewinner = findViewById(R.id.profilMitgewinnerView);
        TextView mitVerlierer = findViewById(R.id.profilMitverliererView);
        TextView anzahlGames = findViewById(R.id.profilAllGamesView);

        wins.setText("Anzahl gewonnender Runden: " + String.valueOf(countRoundWins));
        loses.setText("Anzahl verlorender Runden: " + String.valueOf(countRoundLoses));
        loses.setText("Gewinnchance: " + String.valueOf(gewinnChance));
        loses.setText("Am meisten mitgewonnen: " + String.valueOf(countRoundLoses));
        loses.setText("Am meisten mitverloren: " + String.valueOf(countRoundLoses));
        loses.setText("Anzahl aller getätigten Spiele: " + String.valueOf(countGames));

    }


    //Für Toolbar damit beim drücken vom Backbutton zurückgesprungen wird
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
