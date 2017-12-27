package com.example.josepablo.pairgame;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    GridView grdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        grdView = (GridView) findViewById(R.id.grdView);
        GridViewAdapter adapter = new GridViewAdapter(MainActivity.this);

        grdView.setAdapter(adapter);

    }


}
