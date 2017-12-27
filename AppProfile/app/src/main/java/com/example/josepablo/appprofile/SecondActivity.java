package com.example.josepablo.appprofile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SecondActivity extends AppCompatActivity {

    TextView txtName;
    TextView txtEmail;
    TextView txtUserName;
    TextView txtPassword;
    TextView txtGenderType;
    ImageView imgReceieved;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        txtName = (TextView) findViewById(R.id.textName);
        txtEmail = (TextView) findViewById(R.id.textEmailRecieved);
        txtUserName = (TextView) findViewById(R.id.textUserName);
        txtPassword = (TextView) findViewById(R.id.textPassword);
        txtGenderType = (TextView) findViewById(R.id.textGenderType);
        imgReceieved = (ImageView) findViewById(R.id.imgPerfilRecieved);

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("NAME");
        String email = extras.getString("EMAIL");
        String username = extras.getString("USER_NAME");
        String password = extras.getString("PASSWORD");
        String genderType = extras.getString("GENDER_TYPE");

        txtName.setText(name);
        txtEmail.setText(email);
        txtUserName.setText(username);
        txtPassword.setText(password);
        txtGenderType.setText(genderType);

        byte[] byteArray = extras.getByteArray("PICTURE");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray,0, byteArray.length);
        imgReceieved.setImageBitmap(bitmap);
    }
}
