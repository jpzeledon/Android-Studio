 package com.example.josepablo.martialartsclub;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josepablo.martialartsclub.Model.DataBaseHandler;
import com.example.josepablo.martialartsclub.Model.MartialArt;
import com.example.josepablo.martialartsclub.Model.VerificationData;

import java.lang.reflect.Type;
import java.util.ArrayList;


 public class UpdateMartialArtActivity extends AppCompatActivity implements View.OnClickListener{

     private DataBaseHandler dataBaseHandler;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_update_martial_art);

         dataBaseHandler = new DataBaseHandler(UpdateMartialArtActivity.this);
         modifyUserInterface();


     }

     //This method creates interface objects to add them to the activity
     private void modifyUserInterface() {

         ArrayList<MartialArt> martialArtObjects =
                 dataBaseHandler.returnAllMartialArtObjects();
         if (martialArtObjects.size() > 0) {


             ScrollView scrollView = new ScrollView(UpdateMartialArtActivity.this);
             GridLayout gridLayout = new GridLayout(UpdateMartialArtActivity.this);
             gridLayout.setRowCount(martialArtObjects.size());
             gridLayout.setColumnCount(5);

             TextView[] idTextViews = new TextView[martialArtObjects.size()];
             EditText[][] edtNamesAndPrices =
                     new EditText[martialArtObjects.size()][2];
             Spinner[] spinnerColors = new  Spinner[martialArtObjects.size()];
             Button[] modifyButtons = new Button[martialArtObjects.size()];

             Point screenSize = new Point();
             getWindowManager().getDefaultDisplay().getSize(screenSize);

             int screenWidth = screenSize.x;
             int index = 0;

             //This for creates textviews allowing the user to see the id of every martialArt object
             //and creates 2 edtisText, 1 spinner and 1 button so it can be possible to modify the
             //information of each martialArt object
             for (MartialArt martialArtObject : martialArtObjects) {

                 idTextViews[index] = new TextView(UpdateMartialArtActivity.this);
                 idTextViews[index].setGravity(Gravity.CENTER);
                 idTextViews[index].setText(martialArtObject.getMartialArtID() + "");

                 edtNamesAndPrices[index][0] = new EditText(UpdateMartialArtActivity.this);
                 edtNamesAndPrices[index][1] = new EditText(UpdateMartialArtActivity.this);
                 spinnerColors[index] = new Spinner(UpdateMartialArtActivity.this);


                 edtNamesAndPrices[index][0].setText(martialArtObject.getMartialArtName());
                 edtNamesAndPrices[index][1].setText(martialArtObject.getMartialArtPrice() + "");
                 edtNamesAndPrices[index][1].setInputType(InputType.TYPE_CLASS_NUMBER);

                 ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                         R.array.colors_array, android.R.layout.simple_spinner_item);
                 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                 spinnerColors[index].setAdapter(adapter);

                 spinnerColors[index].setSelection(
                         adapter.getPosition(martialArtObject.getMartialArtColor()));

                 edtNamesAndPrices[index][0].setId(martialArtObject.getMartialArtID() + 10);
                 edtNamesAndPrices[index][1].setId(martialArtObject.getMartialArtID() + 20);
                 spinnerColors[index].setId(martialArtObject.getMartialArtID() + 30);

                 modifyButtons[index] = new Button(UpdateMartialArtActivity.this);
                 modifyButtons[index].setText("Modify");
                 modifyButtons[index].setId(martialArtObject.getMartialArtID());
                 modifyButtons[index].setOnClickListener(UpdateMartialArtActivity.this);

                 gridLayout.addView(idTextViews[index], (int) (screenWidth * 0.05),
                         ViewGroup.LayoutParams.WRAP_CONTENT);
                 gridLayout.addView(edtNamesAndPrices[index][0],
                         (int) (screenWidth * 0.20), ViewGroup.LayoutParams.WRAP_CONTENT);
                 gridLayout.addView(edtNamesAndPrices[index][1],
                         (int) (screenWidth * 0.20), ViewGroup.LayoutParams.WRAP_CONTENT);
                 gridLayout.addView(spinnerColors[index],
                         (int)( screenWidth * 0.25), ViewGroup.LayoutParams.WRAP_CONTENT);
                 gridLayout.addView(modifyButtons[index],
                         (int)(screenWidth * 0.30), ViewGroup.LayoutParams.WRAP_CONTENT);

                 index++;

             }

             scrollView.addView(gridLayout);
             setContentView(scrollView);
         }
     }


     @Override
     public void onClick(View view) {

         //After the system takes all the new information is verificated and then if is right
         // is add it to the database
         int martialArtObjectID = view.getId();

         EditText edtMartialArtName = (EditText) findViewById(martialArtObjectID + 10);
         EditText edtMartialArtPrice = (EditText) findViewById(martialArtObjectID + 20);
         Spinner spinnerColors = (Spinner) findViewById(martialArtObjectID + 30);

         String martialArtNameStringValue = edtMartialArtName.getText().toString();
         String martialArtPriceStringValue = edtMartialArtPrice.getText().toString();

         if (!martialArtNameStringValue.trim().equals("") && !martialArtPriceStringValue.trim().equals("")){

             if (VerificationData.verificateRepeatMartialArtName(
                     dataBaseHandler.returnAllMartialArtObjects(), martialArtNameStringValue) == false) {

                 String martialArtColorStringValue = spinnerColors.getSelectedItem().toString();

                 try {

                     double martialArtPriceDoubleValue =
                             Double.parseDouble(martialArtPriceStringValue);

                     dataBaseHandler.modifyMartialArtObject(martialArtObjectID,
                             martialArtNameStringValue,
                             martialArtPriceDoubleValue,
                             martialArtColorStringValue);

                     Toast.makeText(UpdateMartialArtActivity.this, "This martial art Object is updated",
                             Toast.LENGTH_SHORT).show();


                 } catch (NumberFormatException exception) {

                     exception.printStackTrace();

                 }
             }else{

                 Toast.makeText(UpdateMartialArtActivity.this, "This martial art " +
                         "already exists ", Toast.LENGTH_SHORT).show();
             }

         }

    }
 }
