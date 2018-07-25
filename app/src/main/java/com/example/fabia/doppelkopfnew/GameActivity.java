package com.example.fabia.doppelkopfnew;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    Game game;
    GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Create Backbutton on Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Get Selected Game
        gameController = new GameController(new ArrayList<Game>());
        gameController.readFromJSON(GameActivity.this);
        game = gameController.getGamebyName(getIntent().getStringExtra("name"));

        if(GameController.isGameDirty(game)){
            Toast.makeText(GameActivity.this, "Fehlerhaftes Spiel", Toast.LENGTH_SHORT).show();
            this.finish();
            return;
        }



        //Display GameName
        TextView nameView = findViewById(R.id.GameNameTextView);
        nameView.setText(game.getName());

        //Display PlayerNames
        TextView p0name = findViewById(R.id.player0TV);
        TextView p1name = findViewById(R.id.player1TV);
        TextView p2name = findViewById(R.id.player2TV);
        TextView p3name = findViewById(R.id.player3TV);
        p0name.setText(game.getPlayer0().getName());
        p1name.setText(game.getPlayer1().getName());
        p2name.setText(game.getPlayer2().getName());
        p3name.setText(game.getPlayer3().getName());

        //Init ScoreViews
        TextView score0 = findViewById(R.id.score0TextView);
        TextView score1 = findViewById(R.id.score1TextView);
        TextView score2 = findViewById(R.id.score2TextView);
        TextView score3 = findViewById(R.id.score3TextView);

        score0.setText(String.valueOf(0));
        score1.setText(String.valueOf(0));
        score2.setText(String.valueOf(0));
        score3.setText(String.valueOf(0));

        if(!game.getRoundStats().isEmpty()){
            displayStats();
        }
        
        //Get Enter Button
        Button enterButton = findViewById(R.id.enterPointsButton);

        //-----BEGIN DIALOG ABLAUF FÜR EINGABE VON SPIELDATEN----
        //Config Dialog for Enterbutton
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Alert Dialog erstellen welcher Spieler anzeigt aus welchen man gewinner aussucht
                AlertDialog.Builder winnerDialog = new AlertDialog.Builder(GameActivity.this);
                winnerDialog.setTitle("Gewinner");

                //Liste an Spielern
                final String[] items = new String[]{
                  game.getPlayer0().getName(),
                        game.getPlayer1().getName(),
                        game.getPlayer2().getName(),
                        game.getPlayer3().getName(),
                        "Böcke"

                };
                //Liste welche Spieler Ausgewählt sind
                final boolean[] checkedItems = new boolean[]{
                        false,
                        false,
                        false,
                        false,
                        false

                };
                //Liste zum anzeigen festlegen und checkedItems updaten wenn onClick event
                winnerDialog.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        checkedItems[i] = b;

                    }
                });

                //Weiter Button Configurieren
                winnerDialog.setPositiveButton("Weiter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                winnerDialog.setNegativeButton("Abbrechen",null);


                //Dialog anzeigen
                final AlertDialog winnerAlertDialog = winnerDialog.show();

                //Weiter Button onClick Listener überschreiben damit bei flascher Eingabe Dialog trzdm offen bleibt
                winnerAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Anzahl ausgewählter Spieler bestimmen
                        int counter = 0;
                        for(int j = 0; j < 4; j++){
                            if(checkedItems[j]){
                                counter++;
                            }
                        }
                        //Wenn nicht 1 oder 2 Spieler ausgewählt sind Toast ausgeben und return
                        if(counter != 1 && counter != 2){
                            Toast.makeText(GameActivity.this, "Es können nur 1 oder 2 Spieler gewinnen", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //Wenn Auswahl ok Dialog schließen
                        winnerAlertDialog.dismiss();

                        //Neuen Dialog zum Wählen der Punktzahl Builden
                        final AlertDialog.Builder pointsDialog = new AlertDialog.Builder(GameActivity.this);

                        //NumberPicker erstellen um Auswahl zu ermöglichen
                        final NumberPicker numberPicker = new NumberPicker(GameActivity.this);
                        numberPicker.setMaxValue(50);
                        numberPicker.setMinValue(0);

                        pointsDialog.setView(numberPicker);

                        pointsDialog.setNegativeButton("Abbruch",null);

                        pointsDialog.setPositiveButton("Eintragen",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //Ausgewählte anzahl an Punkten holen und für Verlierer Anzahl Punkte invertieren
                                int points = numberPicker.getValue();
                                int p0 = points;
                                int p1 = points;
                                int p2 = points;
                                int p3 = points;
                                int bock = 0;

                                if(!checkedItems[0]){
                                    p0 = -p0;
                                }
                                if(!checkedItems[1]){
                                    p1 = -p1;
                                }
                                if(!checkedItems[2]){
                                    p2 = -p2;
                                }
                                if(!checkedItems[3]){
                                    p3 = -p3;
                                }
                                if(checkedItems[4]){
                                    bock = 3;
                                    Log.d("Böcke","Böcke resettet");
                                }else{
                                    if(!game.getRoundStats().isEmpty()){
                                        if(game.getRoundStats().get(game.getRoundStats().size()-1).getBockRoundsleft() > 0){
                                            bock = game.getRoundStats().get(game.getRoundStats().size()-1).getBockRoundsleft() - 1;
                                            Log.d("Böcke","Böcke--: " + bock);
                                        }
                                    }

                                }



                                insertStats(p0,p1,p2,p3,bock,game.getRoundStats().size());
                                //Anzeige vom Punktestand anpassen
                                updateScore(p0,p1,p2,p3);

                                RoundStats thisRound = new RoundStats(checkedItems[0],checkedItems[1],checkedItems[2],checkedItems[3],checkForSoloWin(checkedItems),points,bock);

                                game.getRoundStats().add(thisRound);

                            }
                        });

                        pointsDialog.show();
                    }
                });


                


            }
        });
        //-----ENDE DIALOG ABLAUF FÜR EINGABE VON SPIELDATEN----






    }

    //Init
    private void displayStats(){
        for(int i = 0 ; i < game.getRoundStats().size();i++){
            int points0 = game.getRoundStats().get(i).getPoints();
            int points1 = game.getRoundStats().get(i).getPoints();
            int points2 = game.getRoundStats().get(i).getPoints();
            int points3 = game.getRoundStats().get(i).getPoints();

            if(!game.getRoundStats().get(i).isIsplayer0win()){
                points0 = -points0;
            }
            if(!game.getRoundStats().get(i).isIsplayer1win()){
                points1 = -points1;
            }
            if(!game.getRoundStats().get(i).isIsplayer2win()){
                points2 = -points2;
            }
            if(!game.getRoundStats().get(i).isIsplayer3win()){
                points3 = -points3;
            }

            insertStats(points0,points1,points2,points3,game.getRoundStats().get(i).getBockRoundsleft(),i);
            updateScore(points0,points1,points2,points3);
        }
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Nur schreiben wenn Game nicht dirty
        if(!GameController.isGameDirty(game)){
            gameController.writeToJSON(GameActivity.this);
        }

    }

    /*
        funzt nur wenn max 2 spieler gewinnen können
         */
    private boolean checkForSoloWin(boolean[] checkedPlayers){
        boolean isSoloWin = false;
        if(checkedPlayers[0]){
            isSoloWin = !isSoloWin;
        }
        if(checkedPlayers[1]){
            isSoloWin = !isSoloWin;
        }
        if(checkedPlayers[2]){
            isSoloWin = !isSoloWin;
        }
        if(checkedPlayers[3]){
            isSoloWin = !isSoloWin;
        }
        return isSoloWin;
    }

    /*
    Updated die Punkteanzeige mit den übergebenen Werten
     */
    private void updateScore(int points0,int points1, int points2, int points3){
        //Get ScoreViews und Update den Angezeigten Score
        TextView score0 = findViewById(R.id.score0TextView);
        TextView score1 = findViewById(R.id.score1TextView);
        TextView score2 = findViewById(R.id.score2TextView);
        TextView score3 = findViewById(R.id.score3TextView);

        int curr = Integer.valueOf(score0.getText().toString());
        curr = curr + points0;
        score0.setText(String.valueOf(curr));

        curr = Integer.valueOf(score1.getText().toString());
        curr = curr + points1;
        score1.setText(String.valueOf(curr));

        curr = Integer.valueOf(score2.getText().toString());
        curr = curr + points2;
        score2.setText(String.valueOf(curr));

        curr = Integer.valueOf(score3.getText().toString());
        curr = curr + points3;
        score3.setText(String.valueOf(curr));


    }

    private void insertStats(int points0,int points1, int points2, int points3, int countBockRounds,int roundNr){


        //StatsTable holen in den inserted wird
        TableLayout statsTable = findViewById(R.id.gameStatsTableLayout);

        //Neue TableRow erstellen welche dem Table hinzugefügt wird
        TableRow toAdd = new TableRow(this);

        //Neue TextViews erstellen mit den jeweiligen Punktzaheln
        TextView tv0 = new TextView(this);
        TextView tv1 = new TextView(this);
        TextView tv2 = new TextView(this);
        TextView tv3 = new TextView(this);
        //Bei bockrunden anzahl verdoppeln
        if(countBockRounds > 0){
            points0 *= 2;
            points1 *= 2;
            points2 *= 2;
            points3 *= 2;
            tv0.setTextColor(Color.GREEN);
            tv1.setTextColor(Color.GREEN);
            tv2.setTextColor(Color.GREEN);
            tv3.setTextColor(Color.GREEN);
        }
        tv0.setText(String.valueOf(points0));
        tv1.setText(String.valueOf(points1));
        tv2.setText(String.valueOf(points2));
        tv3.setText(String.valueOf(points3));

        tv0.setTextSize(24);
        tv1.setTextSize(24);
        tv2.setTextSize(24);
        tv3.setTextSize(24);

        //LayoutParamy spezifizieren damit alle Texte die gleiche breite haben
        TableRow.LayoutParams params = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;

        tv0.setLayoutParams(params);
        tv1.setLayoutParams(params);
        tv2.setLayoutParams(params);
        tv3.setLayoutParams(params);

        tv0.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        //Config für rundenZähler
        TextView roundCount = new TextView(this);
        roundCount.setText(String.valueOf(roundNr));
        roundCount.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        roundCount.setTextSize(20);
        roundCount.setTextColor(Color.BLACK);
        roundCount.setPadding(4,0,0,0);

        toAdd.addView(roundCount);
        toAdd.addView(tv0);
        toAdd.addView(tv1);
        toAdd.addView(tv2);
        toAdd.addView(tv3);

        statsTable.addView(toAdd);
    }

    //Für Toolbar damit beim drücken vom Backbutton zurückgesprungen wird
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
