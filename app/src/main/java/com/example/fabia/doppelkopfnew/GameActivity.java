package com.example.fabia.doppelkopfnew;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.LayoutDirection;
import android.util.Log;
import android.util.Xml;
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

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private Game game;
    private GameController gameController;

    //defines how long BockRounds last
    public static int BOCK_ROUND_COUNT = 4;
    //defines how much BockRounds increase Score
    public static int BOCK_ROUND_MULTIPLIER = 1;
    //defines how much SoloRounds increase Score for Winner
    public static int SOLO_ROUND_MULTIPLIER = 3;
    //defines min Value for NumberPicker
    public static int MIN_VALUE_NUMBERPICKER = -50;
    //defines max Value for NumberPicker
    public static int MAX_VALUE_NUMBERPICKER = 50;


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

        //When Game dirty Activity -> finish Activity and display Toast
        if(GameController.isGameDirty(game)){
            Toast.makeText(GameActivity.this, "Fehlerhaftes Spiel", Toast.LENGTH_SHORT).show();
            this.finish();
            return;
        }


        //Display GameName
        TextView nameView = findViewById(R.id.GameNameTextView);
        nameView.setText(game.getName());

        //Display PlayerNames
        displayPlayerNames();

        //Init ScoreViews
        setScoreViews(0,0,0,0);

        //Wenn bereits Stats vorhanden dann anzeigen
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
                        int anzSelectedPlayers = 0;
                        for(int j = 0; j < 4; j++){
                            if(checkedItems[j]){
                                anzSelectedPlayers++;
                            }
                        }
                        //Wenn nicht 1 oder 2 Spieler ausgewählt sind Toast ausgeben und return
                        if(anzSelectedPlayers != 1 && anzSelectedPlayers != 2){
                            Toast.makeText(GameActivity.this, "Es können nur 1 oder 2 Spieler gewinnen", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //Wenn Auswahl ok Dialog schließen
                        winnerAlertDialog.dismiss();

                        //Neuen Dialog zum Wählen der Punktzahl Builden
                        final AlertDialog.Builder numberPickerDialogBuilder = new AlertDialog.Builder(GameActivity.this);

                        //NumberPicker erstellen um Auswahl zu ermöglichen
                        final NumberPicker numberPicker = new NumberPicker(GameActivity.this);
                        numberPicker.setMaxValue(0);

                        numberPicker.setMaxValue(MAX_VALUE_NUMBERPICKER-MIN_VALUE_NUMBERPICKER);
                        numberPicker.setValue(0-MIN_VALUE_NUMBERPICKER);
                        numberPicker.setFormatter(new NumberPicker.Formatter() {
                            @Override
                            public String format(int index) {
                                return Integer.toString(index + MIN_VALUE_NUMBERPICKER);
                            }
                        });
                        
                        //NumberPicker als Dialog setzen
                        numberPickerDialogBuilder.setView(numberPicker);
                        
                        //Abbruch Button hinzufügen
                        numberPickerDialogBuilder.setNegativeButton("Abbruch",null);
                        
                        //Cofig Button zum Eintagen der Punkte
                        numberPickerDialogBuilder.setPositiveButton("Eintragen",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //Hilfsboolean da erste Runde Böcke übersprungen werden soll
                                boolean isNewBoecke = false;

                                //Ausgewählte anzahl an Punkten holen und für Verlierer Anzahl Punkte invertieren
                                int points = numberPicker.getValue() + MIN_VALUE_NUMBERPICKER;
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

                                //*****BÖCKE BÖCKE BÖCKE***********
                                if(checkedItems[4]){
                                    if(!game.getRoundStats().isEmpty()){

                                    } else if(game.getRoundStats().get(game.getRoundStats().size()-1).getBockRoundsleft() == 0){
                                        isNewBoecke = true;
                                    }
                                    bock += BOCK_ROUND_COUNT;
                                    Log.d("Böcke","Böcke resettet");

                                }else{
                                    isNewBoecke = false;
                                    if(!game.getRoundStats().isEmpty()){
                                        //Wenn die anz an BockRoundsleft der vorherigen runde > 0 ist, dann -1
                                        if(game.getRoundStats().get(game.getRoundStats().size()-1).getBockRoundsleft() > 0){
                                            bock = game.getRoundStats().get(game.getRoundStats().size()-1).getBockRoundsleft() - 1;
                                            Log.d("Böcke","Böcke--: " + bock);
                                        }
                                    }

                                }

                                //Anzeige vom Punktestand anpassen wenn bockrunde
                                if(bock > 0){
                                    p0 *= BOCK_ROUND_MULTIPLIER;
                                    p1 *= BOCK_ROUND_MULTIPLIER;
                                    p2 *= BOCK_ROUND_MULTIPLIER;
                                    p3 *= BOCK_ROUND_MULTIPLIER;
                                }
                                //END********* BÖCKE BÖCKE BÖCKE**********


                                //******* SOLO SOLO SOLO ********
                                if(checkForSoloWin(checkedItems)){
                                    if(checkedItems[0]){
                                        p0 *= SOLO_ROUND_MULTIPLIER;
                                    }
                                    if(checkedItems[1]){
                                        p1 *= SOLO_ROUND_MULTIPLIER;
                                    }
                                    if(checkedItems[2]){
                                        p2 *= SOLO_ROUND_MULTIPLIER;
                                    }
                                    if(checkedItems[3]){
                                        p3 *= SOLO_ROUND_MULTIPLIER;
                                    }
                                }
                                //END ********* SOLO SOLO SOLO *******

                                insertStats(p0,p1,p2,p3,bock,game.getRoundStats().size(), isNewBoecke);
                                updateScore(p0,p1,p2,p3);
                                
                                //RoundStats Objekt erstellen und in Liste vom Game hinzufügen

                                RoundStats thisRound = new RoundStats(checkedItems[0],checkedItems[1],checkedItems[2],checkedItems[3],checkForSoloWin(checkedItems),points,bock,isNewBoecke);

                                game.getRoundStats().add(thisRound);

                            }
                        });
                        
                        //Dialog anzeigen
                        numberPickerDialogBuilder.show();
                    }
                });


                


            }
        });

        //-----ENDE DIALOG ABLAUF FÜR EINGABE VON SPIELDATEN----






    }


    //Wird aufgerufen wenn bereits stats für das Game existieren
    //muss sich selbst um berechnung kümmern von wer +/- bekommt
    //multiplikatoren für bock und solorunden anwenden
    private void displayStats(){
        for(int i = 0 ; i < game.getRoundStats().size();i++){

            RoundStats currStats = game.getRoundStats().get(i);
            boolean isbock = false;
            boolean isNewBock = false;

            int points0 = currStats.getPoints();
            int points1 = currStats.getPoints();
            int points2 = currStats.getPoints();
            int points3 = currStats.getPoints();

            if(!currStats.isIsplayer0win()){
                points0 = -points0;
            }
            if(!currStats.isIsplayer1win()){
                points1 = -points1;
            }
            if(!currStats.isIsplayer2win()){
                points2 = -points2;
            }
            if(!currStats.isIsplayer3win()){
                points3 = -points3;
            }

            //SOLOS
            if(currStats.isSoloWin()){
                if(currStats.isIsplayer0win()){
                    points0 *= SOLO_ROUND_MULTIPLIER;
                }
                if(currStats.isIsplayer1win()){
                    points1 *= SOLO_ROUND_MULTIPLIER;
                }
                if(currStats.isIsplayer2win()){
                    points2 *= SOLO_ROUND_MULTIPLIER;
                }
                if(currStats.isIsplayer3win()){
                    points3 *= SOLO_ROUND_MULTIPLIER;
                }
            }
            //checkt ob letzte Runde eine erste neue Bockrunde war
            isNewBock = currStats.isNewBoecke();



            insertStats(points0,points1,points2,points3,currStats.getBockRoundsleft(),i+1, isNewBock);
            updateScore(points0,points1,points2,points3);
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
    rechnet punkte einfach auf momentanen stand drauf
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

    /*
    fügt pointsX in tabelle ein
    makiert bockrunden gelb
    schreibt rundenNr vor Zeile
     */
    private void insertStats(int points0,int points1, int points2, int points3, int countBockRounds,int roundNr, boolean isBockNew){


        //StatsTable holen in den inserted wird
        TableLayout statsTable = findViewById(R.id.gameStatsTableLayout);

        //Neue TableRow erstellen welche dem Table hinzugefügt wird
        TableRow toAdd = new TableRow(this);

        //Neue TextViews erstellen
        TextView tv0 = new TextView(this);
        TextView tv1 = new TextView(this);
        TextView tv2 = new TextView(this);
        TextView tv3 = new TextView(this);

        //Bei bockrunden Text gelb färben
        if(countBockRounds > 0 && !isBockNew){
            tv0.setTextColor(ContextCompat.getColor(this,R.color.yellowGoldy));
            tv1.setTextColor(ContextCompat.getColor(this,R.color.yellowGoldy));
            tv2.setTextColor(ContextCompat.getColor(this,R.color.yellowGoldy));
            tv3.setTextColor(ContextCompat.getColor(this,R.color.yellowGoldy));
        }

        //Punktestand setzten
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


    /*
    Displays Names of Players with game.getPlayerX().getName()
     */
    private void displayPlayerNames(){
        TextView p0name = findViewById(R.id.player0TV);
        TextView p1name = findViewById(R.id.player1TV);
        TextView p2name = findViewById(R.id.player2TV);
        TextView p3name = findViewById(R.id.player3TV);

        p0name.setText(game.getPlayer0().getName());
        p1name.setText(game.getPlayer1().getName());
        p2name.setText(game.getPlayer2().getName());
        p3name.setText(game.getPlayer3().getName());
    }

    /*
    sets the score views to given numbers
     */
    private void setScoreViews(int score0, int score1, int score2, int score3){
        TextView score0TV = findViewById(R.id.score0TextView);
        TextView score1TV = findViewById(R.id.score1TextView);
        TextView score2TV = findViewById(R.id.score2TextView);
        TextView score3TV = findViewById(R.id.score3TextView);

        score0TV.setText(String.valueOf(score0));
        score1TV.setText(String.valueOf(score1));
        score2TV.setText(String.valueOf(score2));
        score3TV.setText(String.valueOf(score3));
    }
    
    //Wenn Activity beendet wird und Game nicht Dirty ist, dann abspeichern in JSON
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Nur schreiben wenn Game nicht dirty
        if(!GameController.isGameDirty(game)){
            gameController.writeToJSON(GameActivity.this);
        }

    }
    
    //Für Toolbar damit beim drücken vom Backbutton zurückgesprungen wird
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
