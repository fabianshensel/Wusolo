package com.example.fabia.doppelkopfnew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LayoutDirection;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Create Backbutton on Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Get Selected Game
        GameController gameController = new GameController(new ArrayList<Game>());
        gameController.readFromJSON(GameActivity.this);
        game = gameController.getGamebyName(getIntent().getStringExtra("name"));

        //Display GameName
        TextView nameView = findViewById(R.id.GameNameTextView);
        nameView.setText(game.getName());

        //Display PlayerNames
        TextView p0name = findViewById(R.id.player0TV);
        TextView p1name = findViewById(R.id.player1TV);
        TextView p2name = findViewById(R.id.player2TV);
        TextView p3name = findViewById(R.id.player3TV);
        p0name.setText(game.getPlayer0().getName());
        p1name.setText(game.getPlayer1().getName());
        p2name.setText(game.getPlayer2().getName());
        p3name.setText(game.getPlayer3().getName());

        //Get Enter Button
        Button enterButton = findViewById(R.id.enterPointsButton);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText numberInput = findViewById(R.id.gamePointsEditText);
                String input = numberInput.getText().toString();
                if(input.isEmpty()){
                    Toast.makeText(GameActivity.this, "Bitte gebe eine Zahl ein", Toast.LENGTH_SHORT).show();
                    return;
                }

                int number = Integer.valueOf(input);
                insertStats(number,-number,number,-number);
            }
        });






    }

    private void insertStats(int points0,int points1, int points2, int points3){
        //StatsTable holen in den inserted wird
        TableLayout statsTable = findViewById(R.id.gameStatsTableLayout);

        //Neue TableRow erstellen welche dem Table hinzugefügt wird
        TableRow toAdd = new TableRow(this);

        //Neue TextViews erstellen mit den jeweiligen Punktzaheln
        TextView tv0 = new TextView(this);
        TextView tv1 = new TextView(this);
        TextView tv2 = new TextView(this);
        TextView tv3 = new TextView(this);
        tv0.setText(String.valueOf(points0));
        tv1.setText(String.valueOf(points1));
        tv2.setText(String.valueOf(points2));
        tv3.setText(String.valueOf(points3));

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

        toAdd.addView(tv0);
        toAdd.addView(tv1);
        toAdd.addView(tv2);
        toAdd.addView(tv3);

        statsTable.addView(toAdd);
    }

    //Für Toolbar damit beim drücken vom Backbutton zurückgesprungen wird
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
