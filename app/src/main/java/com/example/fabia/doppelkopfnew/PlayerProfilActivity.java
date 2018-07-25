package com.example.fabia.doppelkopfnew;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerProfilActivity extends AppCompatActivity {

    private ArrayList<Game> gamesWithPlayer;

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
        TextView nameTV = (TextView)findViewById(R.id.profilPlayerNameTextView);
        TextView commentTV = (TextView)findViewById(R.id.profilPlayerCommentTextView);
        TextView winsTV = (TextView)findViewById(R.id.profilWinsTextView);
        TextView losesTV = (TextView)findViewById(R.id.profilLosesTextView);
        ImageView imageView = findViewById(R.id.profilPictureImageView);



        //Set Views to display Player
        nameTV.setText(toDisplay.getName());
        commentTV.setText("'" + toDisplay.getComment() + "'");

        Bitmap b = PictureStorageHelper.loadImageFromStorage(toDisplay.getImagepath() , toDisplay.getName() + ".jpg");
        if(b != null){
            imageView.setImageBitmap(b);
        }

        //Spiele holen
        GameController gameController = new GameController(new ArrayList<Game>());
        gameController.readFromJSON(this);
        this.gamesWithPlayer = gameController.getGamesWithPlayer(toDisplay.getName());

        //Stats anzeigen
        displayStats(toDisplay.getName());


    }

    private void displayStats(String playerName){
        int countRoundWins = 0;
        int countRoundLoses = 0;


        //Jedes game durchgehen
        for(int i = 0 ; i < gamesWithPlayer.size(); i++){
            //Für jedes Game herrausfinden welchen Index Spieler in Game hat
            int playerindex = gamesWithPlayer.get(i).getIndexOfPlayer(playerName);
            //Wenn Spieler nicht im Game
            if(playerindex == -1){
                Log.e("Spieler nicht in Game","displayStats player not in Game");
                return;
            }
            //Switch mit dem Index und dann jeweis für jede Runde schauen ob player mit index gewonnen hat
            switch (playerindex){
                case 0:
                    for(int j = 0; j < gamesWithPlayer.get(i).getRoundStats().size();j++){
                        if(gamesWithPlayer.get(i).getRoundStats().get(j).isIsplayer0win()){
                            countRoundWins++;
                        }else{
                            countRoundLoses++;
                        }
                    }
                    break;
                case 1:
                    for(int j = 0; j < gamesWithPlayer.get(i).getRoundStats().size();j++){
                        if(gamesWithPlayer.get(i).getRoundStats().get(j).isIsplayer1win()){
                            countRoundWins++;
                        }else{
                            countRoundLoses++;
                        }
                    }
                    break;
                case 2:
                    for(int j = 0; j < gamesWithPlayer.get(i).getRoundStats().size();j++){
                        if(gamesWithPlayer.get(i).getRoundStats().get(j).isIsplayer2win()){
                            countRoundWins++;
                        }else{
                            countRoundLoses++;
                        }
                    }
                    break;
                case 3:
                    for(int j = 0; j < gamesWithPlayer.get(i).getRoundStats().size();j++){
                        if(gamesWithPlayer.get(i).getRoundStats().get(j).isIsplayer3win()){
                            countRoundWins++;
                        }else{
                            countRoundLoses++;
                        }
                    }
                    break;
            } //Switch end



        } //for-Loop end

        //Anzeige updaten
        TextView wins = findViewById(R.id.profilWinsTextView);
        TextView loses = findViewById(R.id.profilLosesTextView);

        wins.setText("Anzahl gewonnender Runden: " + String.valueOf(countRoundWins));
        loses.setText("Anzahl verlorender Runden: " + String.valueOf(countRoundLoses));

    }


    //Für Toolbar damit beim drücken vom Backbutton zurückgesprungen wird
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
