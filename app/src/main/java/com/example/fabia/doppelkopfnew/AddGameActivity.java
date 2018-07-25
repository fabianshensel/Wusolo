package com.example.fabia.doppelkopfnew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddGameActivity extends AppCompatActivity {

    private PlayerController playerController;
    private GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        //Create Backbutton on Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Spieler holen
        playerController = new PlayerController(new ArrayList<Player>());
        playerController.readFromJSON(this);
        //Game Controller holen
        gameController = new GameController(new ArrayList<Game>());
        gameController.readFromJSON(AddGameActivity.this);

        //Views holen
        final Spinner player1Spinner = findViewById(R.id.player1Spinner);
        Spinner player2Spinner = findViewById(R.id.player2Spinner);
        Spinner player3Spinner = findViewById(R.id.player3Spinner);
        Spinner player4Spinner = findViewById(R.id.player4Spinner);
        Button addPlayer = findViewById(R.id.addGameAddPlayerButton);
        Button createGame = findViewById(R.id.createGameButton);
        final EditText gameNameEditText = findViewById(R.id.gameNameEditText);

        //ArrayAdapter mit Spieler füllen
        ArrayAdapter<Player> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,playerController.getPlayerList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Spinner füllen
        player1Spinner.setAdapter(adapter);
        player2Spinner.setAdapter(adapter);
        player3Spinner.setAdapter(adapter);
        player4Spinner.setAdapter(adapter);

        //Config AddPlayerButton
        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddGameActivity.this,AddPlayerActivity.class));
            }
        });

        //Config CreateGame Button
        createGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gameName = gameNameEditText.getText().toString();

                if(gameName.isEmpty()){
                    Toast.makeText(AddGameActivity.this, "Bitte Spielnamen eintragen",Toast.LENGTH_LONG).show();
                    return;
                }
                if(gameController.isInList(gameName)){
                    Toast.makeText(AddGameActivity.this, "Spielname bereits in Verwendung",Toast.LENGTH_LONG).show();
                    return;
                }



                Spinner player1Spinner = findViewById(R.id.player1Spinner);
                Spinner player2Spinner = findViewById(R.id.player2Spinner);
                Spinner player3Spinner = findViewById(R.id.player3Spinner);
                Spinner player4Spinner = findViewById(R.id.player4Spinner);

                Player p1 = (Player) player1Spinner.getSelectedItem();
                Player p2 = (Player) player2Spinner.getSelectedItem();
                Player p3 = (Player) player3Spinner.getSelectedItem();
                Player p4 = (Player) player4Spinner.getSelectedItem();
                //Check if 2 Players are the same
                if(p1.equals(p2) || p1.equals(p3) || p1.equals(p4) || p2.equals(p3) || p2.equals(p4) || p3.equals(p4)){
                    Toast.makeText(AddGameActivity.this, "Es müssen verschiedene Spieler ausgewählt werden",Toast.LENGTH_LONG).show();
                    return;
                }

                Game newGame = new Game(gameName,p1,p2,p3,p4,new ArrayList<RoundStats>());
                //gameController.readFromJSON(AddGameActivity.this);
                gameController.getGameList().add(newGame);
                gameController.writeToJSON(AddGameActivity.this);
                finish();

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //Wenn z.B. neuer Spieler hinzugefügt wird Spinner updaten damit der auch angezeigt wird
        playerController.readFromJSON(this);

        //Views holen
        Spinner player1Spinner = findViewById(R.id.player1Spinner);
        Spinner player2Spinner = findViewById(R.id.player2Spinner);
        Spinner player3Spinner = findViewById(R.id.player3Spinner);
        Spinner player4Spinner = findViewById(R.id.player4Spinner);

        //ArrayAdapter mit Spieler füllen
        ArrayAdapter<Player> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,playerController.getPlayerList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Spinner füllen
        player1Spinner.setAdapter(adapter);
        player2Spinner.setAdapter(adapter);
        player3Spinner.setAdapter(adapter);
        player4Spinner.setAdapter(adapter);
    }

    //Für Toolbar damit beim drücken vom Backbutton zurückgesprungen wird
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
