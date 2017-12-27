package com.example.josepablo.appwithsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jose Pablo on 18/12/2017.
 */

public class MySqliteHandler extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "computer.db";

    //Computer Table Name
    private static final String TABLE_COMPUTER = "computers";

    //Computer Table Columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_COMPUTER_NAME = "computerName";
    private static final String COLUMN_COMPUTER_TYPE = "computerType";

    String CREATE_COMPUTER_TABLE = "CREATE TABLE " + TABLE_COMPUTER + "(" + COLUMN_ID +
            " INTEGER PRIMARY KEY, " + COLUMN_COMPUTER_NAME + " TEXT, " +
            COLUMN_COMPUTER_TYPE + " TEXT " + ")";

    public MySqliteHandler(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL(CREATE_COMPUTER_TABLE);

    }

    @Override
    public  void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPUTER);
        onCreate(db);
    }

    // All Database Operations: create, read, update, delete

    //create
    public void addComputer(Computer computer) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMPUTER_NAME, computer.getComputerName());
        values.put(COLUMN_COMPUTER_TYPE, computer.getComputerType());

        database.insert(TABLE_COMPUTER, null, values);
        database.close();
    }

    //Getting a single computer
    public Computer getComputer(int id){

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_COMPUTER, new String[] {COLUMN_ID,
                        COLUMN_COMPUTER_NAME, COLUMN_COMPUTER_TYPE},
                        COLUMN_ID + "=?", new  String[] {String.valueOf(id)}, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }

        Computer computer = new Computer(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        return  computer;
    }


    //Getting all Computer Objects
    public List<Computer> getAllComputers(){

        List<Computer> computerList = new ArrayList<>();

        String selectAllQuery = "SELECT * FROM " + TABLE_COMPUTER;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectAllQuery, null);
        if(cursor.moveToFirst()){

            do{

                Computer computer = new Computer();
                computer.setId(Integer.parseInt(cursor.getString(0)));
                computer.setComputerName(cursor.getString(1));
                computer.setComputerType(cursor.getString(2));

                computerList.add(computer);

            }while (cursor.moveToNext());

        }

        return computerList;
    }

    //Updating a single computer
    public int updateComputer(Computer computer){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMPUTER_NAME, computer.getComputerName());
        values.put(COLUMN_COMPUTER_TYPE, computer.getComputerType());

        return  database.update(TABLE_COMPUTER, values, COLUMN_ID + " = ?",
                new String[] {String.valueOf(computer.getId())});
    }

    //Deleting a single computer
    public void deleteComputer(Computer computer){

        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_COMPUTER, COLUMN_ID + " = ?",
                new String[]{String.valueOf(computer.getId())});
        database.close();
    }

    //Getting the number of computers

    public int getComputersCount(){

        String computersCountQuery = "SELECT * FROM " + TABLE_COMPUTER;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(computersCountQuery, null);
        cursor.close();

        return cursor.getCount();
    }





}
