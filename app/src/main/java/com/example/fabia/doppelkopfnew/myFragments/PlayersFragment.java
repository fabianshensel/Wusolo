package com.example.fabia.doppelkopfnew.myFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

import com.example.fabia.doppelkopfnew.AddPlayerActivity;
import com.example.fabia.doppelkopfnew.PictureStorageHelper;
import com.example.fabia.doppelkopfnew.Player;
import com.example.fabia.doppelkopfnew.PlayerController;
import com.example.fabia.doppelkopfnew.PlayerProfilActivity;
import com.example.fabia.doppelkopfnew.R;

import java.io.File;
import java.util.ArrayList;

public class PlayersFragment extends Fragment {

    private LinearLayout linLayout;
    private PlayerController pControl;
    private Context c;

    private int NEW_PLAYER_ADDED = 1;

    public void setContext(Context c){
        this.c = c;
    }


    /*
    Erstellt für Player aus playerList jeweils einen Button welcher zur ProfilActivity verlinkt
     */
    private void displayPlayer(){
        for(int i = 0; i < pControl.getPlayerList().size();i++) {

            //fanzy shit damit der button ein wenig netter aussieht
            final Button b = new Button(c);
            b.setText("     " + pControl.getPlayerList().get(i).getName());
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
            //fanzy shit damit der button ein wenig netter aussieht

            //OnClick configurieren, sodass die ProfilActivity die richtigen Daten uebergeben bekommt
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(c, PlayerProfilActivity.class);
                    intent.putExtra("player",pControl.getPlayerList().get(b.getId()));
                    startActivity(intent);
                }
            });

            //Bei gedrückt halten des Buttons löschen Aktion durchführen
            b.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //Abfrage on wirklich gelöscht werden soll
                    AlertDialog.Builder dialog = new AlertDialog.Builder(c);
                    dialog.setTitle("Spieler entfernen");
                    dialog.setMessage("Möchten sie "+ pControl.getPlayerList().get(b.getId()).getName() + " entfernen?");
                    //Wenn gelöscht werden soll dann Spieler aus Liste entfernen,JSON updaten,ListView Updaten
                    dialog.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            //Bild löschen
                            File toDelete = new File(pControl.getPlayerList().get(b.getId()).getImagepath() , pControl.getPlayerList().get(b.getId()).getName() + ".jpg");
                            if(toDelete.exists()){
                                toDelete.delete();
                            }


                            //Spieler aus Liste nehemen und JSON updaten
                            pControl.getPlayerList().remove(b.getId());
                            pControl.getPlayerList().trimToSize();
                            pControl.writeToJSON(c);
                            linLayout.removeAllViews();
                            displayPlayer();

                        }
                    });

                    dialog.setNegativeButton("Nein",null);
                    dialog.show();
                    return true;
                }
            });


            Bitmap bit = PictureStorageHelper.loadImageFromStorage(pControl.getPlayerList().get(i).getImagepath(),pControl.getPlayerList().get(i).getName() + ".jpg");

            BitmapDrawable a = new BitmapDrawable(getResources(),bit);
            a.setBounds(20,0,120,100);
            b.setCompoundDrawables(a,null,null,null);

            //Add button to linear layout
            linLayout.addView(b);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        pControl = new PlayerController(new ArrayList<Player>());
        pControl.readFromJSON(c);
        linLayout.removeAllViews();
        displayPlayer();
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_players,container,false);

        linLayout = v.findViewById(R.id.playerListLinearLayout);
        Button addButton = (Button) v.findViewById(R.id.addPlayerButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(c, AddPlayerActivity.class),1);
            }
        });


        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == NEW_PLAYER_ADDED){
            //Neuen Player anlegen und zum playerController hinzufügen
            pControl.readFromJSON(c);
            linLayout.removeAllViews();
            displayPlayer();
        }

    }
}
