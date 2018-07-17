package com.example.fabia.doppelkopfnew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PlayerProfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profil);

        //Dummy Player to Display
        Player toDisplay = new Player("Max Mustermann","Hier ist ein Beispiel Text für das Kommentarfeld",null,new PlayerStats(40,30));

        //Get Views
        TextView nameTV = (TextView)findViewById(R.id.profilPlayerNameTextView);
        TextView commentTV = (TextView)findViewById(R.id.profilPlayerCommentTextView);
        TextView winsTV = (TextView)findViewById(R.id.profilWinsTextView);
        TextView losesTV = (TextView)findViewById(R.id.profilLosesTextView);


        //Set Views to display Player
        nameTV.setText(toDisplay.getName());
        commentTV.setText(toDisplay.getComment());
        winsTV.setText( getString(R.string.wins) + ": " + toDisplay.getStats().getWinCount());
        losesTV.setText(getString(R.string.loss) + ": " + toDisplay.getStats().getLossCount());


    }
}
