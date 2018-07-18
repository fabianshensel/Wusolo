package com.example.fabia.doppelkopfnew.myFragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.fabia.doppelkopfnew.MainActivity;
import com.example.fabia.doppelkopfnew.Player;
import com.example.fabia.doppelkopfnew.PlayerProfilActivity;
import com.example.fabia.doppelkopfnew.R;

import java.util.ArrayList;

public class PlayersFragment extends Fragment {

    private LinearLayout linLayout;
    private ArrayList<Player> playerList;
    private Context c;

    public void setContext(Context c){
        this.c = c;
    }

    public void setPlayerList(ArrayList<Player> pList){
        this.playerList = pList;
    }

    private void displayPlayer(){
        for(int i = 0; i < playerList.size();i++) {
            final Button b = new Button(c);
            b.setText("     " + playerList.get(i).getName());
            b.setId(i);
            b.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            b.setStateListAnimator(null);
            b.setBackgroundColor(Color.GREEN);
            b.setTextSize(30);
            b.setAllCaps(false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200);
            params.setMargins(10,10,10,10);
            b.setLayoutParams(params);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(c, PlayerProfilActivity.class);
                    intent.putExtra("player",playerList.get(b.getId()));
                    startActivity(intent);
                }
            });
            linLayout.addView(b);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        linLayout.removeAllViews();
        displayPlayer();

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_players,container,false);

        linLayout = v.findViewById(R.id.playerListLinearLayout);

        return v;
    }

}
