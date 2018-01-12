package com.example.josepablo.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.SystemClock;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Jose Pablo on 11/01/2018.
 */

public class SaveFile {

    public static File saveFile(Activity myActivity, Bitmap bitmap) throws IOException{

        String externalStorageState = Environment.getExternalStorageState();
        File myFile = null;
        if(externalStorageState.equals(Environment.MEDIA_MOUNTED)){
            //This section takes the current date to use it like a name for the image and
            //takes the remaining and required space to check if is possible to save the image
            File picturesDirectory = myActivity.getExternalFilesDir("ColoAppPictures");
            Date currentDate = new Date();
            long elapsedTime = SystemClock.elapsedRealtime();
            String uniqueImageName = "/" + currentDate + "_" + elapsedTime + ".png";
            myFile = new File(picturesDirectory + uniqueImageName);
            long remainingSpace = picturesDirectory.getFreeSpace();
            long requiredSpace = bitmap.getByteCount();

            if(requiredSpace * 1.8 < remainingSpace){

                try{

                    FileOutputStream fileOutputStream = new FileOutputStream(myFile);
                    boolean isImageSaveSuccessfully = bitmap.compress
                            (Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    if(isImageSaveSuccessfully){

                        return myFile;

                    }else{

                        throw new IOException("The image is not saved successfully " +
                                "to External Storage");

                    }

                }catch (Exception exception){

                    throw new IOException("The operation of saving the Image to" +
                            "External Storage went wrong");
                }

            }else{

                throw new IOException("There is no enough space in order" +
                        "to save the image to External Storage");
            }


        }else{

            throw new IOException("This device does not have a external storage");
        }
    }
}
