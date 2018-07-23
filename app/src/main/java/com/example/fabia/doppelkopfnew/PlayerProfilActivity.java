package com.example.fabia.doppelkopfnew;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayerProfilActivity extends AppCompatActivity {

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
        winsTV.setText( getString(R.string.wins) + ": " + toDisplay.getStats().getWinCount());
        losesTV.setText(getString(R.string.loss) + ": " + toDisplay.getStats().getLossCount());

        Bitmap b = PictureStorageHelper.loadImageFromStorage(toDisplay.getImagepath() , toDisplay.getName() + ".jpg");
        if(b != null){
            imageView.setImageBitmap(b);
        }



    }

    //Für Toolbar damit beim drücken vom Backbutton zurückgesprungen wird
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
