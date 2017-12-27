package com.example.josepablo.appwithsqlite;

/**
 * Created by Jose Pablo on 18/12/2017.
 */

//Class to save the information of a computer
public class Computer {

    private int id;
    private String computerName;
    private String computerType;

    //Empty constructor
    public Computer(){

        super();
    }

    public Computer(int id, String computerName, String computerType){

        this.id = id;
        this.computerName = computerName;
        this.computerType = computerType;

    }

    public Computer(String computerName, String computerType){

        this.computerName = computerName;
        this.computerType = computerType;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getComputerType() {
        return computerType;
    }

    public void setComputerType(String computerType) {
        this.computerType = computerType;
    }
}
