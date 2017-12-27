package com.example.josepablo.sdcardproject;

import android.content.Context;
import android.media.audiofx.EnvironmentalReverb;
import android.os.Environment;
import android.widget.Toast;

/**
 * Created by Jose Pablo on 12/11/2017.
 */

public class SDCARDChecker {

    public static void checkWeatherExternalStorageAvailableOrNot(Context context){

        boolean isExternalStorageAvailable = false;
        boolean isExternalStorageWriteable = false;

        String state = Environment.getExternalStorageState();

        if(Environment.MEDIA_MOUNTED.equals(state))
        {   //Read and write the media

            isExternalStorageAvailable = isExternalStorageWriteable = true;
            Toast.makeText(context, "escribir y leer", Toast.LENGTH_LONG).show();
        }else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
        {   //Only read the media

            isExternalStorageAvailable = true;
            isExternalStorageWriteable = false;
            Toast.makeText(context, "solo leer", Toast.LENGTH_LONG).show();
        }
        else
        {   //Neither read or write

            isExternalStorageAvailable = isExternalStorageWriteable = false;
            Toast.makeText(context, "no es posbile leer ni escribir", Toast.LENGTH_LONG).show();
        }
    }

}
