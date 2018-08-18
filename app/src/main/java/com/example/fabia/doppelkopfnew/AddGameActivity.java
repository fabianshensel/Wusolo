package com.example.fabia.doppelkopfnew;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddGameActivity extends AppCompatActivity {

    private PlayerController playerController;
    private GameController gameController;

    private static final int REQUEST_LOCATION = 1;
    private LocationManager locationManager;

    private String latitude;
    private String longitude;

    String strAdd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        //Create Location blabla
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


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
        final Button addPlayer = findViewById(R.id.addGameAddPlayerButton);
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
                Intent intent = new Intent(AddGameActivity.this,AddPlayerActivity.class);
                startActivity(intent);
            }
        });

        //Config CreateGame Button
        createGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gameName = gameNameEditText.getText().toString();
                String address = "unbekannte Adresse";

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

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps();

                } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    getLocation();
                    if (!latitude.isEmpty() && !longitude.isEmpty()) {
                        com.example.fabia.doppelkopfnew.Location location = new com.example.fabia.doppelkopfnew.Location(latitude, longitude,getCompleteAddressString(Double.valueOf(latitude), Double.valueOf(longitude)));
                    }
                }

                if(strAdd != ""){
                    address = strAdd;
                }
                
                Game newGame = new Game(gameName,p1,p2,p3,p4,new ArrayList<RoundStats>(), address, generateDate());
                //gameController.readFromJSON(AddGameActivity.this);
                gameController.getGameList().add(newGame);
                gameController.writeToJSON(AddGameActivity.this);
                finish();

            }
        });


    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(AddGameActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (AddGameActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(AddGameActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            android.location.Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            android.location.Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                latitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

            } else  if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                latitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

            } else  if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                latitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

            }else{

                Toast.makeText(this,"Unble to Trace your location",Toast.LENGTH_SHORT).show();

            }
        }
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current loction address", strReturnedAddress.toString());
            } else {
                Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }



    private String generateDate(){
        DateTimeFormatter dtf;
        LocalDateTime now;
        now = LocalDateTime.now();
        dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dtf.format(now);
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
