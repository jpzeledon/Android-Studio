package com.example.josepablo.accesstophoneservices;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int CALL = 1;

    private static String[] PERMISSIONS = {
            Manifest.permission.CALL_PHONE
    };

    public static void verifyThatWeCanCallSomeone(Activity activity){
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE);

        if(permission != PackageManager.PERMISSION_GRANTED){
            //We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS,
                    CALL
            );
        }
    }

    Button btnOpenUdemy;
    Button btnSearchGoogle;
    Button btnCall;
    Button btnAccessDialPad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyThatWeCanCallSomeone(MainActivity.this) ;

        //Assigns the ids and listeners
        btnOpenUdemy = (Button) findViewById(R.id.btnUdemy);
        btnSearchGoogle = (Button) findViewById(R.id.btnSearchGoogle);
        btnCall = (Button) findViewById(R.id.btnCall);
        btnAccessDialPad = (Button) findViewById(R.id.btnAccessDialPad);

        btnOpenUdemy.setOnClickListener(MainActivity.this);
        btnSearchGoogle.setOnClickListener(MainActivity.this);
        btnCall.setOnClickListener(MainActivity.this);
        btnAccessDialPad.setOnClickListener(MainActivity.this);

    }

    @Override
    public void onClick(View view){
        //Each case opens a different function

        switch(view.getId()){

            case R.id.btnUdemy:

                Intent intentUdemy = new Intent(Intent.ACTION_VIEW);
                intentUdemy.setData(Uri.parse("http://www.udemy.com"));
                startActivity(intentUdemy);

            break;

            case R.id.btnSearchGoogle:

                Intent intentSearchGoogle = new Intent(Intent.ACTION_WEB_SEARCH);
                intentSearchGoogle.setData(Uri.parse("http://www.google.com"));
                startActivity(intentSearchGoogle);

            break;

            case R.id.btnCall:

                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CALL_PHONE},CALL);

                }else{
                    Intent intentCall = new Intent(Intent.ACTION_CALL);
                    intentCall.setData(Uri.parse("tel:323432423434324"));
                    startActivity(intentCall);
                }

            break;

            case R.id.btnAccessDialPad:

                Intent intentDialPad = new Intent(Intent.ACTION_DIAL);
                startActivity(intentDialPad);
                 
            break;

        }
    }
}
