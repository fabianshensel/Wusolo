package com.example.fabia.doppelkopfnew;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddPlayerActivity extends AppCompatActivity {

    private String pPath;
    private Bitmap bitmap;

    private PlayerController playerController;
    private int IMAGE_REQUEST = 1;
    private int CAMERA_REQUEST = 2;
    private int NEW_PLAYER_ADDED = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        //Create Backbutton on Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        playerController = new PlayerController(new ArrayList<Player>());
        playerController.readFromJSON(this);

        //Get Views
        final EditText nameText = findViewById(R.id.playerNameEditText);
        final EditText commentText = findViewById(R.id.playerCommentEditText);
        Button addBtn = findViewById(R.id.playerHinzufuegenButton);
        ImageButton imageButton = findViewById(R.id.playerAddImageButton);
        ImageButton cameraImagebtn = findViewById(R.id.playerAddCameraImageButton);
        final ImageButton defaultMan = findViewById(R.id.deafultManImageButton);
        final ImageButton defaultGirl = findViewById(R.id.deafultGirlImageButton);

        //defaultMan Bitmap als default festlegen
        defaultMan.setBackground(getDrawable(R.drawable.selected_round_background));

        //Congif defaultGirl Button
        defaultGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = ((BitmapDrawable)defaultGirl.getDrawable()).getBitmap();
                defaultGirl.setBackground(getDrawable(R.drawable.selected_round_background));
                defaultMan.setBackgroundColor(Color.TRANSPARENT);
            }
        });

        //Config defaultMan button
        defaultMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = ((BitmapDrawable)defaultMan.getDrawable()).getBitmap();
                defaultMan.setBackground(getDrawable(R.drawable.selected_round_background));
                defaultGirl.setBackgroundColor(Color.TRANSPARENT);
            }
        });

        //Config Camera Button
        cameraImagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, CAMERA_REQUEST);
            }
        });

        //Config Image Button
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Photo Auswahl starten und Photo über onActivityResult holen
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, IMAGE_REQUEST);
            }
        });

        //Config Add Button
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(AddPlayerActivity.this, "Bitte Namen eingaben",Toast.LENGTH_LONG).show();
                    return;
                }
                if(playerController.isInList(name)){
                    Toast.makeText(AddPlayerActivity.this, "Es gibt bereits einen Spieler mit diesem Namen",Toast.LENGTH_LONG).show();
                    return;
                }

                String comment = commentText.getText().toString();
                if(comment.isEmpty()){
                    comment = "Ich bin unkreativ und brauche einen Beispieltext.";
                }

                //Bild speichern wenn name unique damit nicht Bilder ueberschrieben werden
                if(!playerController.isInList(name)){
                    if(bitmap != null) {
                        pPath = PictureStorageHelper.saveToInternalStorage(bitmap, AddPlayerActivity.this, name + ".jpg");
                    }else{
                        pPath = "noPath";
                    }
                }

                //Neuen Spieler erstellen und in JSON-Datei abspeichern
                playerController.getPlayerList().add(new Player(name,comment,null,new PlayerStats(0,0),pPath));
                playerController.writeToJSON(AddPlayerActivity.this);

                setResult(NEW_PLAYER_ADDED);
                finish();
            }
        });

    }


    //Für Toolbar damit beim drücken vom Backbutton zurückgesprungen wird
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }




    //Result von PhotoPicker holen in Attribut bitmap speichern noch nicht in Speicher von Handy speichern
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if(reqCode == IMAGE_REQUEST){
            if (resultCode == RESULT_OK) try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(imageStream);

                //Auswahlanzeige zurücksetzen
                ImageButton defaultMan = findViewById(R.id.deafultManImageButton);
                ImageButton defaultGirl = findViewById(R.id.deafultGirlImageButton);
                defaultMan.setBackgroundColor(Color.TRANSPARENT);
                defaultGirl.setBackgroundColor(Color.TRANSPARENT);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(AddPlayerActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(AddPlayerActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
            }
        }

        if(reqCode == CAMERA_REQUEST){
            if(resultCode == RESULT_OK){

                    bitmap = (Bitmap) data.getExtras().get("data");

                //Auswahlanzeige zurücksetzen
                ImageButton defaultMan = findViewById(R.id.deafultManImageButton);
                ImageButton defaultGirl = findViewById(R.id.deafultGirlImageButton);
                defaultMan.setBackgroundColor(Color.TRANSPARENT);
                defaultGirl.setBackgroundColor(Color.TRANSPARENT);


            }
        }

    }
}
