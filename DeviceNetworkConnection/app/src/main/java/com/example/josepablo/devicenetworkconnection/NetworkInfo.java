package com.example.josepablo.devicenetworkconnection;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Jose Pablo on 16/12/2017.
 */

public class NetworkInfo {

    //This function obtains the type of internet connectivity then returns an int, 0 if is Mobile,
    // 1 if es wIFI, 2 if is another kind of connectivity and 3 if does not detected it
    public static int getNetworkStatus(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager)
             context.getSystemService(Context.CONNECTIVITY_SERVICE);

        android.net.NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null){
            switch (networkInfo.getType()){
                case ConnectivityManager.TYPE_MOBILE:
                    return 0;
                case ConnectivityManager.TYPE_WIFI:
                    return 1;
                default:
                    return 2;

            }
        }else{
            return 3;
        }
    }


}
