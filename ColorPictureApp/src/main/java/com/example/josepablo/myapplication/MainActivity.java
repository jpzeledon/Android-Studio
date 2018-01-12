package com.example.josepablo.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnTakeAPicture;
    private Button btnSaveThePicture;
    private ImageView imgPhoto;
    private SeekBar redSeekBar;
    private SeekBar greenSeekBar;
    private SeekBar blueSeekBar;
    private TextView txtRedColorValue;
    private TextView txtGreenColorValue;
    private TextView txtBlueColorValue;
    private Button btnShareImage;

    private static final int CAMERA_IMAGE_REQUEST_CODE = 1000;

    private Bitmap bitmap;

    private Colorful colorful;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assignments of ids
        btnTakeAPicture = (Button) findViewById(R.id.btnTakePicture);
        btnSaveThePicture = (Button) findViewById(R.id.btnSavePicture);
        btnShareImage = (Button) findViewById(R.id.btnShare);
        imgPhoto = (ImageView)  findViewById(R.id.imgPhoto);
        redSeekBar = (SeekBar) findViewById(R.id.redColorSeekBar);
        greenSeekBar = (SeekBar) findViewById(R.id.greenColorSeekBar);
        blueSeekBar = (SeekBar) findViewById(R.id.blueColorSeekBar);
        txtRedColorValue = (TextView) findViewById(R.id.txtRedColorValue);
        txtGreenColorValue = (TextView) findViewById(R.id.txtGreenColorValue);
        txtBlueColorValue = (TextView) findViewById(R.id.txtBlueColorValue);

        //Assignments of button listeners
        btnTakeAPicture.setOnClickListener(MainActivity.this);
        btnSaveThePicture.setOnClickListener(MainActivity.this);
        btnShareImage.setOnClickListener(MainActivity.this);

        //Assignment of handler
        ColorizationHandler colorizationHandler = new ColorizationHandler();

        //Assgment of bar Listeners
        redSeekBar.setOnSeekBarChangeListener(colorizationHandler);
        greenSeekBar.setOnSeekBarChangeListener(colorizationHandler);
        blueSeekBar.setOnSeekBarChangeListener(colorizationHandler);

        //Assignments of invisible views
        btnSaveThePicture.setVisibility(View.INVISIBLE);
        redSeekBar.setVisibility(View.INVISIBLE);
        greenSeekBar.setVisibility(View.INVISIBLE);
        blueSeekBar.setVisibility(View.INVISIBLE);
        btnShareImage.setVisibility(View.INVISIBLE);
        txtRedColorValue.setVisibility(View.INVISIBLE);
        txtGreenColorValue.setVisibility(View.INVISIBLE);
        txtBlueColorValue.setVisibility(View.INVISIBLE);


    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btnTakePicture){
            //Asks the permission to use the camera to the user
            int permissionResult = ContextCompat.checkSelfPermission
                    (MainActivity.this, Manifest.permission.CAMERA);

            if(permissionResult == PackageManager.PERMISSION_GRANTED){

                PackageManager packageManager = getPackageManager();
                if(packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)){

                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_IMAGE_REQUEST_CODE);

                }else{

                    Toast.makeText(MainActivity.this, "Your device does not have a camera", Toast.LENGTH_SHORT);

                }

            }else{

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        1);

            }

        }else if (view.getId() == R.id.btnSavePicture){

            int permissionCheck = ContextCompat.checkSelfPermission
                    (MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionCheck == PackageManager.PERMISSION_GRANTED){

                try{

                    SaveFile.saveFile(MainActivity.this, bitmap);
                    Toast.makeText(MainActivity.this, "The Image is now" +
                            "Successfully saved to External Storage!", Toast.LENGTH_SHORT).show();

                }catch (Exception exception){

                    exception.printStackTrace();

                }

            }else{

                ActivityCompat.requestPermissions(MainActivity.this, new String[]
                        {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2000);

            }

        }else if(view.getId() == R.id.btnShare){

            try{

                File myPictureFile = SaveFile.saveFile(MainActivity.this, bitmap);
                Uri myUri = Uri.fromFile(myPictureFile);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "This Picture is sent from the Color App");
                shareIntent.putExtra(Intent.EXTRA_STREAM, myUri);
                startActivity(Intent.createChooser(shareIntent, "Let's share your Picture with others"));

            }catch (Exception exception){

                exception.printStackTrace();

            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(MainActivity.this, "OnActivityResult is Called", Toast.LENGTH_SHORT);

        if(requestCode == CAMERA_IMAGE_REQUEST_CODE && resultCode == RESULT_OK){

            //When the picture is taken the system shows to the user new options
            btnSaveThePicture.setVisibility(View.VISIBLE);
            redSeekBar.setVisibility(View.VISIBLE);
            greenSeekBar.setVisibility(View.VISIBLE);
            blueSeekBar.setVisibility(View.VISIBLE);
            btnShareImage.setVisibility(View.VISIBLE);
            txtRedColorValue.setVisibility(View.VISIBLE);
            txtGreenColorValue.setVisibility(View.VISIBLE);
            txtBlueColorValue.setVisibility(View.VISIBLE);

            //Sets the necessary data to show de picture
            Bundle bundle = data.getExtras();
            bitmap = (Bitmap) bundle.get("data");
            colorful = new Colorful(bitmap, 0.0f,0.0f, 0.0f );
            imgPhoto.setImageBitmap(bitmap);

        }
    }

    private class ColorizationHandler implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }


        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            if(fromUser){
                //Saves the new color data in the colorful class and shows to the user the new
                //values in the bar and in textView, after this the  system makes the changes to the
                //picture and the system shows the modifications of the picture to the user

                if(seekBar == redSeekBar){

                    colorful.setRedColorValue(progress/100.0f);
                    redSeekBar.setProgress((int)(100 * (colorful.getRedColorValue())));
                    txtRedColorValue.setText(colorful.getRedColorValue() + "");

                }else if(seekBar == greenSeekBar){

                    colorful.setGreenColorValue(progress / 100.0f);
                    greenSeekBar.setProgress((int)(100 * (colorful.getGreenColorValue())));
                    txtGreenColorValue.setText(colorful.getGreenColorValue() + "");

                } else if(seekBar == blueSeekBar){

                    colorful.setBlueColorValue(progress / 100.0f);
                    blueSeekBar.setProgress((int)(100 * (colorful.getBlueColorValue())));
                    txtBlueColorValue.setText(colorful.getBlueColorValue() + "");

                }

                bitmap = colorful.returnTheColorizedBitmap();
                imgPhoto.setImageBitmap(bitmap);

            }
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
