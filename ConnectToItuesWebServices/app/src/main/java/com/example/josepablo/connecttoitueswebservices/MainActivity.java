package com.example.josepablo.connecttoitueswebservices;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.josepablo.connecttoitueswebservices.model.ItunesStuff;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textArtistName;
    TextView textType;
    TextView textKind;
    TextView textCollectionName;
    TextView textTrackName;
    ImageView imgArt;
    String imgURL;
    Button btnGetData;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textArtistName = (TextView) findViewById(R.id.textArtistName);;
        textType = (TextView) findViewById(R.id.textType);
        textKind = (TextView) findViewById(R.id.textKind);
        textCollectionName = (TextView) findViewById(R.id.textCollectionName);
        textTrackName = (TextView) findViewById(R.id.textTrackName);
        imgArt = (ImageView) findViewById(R.id.imgArt);
        btnGetData = (Button) findViewById(R.id.btnGetData);

        btnGetData.setOnClickListener(MainActivity.this);

    }

    @Override
    public void onClick(View view) {

        JSONItunesStuffTask jsonItunesStuffTask = new JSONItunesStuffTask(MainActivity.this);
        jsonItunesStuffTask.execute();
    }

    private class JSONItunesStuffTask extends AsyncTask<String, Void, ItunesStuff>{

        Context context;
        ProgressDialog progressDialog;

        public JSONItunesStuffTask(Context context){

            this.context = context;
            progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            progressDialog.setTitle("Downloading Info From Itunes.....Please Wait");
            progressDialog.show();
        }

        @Override
        protected  ItunesStuff doInBackground(String... params){

            ItunesStuff itunesStuff = new ItunesStuff();
            ItunesHTTPClient itunesHTTPClient = new ItunesHTTPClient();
            String data = (itunesHTTPClient.getItunesStuffData());

            try{

                itunesStuff = JsonItunesParser.getItunesStuff(data);
                imgURL = itunesStuff.getArtistViewURL();
                bitmap = (itunesHTTPClient.getBitmapFromURL(imgURL));

            }catch (Throwable t){

                t.printStackTrace();
            }

            return  itunesStuff;
        }

        @Override
        protected void onPostExecute(ItunesStuff itunesStuff){
            super.onPostExecute(itunesStuff);

            textArtistName.setText(itunesStuff.getArtistName());
            textType.setText(itunesStuff.getType());
            textKind.setText(itunesStuff.getKind());
            textCollectionName.setText(itunesStuff.getCollectionName());
            textTrackName.setText(itunesStuff.getTrackName());
            imgArt.setImageBitmap(bitmap);

            if(progressDialog.isShowing()){
                progressDialog.dismiss();;
            }
        }

    }
}
