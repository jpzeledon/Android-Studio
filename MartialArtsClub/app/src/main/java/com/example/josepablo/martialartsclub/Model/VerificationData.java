package com.example.josepablo.martialartsclub.Model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.example.josepablo.martialartsclub.AddMartialArtActivity;

import java.util.ArrayList;

/**
 * Created by Jose Pablo on 14/01/2018.
 */

public class VerificationData {

    public static boolean verificateRepeatMartialArtName( ArrayList<MartialArt> martialArtObjects, String name){

        boolean result = false;
        if (martialArtObjects.size() > 0) {

            String objectName;
            int count = 0;
            while(result == false && martialArtObjects.size() > count)
            {
                objectName = martialArtObjects.get(count).getMartialArtName().toString();
                if(objectName.equals(name) ){

                    result = true;

                }else {

                    count++;
                }
            }

        }
        return result;
    }


}


