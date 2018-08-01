package com.example.fabia.doppelkopfnew;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.base.Predicate;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FirebaseHelper {

    //DEBUG TAG
    private static final String TAG = "FirebaseHelper";

    private static final String KEY_NAME = "name";
    private static final String KEY_COMMENT = "comment";
    private static final String KEY_PICTURE_PATH = "imagepath";

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static CollectionReference playerCollection = db.collection("Players");

    //schreibt alle Player aus playerList in Datenbank außer Player die bereits mit Namen in DB existieren
    public static void storePlayers(final ArrayList<Player> playerList, final Context c) {

        final ArrayList<String> dbList = new ArrayList<>();

        playerCollection.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        //In dbList alle Namen schreiben von Spielern die bereits in Datenbank vorhanden
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            Player p = doc.toObject(Player.class);
                            if (!dbList.contains(p.getName())) {
                                dbList.add(p.getName());
                            }
                        }

                        //Aus der playerList alle Spieler entfernen die bereits in Datenbank existieren
                        for (Iterator<Player> iterator = playerList.iterator(); iterator.hasNext(); ) {
                            Player value = iterator.next();
                            if (dbList.contains(value.getName())) {
                                iterator.remove();
                            }
                        }
                        //Liste zuschneiden
                        playerList.trimToSize();

                        //Für jeden Spieler ein Dokument in der Players Collection anlegen
                        for (int i = 0; i < playerList.size(); i++) {
                            Player p = playerList.get(i);

                            playerCollection.add(p)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d(TAG, "Player saved");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, e.toString());
                                        }
                                    }); //end of db transaction
                        } //end of for-loop
                        Toast.makeText(c, "Es wurden " + playerList.size() + " Spieler hinzugefügt", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(c, "Upload fehlgeschlagen", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                        throw new RuntimeException();
                    }
                });

    }

    public static void getPlayers(final Context c) {

        final ArrayList<Player> players = new ArrayList<>();

        //Alle Player aus Datenbank holen
        playerCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                //Aus jedem Document ein Player Objekt erstellen
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    Player p = doc.toObject(Player.class);
                    players.add(p);
                }
                PlayerController playerController = new PlayerController(new ArrayList<Player>());
                playerController.mergeWithList(players,c);
                Toast.makeText(c, "Es wurden " + players.size() + " Spieler heruntergeladen", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(c, "Herunterladen fehlgeschlagen", Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.toString());
            }
        });
    }


    //Copy Pasta aus StackOverflow
    public static boolean isNetworkAvailable(Context con) {
        try {
            ConnectivityManager cm = (ConnectivityManager) con
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
