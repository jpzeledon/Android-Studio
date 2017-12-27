package com.example.josepablo.sdcardproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewSwitcher.ViewFactory {

    Button btnDownloadFolder;
    Button btnMusicFolder;
    Button btnDocumentsFolder;
    Button btnRingtonesFolder;
    Button btnPodcastsFolder;
    Button btnMoviesFolder;
    Button btnAlarmsFolder;
    Button btnPicturesFolder;

    Button btnSaveFile;
    EditText edtValue;

    Button btnRetrieveInfo;
    TextView txtValue;

    ImageView imgView;

    Button btnAllowAccessPictures;

    LinearLayout linearLayoutHorizontal;
    ImageSwitcher imgSwitcher;

    ArrayList<String> filePathNames;
    File[] files;



    public static final int requestCode = 1234;

    public boolean isStoragePermissionGranted(){
        if(Build.VERSION.SDK_INT >= 23)
        {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED){
                Log.v("LOG", "Permission is granted");
                return true;

            } else{
                Log.v("LOG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                return false;
            }
        }
        else//Permission is granted on sdk<23 upon installation
        {
            Log.v("LOG","Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            Log.v("LOG", "Permission: " +permissions[0] + "was "+ grantResults[0]);
            //Resume tasks needing this permission
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SDCARDChecker.checkWeatherExternalStorageAvailableOrNot(MainActivity.this);

        isStoragePermissionGranted();

        //Assignment of ids and listeners

        btnDownloadFolder = (Button) findViewById(R.id.btnDownloadsFolder);
        btnMusicFolder = (Button) findViewById(R.id.btnMusicFolders);
        btnDocumentsFolder = (Button) findViewById(R.id.btnDocumentsFolder);
        btnRingtonesFolder = (Button) findViewById(R.id.btnRingtonesFolders);
        btnPodcastsFolder = (Button) findViewById(R.id.btnPodcastFolder);
        btnMoviesFolder = (Button) findViewById(R.id.btnMoviesFolder);
        btnAlarmsFolder = (Button) findViewById(R.id.btnAlarmsFolder);
        btnPicturesFolder = (Button) findViewById(R.id.btnPictuesFolder);
        btnSaveFile = (Button) findViewById(R.id.btnSaveFile);
        btnRetrieveInfo = (Button) findViewById(R.id.btnRetieveTheInfo);

        edtValue = (EditText) findViewById(R.id.editValue);

        txtValue = (TextView) findViewById(R.id.textValue);

        imgView = (ImageView) findViewById(R.id.imageView);

        btnAllowAccessPictures = (Button) findViewById(R.id.btnAllowAcessPictures);

        linearLayoutHorizontal = (LinearLayout) findViewById(R.id.LinearLayoutHorizontal);
        imgSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);

        imgSwitcher.setFactory(MainActivity.this);

        imgSwitcher.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this,
                android.R.anim.slide_in_left));
        imgSwitcher.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this,
                android.R.anim.slide_out_right));

        //Makes a little gallery so it can be posible to see the picture inside a carpet in the SD card
        // in this case the name is game
        btnAllowAccessPictures.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if(isStoragePermissionGranted()){

                    filePathNames = new ArrayList<String>();
                    File filePath = new File(Environment.getExternalStoragePublicDirectory(
                          Environment.DIRECTORY_PICTURES), "game");
                    if(filePath.isDirectory() && filePath != null){

                        files = filePath.listFiles();
                        for(int index = 0; index < files.length; index++){
                            filePathNames.add(files[index].getAbsolutePath());
                        }
                    }
                    for (int index = 0; index < filePathNames.size(); index++){
                        final ImageView imageView = new ImageView(MainActivity.this);
                        imageView.setImageURI(Uri.parse(filePathNames.get(index)));
                        imageView.setLayoutParams(new LinearLayout.LayoutParams(500,500));
                        final int i = index;
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                imgSwitcher.setImageURI(Uri.parse(filePathNames.get(i)));

                            }
                        });
                        linearLayoutHorizontal.addView(imageView);
                    }
                }
            }

        });



        btnDownloadFolder.setOnClickListener(MainActivity.this);
        btnMusicFolder.setOnClickListener(MainActivity.this);
        btnDocumentsFolder.setOnClickListener(MainActivity.this);
        btnRingtonesFolder.setOnClickListener(MainActivity.this);
        btnPodcastsFolder.setOnClickListener(MainActivity.this);
        btnMoviesFolder.setOnClickListener(MainActivity.this);
        btnAlarmsFolder.setOnClickListener(MainActivity.this);
        btnPicturesFolder.setOnClickListener(MainActivity.this);
        btnSaveFile.setOnClickListener(MainActivity.this);
        btnRetrieveInfo.setOnClickListener(MainActivity.this);

        imgView.setOnClickListener(MainActivity.this);

    }

    //Announce if it was possible to create a new folder
    public void showStorageDirectoryMessage(File filePath, String nameOfFolder){

        if(!filePath.mkdirs()){
            letsCreateAToast("There can not be such directory in SDCard");

        }else{
            letsCreateAToast("Your folder is created and its name is: " + nameOfFolder);
        }

    }

    //Creates a new carpet
    public File returnStorageDirectoryForFolderNames(String directoryName, String nameOfFolder)
    {
        File filePath = new File(Environment.getExternalStoragePublicDirectory(directoryName),
                nameOfFolder);

        return filePath;
    }

    public void letsCreateAToast(String message){

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

   // @SuppressLint("NewApi")
    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnDownloadsFolder:

                returnStorageDirectoryForFolderNames(Environment.DIRECTORY_DOWNLOADS,
                        "Nice Downloads!!!");

                break;
            case R.id.btnMusicFolders:

                returnStorageDirectoryForFolderNames(Environment.DIRECTORY_MUSIC,
                        "Nice Musics!!!");

                break;
            case R.id.btnDocumentsFolder:

                returnStorageDirectoryForFolderNames(Environment.DIRECTORY_DOCUMENTS,
                        "Documents Downloads!!!");

                break;
            case R.id.btnRingtonesFolders:

                returnStorageDirectoryForFolderNames(Environment.DIRECTORY_RINGTONES,
                        "Nice Ringtones!!!");

                break;
            case R.id.btnPodcastFolder:

                returnStorageDirectoryForFolderNames(Environment.DIRECTORY_PODCASTS,
                        "Nice Podcast!!!");

                break;
            case R.id.btnMoviesFolder:

                returnStorageDirectoryForFolderNames(Environment.DIRECTORY_MOVIES,
                        "Nice Movies!!!");

                break;

            case R.id.btnAlarmsFolder:

                returnStorageDirectoryForFolderNames(Environment.DIRECTORY_ALARMS,
                        "Nice Alarms!!!");

                break;

            case R.id.btnPictuesFolder:

                returnStorageDirectoryForFolderNames(Environment.DIRECTORY_ALARMS,
                        "Nice Pictures!!!");

                break;

            case R.id.btnSaveFile:

                letsSaveFileToDocumentsFolder();

                break;

            case R.id.btnRetieveTheInfo:

                letsRetrieveFileDataFromDocumentsFolder();

                break;

            case R.id.imageView:
                letsSaveTheImageToPicturesFolder();
                 break;




        }
    }

    //Saves a txt document with the data that the user typed
    public void letsSaveFileToDocumentsFolder(){
        File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                "NiceFile.txt");

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.append(edtValue.getText().toString());
            outputStreamWriter.close();
            fileOutputStream.close();
            letsCreateAToast("Saved");

        }catch (Exception e) {
            Log.i("LOG", e.toString());
            letsCreateAToast("Exception occured Check the Log for more info");
        }
    }

    //Gets the data from the txt document that the user created with this app
    public void letsRetrieveFileDataFromDocumentsFolder(){
        File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                "NiceFile.txt");

        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            BufferedReader bufferredReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String fileData = "";
            String bufferData = "";
            while ((fileData = bufferredReader.readLine()) != null){
                bufferData = bufferData + fileData + "\n";
            }
            txtValue.setText(bufferData);
            bufferredReader.close();
        }catch (Exception e) {
            Log.i("LOG", e.toString());
            letsCreateAToast("Exception occured Check the Log for more info");
        }
    }


    public void letsSaveTheImageToPicturesFolder(){

        try{
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mario);
            File filePath = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES),"Mario.jpg");
            OutputStream outputStream = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.PNG,100, outputStream);
            outputStream.flush();
            outputStream.close();
            letsCreateAToast("Your image has been successfuly saved");

        } catch (Exception e) {
            Log.i("LOG", e.toString());
            letsCreateAToast("Exception occured Check the Log for more info");
        }
    }

    @Override
    public View makeView() {

        ImageView imageView = new ImageView(MainActivity.this);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new ImageSwitcher.LayoutParams(1000, 1000));
        return imageView;
    }
}
