package com.example.fabia.doppelkopfnew;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class PlayerListActivity extends AppCompatActivity {

    ArrayList<Player> playerList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);


        fillList();

    }


    private void fillList(){
        playerList.add(new Player("Fabian Hensel","Ich spiele super gerne Doppelkopf",null,new PlayerStats(1,99)));
        playerList.add(new Player("Torben Glass","Halt dein Maul",null,new PlayerStats(99,1)));
        playerList.add(new Player("Jeremy Tuller","Ich hab dann mal Recht studiert",null,new PlayerStats(0,0)));
        playerList.add(new Player("Simon Wusolo","Wo ist die Milch hin?",null,new PlayerStats(99,99)));
    }

}
