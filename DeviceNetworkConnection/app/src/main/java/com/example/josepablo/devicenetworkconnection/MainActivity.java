package com.example.josepablo.devicenetworkconnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imgNetworkType;
    Button btnGetNetworkStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgNetworkType = (ImageView) findViewById(R.id.imgNetworkType);
        btnGetNetworkStatus = (Button) findViewById(R.id.btnGetNetworkStatus);

        btnGetNetworkStatus.setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View view) {

        int statusInfo = NetworkInfo.getNetworkStatus(MainActivity.this);

        if(statusInfo == 0 ){

            imgNetworkType.setImageDrawable(getResources().getDrawable(R.drawable.mobileservice));
            Toast.makeText(MainActivity.this, "Mobile Internet", Toast.LENGTH_SHORT).show();

        } else if (statusInfo == 1){

            imgNetworkType.setImageDrawable(getResources().getDrawable(R.drawable.wifiservice));
            Toast.makeText(MainActivity.this, "Wifi Internet", Toast.LENGTH_SHORT).show();

        }else{

            imgNetworkType.setImageResource(R.mipmap.ic_launcher);

            Toast.makeText(MainActivity.this, "Unknown", Toast.LENGTH_SHORT).show();
        }


    }
}
