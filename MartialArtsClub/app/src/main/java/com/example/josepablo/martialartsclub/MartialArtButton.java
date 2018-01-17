package com.example.josepablo.martialartsclub;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;

import com.example.josepablo.martialartsclub.Model.MartialArt;

/**
 * Created by Jose Pablo on 14/01/2018.
 */

public class MartialArtButton extends AppCompatButton{

     private MartialArt martialArtObject;

     public MartialArtButton(Context context, MartialArt martialArt){

         super(context);
         martialArtObject = martialArt;

     }

     public String getMartialArtColor(){

         return martialArtObject.getMartialArtColor();

     }

     public double getMartialArtPrice(){

         return martialArtObject.getMartialArtPrice();

     }

}
