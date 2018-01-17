package com.example.josepablo.martialartsclub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.josepablo.martialartsclub.Model.DataBaseHandler;
import com.example.josepablo.martialartsclub.Model.MartialArt;
import com.example.josepablo.martialartsclub.Model.VerificationData;

public class AddMartialArtActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtName, edtPrice;
    private Spinner spinnerColors;
    private Button btnAddMartialArt, btnBack;
    private VerificationData verificationData;

    private DataBaseHandler dataBaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_martial_art);

        //Assigning ids the editTexts
        edtName = (EditText) findViewById(R.id.edtName);
        edtPrice = (EditText) findViewById(R.id.edtPrice);
        spinnerColors = (Spinner) findViewById(R.id.spinnerColors);

        ////Assigning the Array String to the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.colors_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerColors.setAdapter(adapter);

        //Assigning ids to the buttons
        btnAddMartialArt = (Button) findViewById(R.id.btnAddMartialArt);
        btnBack = (Button) findViewById(R.id.btnGoBack);

        //Assigning handler
        dataBaseHandler = new DataBaseHandler(AddMartialArtActivity.this);

        //Assigning listeners to the buttons
        btnAddMartialArt.setOnClickListener(AddMartialArtActivity.this);
        btnBack.setOnClickListener(AddMartialArtActivity.this);
    }

    private void addMartialArtObjectToDatabase(){

        String nameValue = edtName.getText().toString();
        String priceValue = edtPrice.getText().toString();
        if (!nameValue.trim().equals("")){

            if(!priceValue.trim().equals(""))
            {
                if(VerificationData.verificateRepeatMartialArtName(
                        dataBaseHandler.returnAllMartialArtObjects(), nameValue) == false){

                    String colorValue = spinnerColors.getSelectedItem().toString();

                    try{

                        double priceDoubleValue = Double.parseDouble(priceValue);
                        MartialArt martialArtObject = new MartialArt(0, nameValue,
                                priceDoubleValue, colorValue);
                        dataBaseHandler.addMartialArt(martialArtObject);
                        Toast.makeText(AddMartialArtActivity.this, martialArtObject+
                                "This martial art object is added to Database", Toast.LENGTH_SHORT).show();

                    }catch (Exception exception){

                        exception.printStackTrace();
                    }

                }else{

                    Toast.makeText(AddMartialArtActivity.this, "This martial art  " +

                            "already exists", Toast.LENGTH_SHORT).show();
                }

            } else{

                edtPrice.setError("This item cannot be empty");
            }


        }else {

            edtName.setError("This item cannot be empty");
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnAddMartialArt:
                addMartialArtObjectToDatabase();
                break;

            case R.id.btnGoBack:
                this.finish();
                break;
        }
    }

}
