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

public class GameController {
    private ArrayList<Game> gameList;

    //Checks if there is a Game with the same Name in the List
    public boolean isInList(String name){
        for(int i = 0; i < gameList.size();i++){
            if(gameList.get(i).getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    /*
    schreibt momentanen Inhalt von gameList in Games.txt
     */
    //!!!!!!!!!!!!!!!!ES WERDEN NOCH NICHT DIE RUNDEN STATS ERFASST!!!!!!!!!!!!!!!!!!!!!!
    public void writeToJSON(Context c){
        try{
            //Directory erstellen
            File dir = new File(c.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),"myFiles");

            if (!dir.exists()) {
                dir.mkdirs();
            }
            File myFile = new File(c.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)+"/myFiles/Games.txt");

            FileOutputStream fout = new FileOutputStream(myFile);
            OutputStreamWriter outWriter = new OutputStreamWriter(fout);

            JSONArray jsonArray = new JSONArray();
            for(int i = 0; i < gameList.size();i++){
                JSONObject obj = new JSONObject();
                Game g = gameList.get(i);
                obj.put("name",g.getName());
                obj.put("player0",g.getPlayer0().getName());
                obj.put("player1",g.getPlayer1().getName());
                obj.put("player2",g.getPlayer2().getName());
                obj.put("player3",g.getPlayer3().getName());
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
    ließt von der JSONDatei nach gameList die Games ein
     */
    //!!!!!!!!!!!!!!!!ES WERDEN NOCH NICHT DIE RUNDEN STATS ERFASST!!!!!!!!!!!!!!!!!!!!!!
    public void readFromJSON(Context c){
        File dir = new File(c.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),"myFiles");

        if (!dir.exists()) {
            dir.mkdirs();
            return;
        }

        gameList = new ArrayList<>();
        String fileName = "/myFiles/Games.txt";
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
                String p0 = obj.getString("player0");
                String p1 = obj.getString("player1");
                String p2 = obj.getString("player2");
                String p3 = obj.getString("player3");

                PlayerController pControl = new PlayerController(new ArrayList<Player>());
                pControl.readFromJSON(c);

                Game g = new Game(name,pControl.getPlayerbyName(p0),pControl.getPlayerbyName(p1),pControl.getPlayerbyName(p2),pControl.getPlayerbyName(p3));
                gameList.add(g);
            }
        }

        catch (Exception e){
            Log.e("Exp",e.getMessage());

        }
    }

    public GameController(ArrayList<Game> gameList) {
        this.gameList = gameList;
    }

    public ArrayList<Game> getGameList() {
        return gameList;
    }

    public void setGameList(ArrayList<Game> gameList) {
        this.gameList = gameList;
    }
}
