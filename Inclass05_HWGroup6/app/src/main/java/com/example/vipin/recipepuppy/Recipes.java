package com.example.vipin.recipepuppy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Recipes extends AppCompatActivity {

    String dish;
    int index = 0;
    Bitmap bitmap1;
    String url;
    ProgressDialog progressBar;
    TextView loadingtv;
    ImageButton btn1,btn2,btn3,btn4;
    Button finish;
    TextView titletv;
    TextView ingredientstv;
    TextView urltv;
    ImageView image;
    ArrayList<Person> persons = new ArrayList<>();
    ArrayList<String> ingredientslist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        //progressBar= (ProgressBar) findViewById(R.id.progress);
        btn1 = (ImageButton) findViewById(R.id.btn1);
        btn2 = (ImageButton) findViewById(R.id.btn2);
        btn3 = (ImageButton) findViewById(R.id.btn3);
        image = (ImageView) findViewById(R.id.imageView);
        btn4 = (ImageButton) findViewById(R.id.btn4);
        titletv= (TextView) findViewById(R.id.titletv);
        finish=(Button) findViewById(R.id.finish);
        ingredientstv= (TextView) findViewById(R.id.ingredientstv);
        urltv= (TextView) findViewById(R.id.urltv);
        final ImageView image = (ImageView) findViewById(R.id.imageView);
        ingredientslist=getIntent().getStringArrayListExtra("list");
        dish = getIntent().getStringExtra("dish");
        url=getIntent().getStringExtra("url");
        new Asyncwork().execute(url);

        //setContentView(R.layout.activity_recipes);

        urltv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = persons.get(index).getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index != 0){
                    titletv.setText("Title: " +persons.get(0).getTitle());

                ingredientstv.setText(persons.get(0).getIngredients());
                urltv.setText("Url: " +persons.get(0).getUrl());

                new ImageLoad().execute(persons.get(0).getImage());
                image.setImageBitmap(bitmap1);
                index = 0;
                }
                else {
                    Toast.makeText(getApplicationContext(), "First Recipe reached", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(index != persons.size() - 1){

                    index = persons.size() - 1;
                    titletv.setText("Title: " +persons.get(index).getTitle());
                ingredientstv.setText(persons.get(index).getIngredients());
                urltv.setText("Url: " +persons.get(index).getUrl());
                new ImageLoad().execute(persons.get(index).getImage());
                image.setImageBitmap(bitmap1);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Last Recipe reached", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (index != 0) {
                    index--;
                    titletv.setText("Title: " +persons.get(index).getTitle());
                    ingredientstv.setText(persons.get(index).getIngredients());
                    urltv.setText("Url: " +persons.get(index).getUrl());

                    new ImageLoad().execute(persons.get(index).getImage());
                    image.setImageBitmap(bitmap1);
                } else {
                    Toast.makeText(getApplicationContext(), "First Recipe reached", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(index < persons.size() ) {
                    index++;
                    titletv.setText("Title: " +persons.get(index).getTitle());
                    ingredientstv.setText(persons.get(index).getIngredients());
                    urltv.setText("Url: " +persons.get(index).getUrl());

                    new ImageLoad().execute(persons.get(index).getImage());
                    image.setImageBitmap(bitmap1);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Last Recipe reached",Toast.LENGTH_SHORT).show();
                }
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Recipes.this,MainActivity.class));
                finish();
            }
        });

    }

    class Asyncwork extends AsyncTask<String, Integer, ArrayList<Person>> {
        @Override
        protected ArrayList<Person> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                publishProgress(50);
                con.setRequestMethod("GET");
                con.connect();
                int status_Code = con.getResponseCode();
                if(status_Code == HttpURLConnection.HTTP_OK){
                    BufferedReader reader= new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = reader.readLine();
                    while(line!=null){
                      sb.append(line + "\n");
                        line = reader.readLine();

                    }
                   publishProgress(100);
                    return Json.PersonJSONParser.parsePersons(sb.toString());
                    }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar=new ProgressDialog(Recipes.this);
            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();


        }

        @Override
        protected void onPostExecute(ArrayList<Person> aVoid) {
            super.onPostExecute(aVoid);

            if(aVoid!= null){
                progressBar.dismiss();
                persons = aVoid;
                titletv.setText("Title: " +persons.get(0).getTitle());
                ingredientstv.setText(persons.get(0).getIngredients());
                urltv.setText("Url: " +persons.get(0).getUrl());

                try {
                    Bitmap result = new ImageLoad().execute(persons.get(0).getImage()).get();
                    image.setImageBitmap(result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                index = 0;

            }
            else{
                setResult(2);
                finish();
            }
            }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        //    progressBar.setProgress(values[0]);
        }


    }

    private class ImageLoad extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap!=null){
                 bitmap1=bitmap;
            }
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url= null;
            try {
                url = new URL(strings[0]);
                HttpURLConnection con= null;
                con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                Bitmap image= BitmapFactory.decodeStream(con.getInputStream());
                return image;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }
    }
}
