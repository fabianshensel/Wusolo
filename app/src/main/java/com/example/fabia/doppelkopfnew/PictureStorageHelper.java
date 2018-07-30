package com.example.fabia.doppelkopfnew;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PictureStorageHelper {

    //Deletes the Pictures of Players who are nolonger in PlayerList
    static public void cleanUpStorage(ArrayList<Player> players){
        //Get everyFile of Directory
        File folder = new File("/storage/emulated/0/Android/data/com.example.fabia.doppelkopfnew/files/Documents/myFiles");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].exists()){
                String fileName = listOfFiles[i].getName();
                if(fileName.endsWith(".jpg")){
                    //cut filename to equal playernames
                    fileName = fileName.substring(0,fileName.lastIndexOf("."));
                    Log.d("Clean Up Storage",fileName);
                    boolean isOutdated = true;
                    //if there is no player in list with same name as file -> delete file
                    for(Player p : players){
                        if(p.getName().equals(fileName)){
                            isOutdated = false;
                        }
                    }
                    if(isOutdated){
                      listOfFiles[i].delete();
                    }
                }
            }
        }
    }

    static public String saveToInternalStorage(Bitmap bitmapImage, Context c , String imageName){
        ContextWrapper cw = new ContextWrapper(c);
        // path to /data/data/yourapp/app_data/imageDir

        File directory = new File(c.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),"myFiles");

        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Create imageDir
        File mypath = new File(directory,imageName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return directory.getAbsolutePath();
    }

    //ImageName braucht ein + ".jpg" am ende
    static public Bitmap loadImageFromStorage(String path,String imageName)
    {

        try {
            File f=new File(path, imageName);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
