package com.example.fabia.doppelkopfnew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PlayerProfil extends AppCompatActivity {

    public Player myPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profil);

        myPlayer = new Player("Torben Glass","Ich bin ein geiler Ficker",null,new PlayerStats(10));

        TextView nameView = (TextView) findViewById(R.id.playerName);
        TextView commentView = (TextView) findViewById(R.id.playerComment);
        TextView winView = (TextView) findViewById(R.id.playerWinCount);


        nameView.setText(myPlayer.getName());
        commentView.setText(myPlayer.getComment());
        winView.setText(winView.getText() + String.valueOf(myPlayer.getStats().getWinCount()));
    }
}
