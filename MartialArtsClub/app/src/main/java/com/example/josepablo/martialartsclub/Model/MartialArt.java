package com.example.josepablo.martialartsclub.Model;

/**
 * Created by Jose Pablo on 12/01/2018.
 */

public class MartialArt {

    private int martialArtID;
    private String martialArtName;
    private double martialArtPrice;
    private String martialArtColor;

    public MartialArt(int id, String name, double price, String color ){

        setMartialArtID(id);
        setMartialArtName(name);
        setMartialArtPrice(price);
        setMartialArtColor(color);

    }

    public int getMartialArtID() {
        return martialArtID;
    }

    public String getMartialArtName() {
        return martialArtName;
    }

    public double getMartialArtPrice() {
        return martialArtPrice;
    }

    public String getMartialArtColor() {
        return martialArtColor;
    }

    public void setMartialArtID(int martialArtID) {
        this.martialArtID = martialArtID;
    }

    public void setMartialArtName(String martialArtName) {
        this.martialArtName = martialArtName;
    }

    public void setMartialArtPrice(double martialArtPrice) {
        this.martialArtPrice = martialArtPrice;
    }

    public void setMartialArtColor(String martialArtColor) {
        this.martialArtColor = martialArtColor;
    }

    @Override
    public String toString() {
        return getMartialArtID() + " " + getMartialArtName() + " " +
               getMartialArtPrice() + " " + getMartialArtColor();
    }
}
