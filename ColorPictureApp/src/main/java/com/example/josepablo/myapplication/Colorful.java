package com.example.josepablo.myapplication;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by Jose Pablo on 11/01/2018.
 */

//This class is in charge of save the image and the color values
public class Colorful {

    private Bitmap bitmap;
    private float redColorValue;
    private float greenColorValue;
    private float blueColorValue;

    public Colorful(Bitmap bitmap, float redValue, float greenValue, float blueValue ){

        this.bitmap = bitmap;
        setRedColorValue(redValue);
        setGreenColorValue(greenValue);
        setBlueColorValue(blueValue);

    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public float getRedColorValue() {
        return redColorValue;
    }

    public void setRedColorValue(float redValue) {
        if(redValue >= 0 && redValue <= 1 ){

            this.redColorValue = redValue;

        }
    }

    public float getGreenColorValue() {
        return greenColorValue;
    }

    public void setGreenColorValue(float greenValue) {
        if(greenValue >= 0 && greenValue <= 1 ) {

            this.greenColorValue = greenValue;

        }
    }

    public float getBlueColorValue() {
        return blueColorValue;
    }

    public void setBlueColorValue(float blueValue) {
        if(blueValue >= 0 && blueValue <= 1 ) {

            this.blueColorValue = blueValue;

        }
    }

    //This function makes the changes of color in each pixel of the image
    public Bitmap returnTheColorizedBitmap(){

        int bitmapWidth = bitmap.getWidth();
        int bitmalHeight = bitmap.getHeight();
        int pixelColor;

        Bitmap.Config bitmapConfig = bitmap.getConfig();
        Bitmap localBitmap = Bitmap.createBitmap(bitmapWidth, bitmalHeight, bitmapConfig);

        for (int row = 0; row < bitmapWidth; row++){

            for (int column = 0; column < bitmalHeight; column++){

                  pixelColor = bitmap.getPixel(row, column);
                  pixelColor = Color.argb(Color.alpha(pixelColor),
                        (int)redColorValue * Color.red(pixelColor),
                        (int)greenColorValue * Color.green(pixelColor),
                        (int)blueColorValue * Color.blue(pixelColor));
                localBitmap.setPixel(row, column, pixelColor);
            }
        }

        return localBitmap;
    }
}
