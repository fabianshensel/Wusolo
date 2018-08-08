package com.example.fabia.doppelkopfnew;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

public class GameInfoActivity extends AppCompatActivity {
    private Game game;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.game_info);
        //Create Backbutton on Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Spiel Info");

        //get Game from Intent
        game = (Game) getIntent().getParcelableExtra("Game");

        super.onCreate(savedInstanceState);
    }

    public void displayInfo(){

        TextView textViewMaxPoints = (TextView)findViewById(R.id.pointsOfWinner);
        TextView textViewMinPoints= (TextView)findViewById(R.id.pointsOfLoser);
/*
        TextView textViewPlayer0= (TextView)findViewById(R.id.pointsOfWinner);
        TextView textViewPlayer1= (TextView)findViewById(R.id.pointsOfWinner);
        TextView textViewPlayer2= (TextView)findViewById(R.id.pointsOfWinner);
        TextView textViewPlayer3= (TextView)findViewById(R.id.pointsOfWinner);
*/
        TextView textViewWinner= (TextView)findViewById(R.id.nameOfWinner);
        TextView textViewLoser= (TextView)findViewById(R.id.nameOfLoser);
        TextView textViewStartOfGame= (TextView)findViewById(R.id.nameOfPlace);

        textViewMaxPoints.setText(game.getGewinnPunktZahl());
        textViewMinPoints.setText(game.getVerlorenPunktzahl());
/*
        textViewPlayer0.setText(game.getPlayer0().getName());
        textViewPlayer1.setText(game.getPlayer1().getName());
        textViewPlayer2.setText(game.getPlayer2().getName());
        textViewPlayer3.setText(game.getPlayer3().getName());
*/
        textViewWinner.setText(game.getWinner().getName());
        textViewLoser.setText(game.getLoser().getName());

        textViewStartOfGame.setText(game.getDate().toString());
    }


    //Für Toolbar damit beim drücken vom Backbutton zurückgesprungen wird
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
