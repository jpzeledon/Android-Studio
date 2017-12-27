package com.example.josepablo.appprofile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    EditText edtName;
    EditText edtEmail;
    EditText edtUserName;
    EditText edtPassword;
    ImageView imgProfile;
    RadioGroup radioGroup;
    Button btnAdd;
    String genderType = "";
    final int IMAGE_REQUEST_CODE = 1000;
    String photoPath = "";
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assigning the ids and the Listeners

        edtName = (EditText) findViewById(R.id.editName);
        edtEmail = (EditText) findViewById(R.id.editEmail);
        edtUserName = (EditText) findViewById(R.id.editUserName);
        edtPassword = (EditText) findViewById(R.id.editPassword);
        imgProfile = (ImageView) findViewById(R.id.imgProfile);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(MainActivity.this);

        radioGroup.setOnCheckedChangeListener(MainActivity.this);

        imgProfile.setOnClickListener(MainActivity.this);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bloque);
        imgProfile.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnAdd:
                //Saves the data in the intents
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("NAME", edtName.getText().toString());
                intent.putExtra("EMAIL", edtEmail.getText().toString());
                intent.putExtra("USER_NAME",edtUserName.getText().toString());
                intent.putExtra("PASSWORD", edtPassword.getText().toString());
                intent.putExtra("GENDER_TYPE", genderType);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("PICTURE", byteArray);

                startActivity(intent);
                break;

            case R.id.imgPerfil:
                //Allow the user to look for a image in his device
                Intent intentImage = new Intent(Intent.ACTION_GET_CONTENT);
                intentImage.setType("image/*");
                startActivityForResult(intentImage, IMAGE_REQUEST_CODE);
                break;
        }

    }

    //Shows the selected image in the ImageView imgProfile
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case IMAGE_REQUEST_CODE:

                if(resultCode == RESULT_OK){

                    Uri choseImage = data.getData();
                    try{
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), choseImage);
                        imgProfile.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            break;
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
    //Saves the gender

            switch(checkedId){

                case R.id.radioMale:
                    genderType = "Male";
                    break;

                case R.id.radioFemale:
                    genderType = "Female";
                    break;
            }
    }
}
