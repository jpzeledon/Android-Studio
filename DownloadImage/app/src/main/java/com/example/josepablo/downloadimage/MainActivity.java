package com.example.josepablo.downloadimage;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;
    Button btnDownloadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        btnDownloadImage = (Button) findViewById(R.id.btnDownloadImage);
        btnDownloadImage.setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View view) {
        DownloadImageTask downloadImageTask = new DownloadImageTask(MainActivity.this);
        downloadImageTask.execute("http://cdn.akc.org/content/article-body-image/housetrain_adult_dog_hero.jpg");
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        ProgressDialog progressDialog;
        Context context;

        public DownloadImageTask(Context context) {

            this.context = context;
            progressDialog = new ProgressDialog(context);

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Downloading Image.... Please Wait");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            imageView.setImageBitmap(bitmap);
            if (progressDialog.isShowing()) {

                progressDialog.dismiss();
            }
        }

        //This function takes de URL string and open it in a inputSrteam then is decoded in the bitmap
        @Override
        protected Bitmap doInBackground(String... params) {

            String stringUrl = params[0];
            Bitmap bitmap = null;

            try{
                URL url = new URL(stringUrl);
                InputStream inputStream = url.openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch (Exception e) {

                e.printStackTrace();
            }
            return bitmap;
        }



    }




}
