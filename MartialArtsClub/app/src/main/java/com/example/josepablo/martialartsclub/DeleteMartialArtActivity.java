package com.example.josepablo.martialartsclub;

import android.icu.text.RelativeDateTimeFormatter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.josepablo.martialartsclub.Model.DataBaseHandler;
import com.example.josepablo.martialartsclub.Model.MartialArt;

import java.util.ArrayList;

public class DeleteMartialArtActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private DataBaseHandler dataBaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_martial_art);

        dataBaseHandler = new DataBaseHandler(DeleteMartialArtActivity.this);

        updateTheUserInterface();


    }

    //Creates a radioButton for each martialArt object and if the radioButton is selected
    //the martial art will be delete it
    private void updateTheUserInterface(){

        ArrayList<MartialArt> allMartialArtObjects =
                dataBaseHandler.returnAllMartialArtObjects();
        RelativeLayout relativeLayout = new RelativeLayout(DeleteMartialArtActivity.this);
        ScrollView scrollView = new ScrollView(DeleteMartialArtActivity.this);
        RadioGroup radioGroup = new RadioGroup(DeleteMartialArtActivity.this);

        for(MartialArt martialArt : allMartialArtObjects){

            RadioButton currentRadioButton = new RadioButton(DeleteMartialArtActivity.this);
            currentRadioButton.setId(martialArt.getMartialArtID());
            currentRadioButton.setText(martialArt.toString());
            radioGroup.addView(currentRadioButton);

        }

        radioGroup.setOnCheckedChangeListener(DeleteMartialArtActivity.this);
        Button btnBack = new Button(DeleteMartialArtActivity.this);
        btnBack.setText("Go Back");
        btnBack.setOnClickListener(DeleteMartialArtActivity.this);

        scrollView.addView(radioGroup);

        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        buttonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonParams.setMargins(0, 0, 0, 70);

        relativeLayout.addView(btnBack, buttonParams);

        ScrollView.LayoutParams scrollViewParams = new ScrollView.LayoutParams(
            ScrollView.LayoutParams.MATCH_PARENT,
            ScrollView.LayoutParams.MATCH_PARENT
            );

        relativeLayout.addView(scrollView, scrollViewParams);

        setContentView(relativeLayout);

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

        dataBaseHandler.deleteMartialArtObjectFromDatabaseByID(checkedId);
        Toast.makeText(DeleteMartialArtActivity.this, "the martial art object is deleted",
                Toast.LENGTH_SHORT).show();
        updateTheUserInterface();

    }

    @Override
    public void onClick(View view) {

        finish();
    }
}
