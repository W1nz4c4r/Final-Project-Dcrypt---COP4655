package com.rmoralessolo2016.dcrypt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;

public class coin_Information_activity extends AppCompatActivity {
    String url1, url2, url3, Graph_Link;
    Button go_backBTN;
    TextView Nick_name, coin_name, price, type, status, news_title1, desc1, title2, desc2,link2, link1;
    ImageView coinIMG, coin_Stats, news_one_images, news_two_images;
    utils utils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_information);

        //get intent information
        Intent intent = getIntent();
        url1 = intent.getStringExtra("url1");
        url2 = intent.getStringExtra("url2");

        //declare variables
        go_backBTN = (Button) findViewById(R.id.search_back_button);
        Nick_name = (TextView) findViewById(R.id.SH_coinNick);
        coin_name = (TextView) findViewById(R.id.SH_coinTittle);
        coinIMG = (ImageView) findViewById(R.id.SH_coinIMG);
        price = (TextView) findViewById(R.id.SH_price_value);
        coin_Stats = (ImageView) findViewById(R.id.coin_stats);
        type = (TextView) findViewById(R.id.coin_type_value);
        status = (TextView) findViewById(R.id.status_value);
        news_title1 = (TextView) findViewById(R.id.news_title_one);
        news_one_images = (ImageView) findViewById(R.id.news_one_img);
        desc1 = (TextView) findViewById(R.id.news_one_desc);
        title2 = (TextView) findViewById(R.id.news_title_two);
        news_two_images = (ImageView) findViewById(R.id.news_two_img);
        desc2 = (TextView) findViewById(R.id.news_two_desc);
        link2 = (TextView)findViewById(R.id.link2) ;
        link1 = (TextView)findViewById(R.id.link1) ;


        Log.e("TAG", "url1 : "+ url1);
        Log.e("TAG", "url2 : "+ url2);

        go_backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sending user back to search
                startActivity(new Intent(getApplicationContext(), Favorites.class));
            }
        });

        //call getInformation
        getInformation();

    } // end of onCreate

    private void getNews(String name) {
        Log.e("TAG", "on get news");


        RequestQueue queue0_1 = Volley.newRequestQueue(this);
        String img_url = "https://gnews.io/api/v4/search?q=crypto&lang=en&token=ffa855b057679fddbdf85894970c6ef1";
        StringRequest stringRequest =  new StringRequest(Request.Method.GET, img_url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        if(response!=null){
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                String results = jsonObject.getString("articles");
                                JSONArray news_section = new JSONArray(results);
                                ArrayList<String> IMG = new ArrayList<String>();
                                for(int i = 0; i <2 ; i++){
                                    int rand = (int) (Math.random() * (10 - 1)) + 1;
                                    JSONObject index_results = news_section.getJSONObject(rand);
                                    IMG.add(index_results.getString("image"));


                                }
                                //Log.e("TAG","on image " + IMG.get(0));
                                Log.e("TAG",IMG.get(0));
                                Picasso.get().load(IMG.get(0)).into(news_one_images);
                                Picasso.get().load(IMG.get(1)).into(news_two_images);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR","error => "+error.toString());
                    }
                });
        queue0_1.add(stringRequest);

        //final news api

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = getString(R.string.newsKEY) + "&language=en&q="+name;

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        if(response!=null){
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                String results = jsonObject.getString("results");
                                JSONArray JSresults = new JSONArray(results);
                                ArrayList<String> titles = new ArrayList<String>();
                                ArrayList<String> desc = new ArrayList<String>();
                                ArrayList<String> link = new ArrayList<String>();

                                for(int i = 0; i <2 ; i++) {
                                    JSONObject index_results = JSresults.getJSONObject(i);
                                    titles.add(index_results.getString("title"));
                                    desc.add(index_results.getString("description"));
                                    link.add(index_results.getString("link"));

                                }
                                Log.e("TAG","on titles");
                                news_title1.setText("\t -" + titles.get(0));
                                title2.setText("\t -" + titles.get(1));
                                desc1.setText(desc.get(0));
                                desc2.setText(desc.get(1));
                                link1.setText(link.get(0));
                                link1.setMovementMethod(LinkMovementMethod.getInstance());
                                link2.setText(link.get(1));
                                link2.setMovementMethod(LinkMovementMethod.getInstance());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR","error => "+error.toString());
                    }
                });

        queue.add(getRequest);

    }

    private void getInformation(){
        //get the secondary information first
        RequestQueue queue2 = Volley.newRequestQueue(this);
        StringRequest stringRequest2 =  new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                Graph_Link = response2;
                Log.e("TAG", response2);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong, please go back and check your input", Toast.LENGTH_LONG).show();
            }
        });
        queue2.add(stringRequest2);

        try
        {
            //sleep so it doesn't block all the calls to the api being made
            Thread.sleep(150);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }


        //request the information
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                HandleResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong, please go back and check your input", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);
    } //end of getInformation

    private void HandleResponse(String response){
        //check if response is null
        if(response != null){
            try{
                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonObject1 = new JSONObject(Graph_Link);

                String name = jsonObject.getString("name");
                String id = jsonObject.getString("id");
                String details = jsonObject.getString("details");
                String stat = jsonObject.getString("status");
                String last = jsonObject1.getString("last");


                JSONObject desc = new JSONObject(details);
                String type_value = desc.getString("type");
                //change names for coin images
                if(name.equals("Ether")){ name = "Ethereum";     }
                if(name.equals("Ether Classic")){  name = "Ethereum Classic";}
                if (name.equals("Ren")){ name ="republic protocol"; }
                if(name.equals("Origin Token")){ name = "Origin Protocol"; }
                if(name.equals("Crypto.com Coin")){ name ="Cryptocom chain";}
                if(name.equals("Ankr")){ name = "Ankr Network";}
                if(name.equals("Badger DAO")){name = "Badger"; }
                if(name.equals("IoTeX (ERC-20)")){ name = "IoTeX"; }
                if(name.equals("Synthetix")){ name = "Synthetix Network Token"; }
                if (name.equals("ARPA")){ name ="ARPA Chain"; }
                if(name.equals("Fetch.ai")){ name = "Fetch ai"; }
                if(name.equals("LCX Token")){ name = "LCX"; }

                url3 = getString(R.string.iconsURL) + id.toLowerCase() + "-" + name.replace(" ","-").toLowerCase() + ".png";
                Log.e("TAG", url3);
                coin_name.setText(name);
                Nick_name.setText(id);
                price.setText("$" + last);
                type.setText(type_value);
                status.setText(stat);
                Picasso.get().load(url3).into(coinIMG);
                utils.fetchSvg(this, getString(R.string.linies_URL) + id.toLowerCase() + "-" + name.replace(" ","-").toLowerCase() +".svg", coin_Stats);
                getNews(name);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    } //end of handle response

} // end of coin_Information_activity