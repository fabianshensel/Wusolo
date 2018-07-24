package com.example.fabia.doppelkopfnew.myFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.fabia.doppelkopfnew.AddGameActivity;
import com.example.fabia.doppelkopfnew.Game;
import com.example.fabia.doppelkopfnew.GameController;
import com.example.fabia.doppelkopfnew.R;

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
            Button b = new Button(c);
            b.setText(gameController.getGameList().get(i).getName());
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
