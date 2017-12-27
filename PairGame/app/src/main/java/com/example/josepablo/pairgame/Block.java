package com.example.josepablo.pairgame;

import android.graphics.drawable.Drawable;

/**
 * Created by Jose Pablo on 30/10/2017.
 */
//This class contains the images and their information
public class Block {

    private boolean turned;
    private int idImg;
    private int idImg_win;
    private int idView;
    private String name;

    public  Block(int idImg, String name,int idImg_win)
    {
        this.turned = false;
        this.idImg = idImg;
        this.name = name;
        this.idImg_win = idImg_win;
    }

    public boolean isTurned() {
        return turned;
    }

    public void setTurned(boolean turned) {
        this.turned = turned;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdView() {
        return idView;
    }

    public void setIdView(int idView) {
        this.idView = idView;
    }

    public int getIdImg() {
        return idImg;
    }

    public void setIdImg(int idImg) {
        this.idImg = idImg;
    }

    public int getIdImg_win() {
        return idImg_win;
    }

    public void setIdImg_win(int idImg_win) {
        this.idImg_win = idImg_win;
    }
}
