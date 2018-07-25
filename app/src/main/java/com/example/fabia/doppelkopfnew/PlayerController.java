package com.example.fabia.doppelkopfnew;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class PlayerController {
    private ArrayList<Player> playerList;

    public PlayerController(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    //Checks if Name name is in List of Players
    public boolean isInList(String name){
        for(int i = 0; i < playerList.size();i++){
            if(playerList.get(i).getName().equals(name)){
                return true;
            }
        }
        return false;

    }

    /*
    might return null if Player not in List
     */
    public Player getPlayerbyName(String name){
        for(int i = 0; i < playerList.size();i++){
            if(playerList.get(i).getName().equals(name)){
                return  playerList.get(i);
            }
        }
        return null;
    }



    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    /*
    schreibt momentanen Inhalt von playerList in Players.txt
     */
    public void writeToJSON(Context c){
        try{
            //Directory erstellen
            File dir = new File(c.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),"myFiles");

            if (!dir.exists()) {
                dir.mkdirs();
            }
            File myFile = new File(c.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)+"/myFiles/Players.txt");

            FileOutputStream fout = new FileOutputStream(myFile);
            OutputStreamWriter outWriter = new OutputStreamWriter(fout);

            JSONArray jsonArray = new JSONArray();
            for(int i = 0; i < playerList.size();i++){
                JSONObject obj = new JSONObject();
                Player p = playerList.get(i);
                obj.put("name",p.getName());
                obj.put("comment",p.getComment());
                obj.put("path",p.getImagepath());
                jsonArray.put(obj);
            }
            outWriter.append(jsonArray.toString());
            outWriter.close();
            fout.close();
        }

        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    /*
    ließt von der JSONDatei nach playerList die Spieler ein wenn keine File vorhanden wird fillList() aufgerufen
     */
    public void readFromJSON(Context c){
        File dir = new File(c.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),"myFiles");

        if (!dir.exists()) {
            dir.mkdirs();
            return;
        }

        playerList = new ArrayList<>();
        String fileName = "/myFiles/Players.txt";
        String input = "";

        try{
            File myFile = new File(c.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)+ fileName);

            FileInputStream fin = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(new InputStreamReader(fin, StandardCharsets.UTF_8.name()));

            String line;

            while((line = myReader.readLine())!= null){
                input += line;
            }

            JSONArray jsonArray = new JSONArray(input);

            for(int i = 0; i < jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                String name = obj.getString("name");
                String comment = obj.getString("comment");
                String path = obj.getString("path");


                Player p = new Player(name,comment,path);
                playerList.add(p);
            }
        }

        catch (Exception e){
            Log.e("Exp",e.getMessage());

        }
    }
}
