package com.example.fabia.doppelkopfnew.myFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.fabia.doppelkopfnew.AddGameActivity;
import com.example.fabia.doppelkopfnew.Game;
import com.example.fabia.doppelkopfnew.GameActivity;
import com.example.fabia.doppelkopfnew.GameController;
import com.example.fabia.doppelkopfnew.R;

import java.io.File;
import java.util.ArrayList;

public class GameListFragment extends Fragment {
    private GameController gameController;
    private LinearLayout linearLayout;
    private Context c;

    public void setContext(Context c) {
        this.c = c;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gamelist,container,false);

        linearLayout = v.findViewById(R.id.gameListLinearLayout);
        Button addbtn = v.findViewById(R.id.addGameButton);

        //Config AddButton to start new Activity
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(c, AddGameActivity.class));
            }
        });

        return v;
    }

    private void displayGames(){
        for(int i = 0 ; i < gameController.getGameList().size(); i++){
            final Button b = new Button(c);
            b.setText(gameController.getGameList().get(i).getName());
            b.setId(i);
            b.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            b.setStateListAnimator(null);
            b.setBackgroundColor(getResources().getColor(R.color.coloGrey));
            b.setElevation(3.0f);
            b.setTextSize(30);
            b.setAllCaps(false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,10,10,10);
            b.setGravity(Gravity.LEFT);
            b.setLayoutParams(params);
            b.setPadding(0,50,0,50);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(c, GameActivity.class);
                    intent.putExtra("name",gameController.getGameList().get(b.getId()).getName());
                    startActivity(intent);
                }
            });

            b.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //Abfrage on wirklich gelöscht werden soll
                    AlertDialog.Builder dialog = new AlertDialog.Builder(c);
                    dialog.setTitle("Spiel entfernen");
                    dialog.setMessage("Möchten sie "+ gameController.getGameList().get(b.getId()).getName() + " entfernen?");
                    //Wenn gelöscht werden soll dann Spieler aus Liste entfernen,JSON updaten,ListView Updaten
                    dialog.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) { ;

                            //Spieler aus Liste nehemen und JSON updaten
                            gameController.getGameList().remove(b.getId());
                            gameController.getGameList().trimToSize();
                            gameController.writeToJSON(c);
                            linearLayout.removeAllViews();
                            displayGames();

                        }
                    });

                    dialog.setNegativeButton("Nein",null);
                    dialog.show();
                    return true;
                }
            });

            linearLayout.addView(b);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        gameController = new GameController(new ArrayList<Game>());
        gameController.readFromJSON(c);
        linearLayout.removeAllViews();
        displayGames();
    }

    @Override
    public void onResume() {
        super.onResume();
        gameController.readFromJSON(c);
        linearLayout.removeAllViews();
        displayGames();
    }
}
