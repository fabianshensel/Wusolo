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

    public ArrayList<Game> getGamesWithPlayer(String name){
        ArrayList<Game> returnList = new ArrayList<>();

        for(int i = 0; i < gameList.size();i++){
            //Schaut on SpielerName mit einem der Spieler im game übereinstimmt

            if(!GameController.isGameDirty(gameList.get(i))){
                if(gameList.get(i).getPlayer0().getName().equals(name) || gameList.get(i).getPlayer1().getName().equals(name) || gameList.get(i).getPlayer2().getName().equals(name) || gameList.get(i).getPlayer3().getName().equals(name)){
                    returnList.add(gameList.get(i));

                }
            }
        }
        return returnList;
    }

    public Game getGamebyName(String name){
        for(int i = 0; i < gameList.size();i++){
            if(gameList.get(i).getName().equals(name)){
                return gameList.get(i);
            }
        }
        return null;
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
                if(g.getPlayer0() != null){
                    obj.put("player0",g.getPlayer0().getName());
                }else{
                    obj.put("player0","noName0");
                }

                if(g.getPlayer1() != null){
                    obj.put("player1",g.getPlayer1().getName());
                }else{
                    obj.put("player1","noName1");
                }
                if(g.getPlayer2() != null){
                    obj.put("player2",g.getPlayer2().getName());
                }else{
                    obj.put("player2","noName2");
                }
                if(g.getPlayer3() != null){
                    obj.put("player3",g.getPlayer3().getName());
                }else{
                    obj.put("player3","noName3");
                }

                for(int j = 0; j < gameList.get(i).getRoundStats().size(); j++){
                    JSONObject roundObj = new JSONObject();
                    RoundStats stats = gameList.get(i).getRoundStats().get(j);

                    roundObj.put("isplayer0",stats.isIsplayer0win());
                    roundObj.put("isplayer1",stats.isIsplayer1win());
                    roundObj.put("isplayer2",stats.isIsplayer2win());
                    roundObj.put("isplayer3",stats.isIsplayer3win());
                    roundObj.put("isSolo",stats.isSoloWin());
                    roundObj.put("points",stats.getPoints());
                    roundObj.put("bock",stats.getBockRoundsleft());

                    obj.put(String.valueOf(j),roundObj);
                }

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

                ArrayList<RoundStats> roundStats = new ArrayList<>();
                for(int j = 0; ;j++){
                    if(obj.has(String.valueOf(j))){
                    JSONObject roundObj = obj.getJSONObject(String.valueOf(j));
                        boolean isp0 = roundObj.getBoolean("isplayer0");
                        boolean isp1 = roundObj.getBoolean("isplayer1");
                        boolean isp2 = roundObj.getBoolean("isplayer2");
                        boolean isp3 = roundObj.getBoolean("isplayer3");
                        boolean isSolo = roundObj.getBoolean("isSolo");
                        int points = roundObj.getInt("points");
                        int bockrounds = roundObj.getInt("bock");
                        RoundStats r = new RoundStats(isp0,isp1,isp2,isp3,isSolo,points,bockrounds);
                        roundStats.add(r);
                    }else{
                        break;
                    }

                }


                PlayerController pControl = new PlayerController(new ArrayList<Player>());
                pControl.readFromJSON(c);



                Game g = new Game(name,pControl.getPlayerbyName(p0),pControl.getPlayerbyName(p1),pControl.getPlayerbyName(p2),pControl.getPlayerbyName(p3),roundStats);
                gameList.add(g);
            }
        }

        catch (Exception e){
            Log.e("Exp",e.getMessage());

        }
    }

    /*
    Checks if a Player who was once in the Game now is not anymore
     */
    static public boolean isGameDirty(Game game){
        if(game.getPlayer0() == null || game.getPlayer1() == null || game.getPlayer2() == null || game.getPlayer3() == null ){
            return true;
        }
        return false;
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
