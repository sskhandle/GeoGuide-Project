package com.cs442_skatkar.geoguidemod1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.json.JSONObject;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Place_Info extends ActionBarActivity {

    MyDBHandler lvdbHandler;
    WebView mWvPlaceDetails;
    String p_name = "";
    String p_vincinity = "";
    String p_address = "";
    String p_phone = "";
    String p_website = "";
    String p_rating = "";
    String p_url = "";

    public static final ArrayList favourite_list = new ArrayList<String>();

    private EditText textEtxt;
    private Button saveButton;
    private Button activity2Button;

    private String text;

    private SharedPreference sharedPreference;

    Activity context = this;

    private ShareActionProvider mShareActionProvider;

    private static final String TAG1 = "Place_Info";



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.drawable.ic_launcher);
        menu.setDisplayUseLogoEnabled(true);
        menu.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2E9AFE")));


        sharedPreference = new SharedPreference();


        // Getting reference to WebView ( wv_place_details ) of the layout activity_place_details
        mWvPlaceDetails = (WebView) findViewById(R.id.wv_place_details);

        mWvPlaceDetails.getSettings().setUseWideViewPort(false);

        // Getting place reference from the map
        String reference = getIntent().getStringExtra("reference");

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
        sb.append("reference="+reference);
        sb.append("&sensor=true");
        sb.append("&key=AIzaSyB5J9XOlB5aVwiDBKuabLUSt3nVEmmVpEE");

        // Creating a new non-ui thread task to download Google place details
        PlacesTask placesTask = new PlacesTask();

        // Invokes the "doInBackground()" method of the class PlaceTask
        placesTask.execute(sb.toString());

    }

    public ArrayList<String> removeDuplicates(ArrayList<String> list) {

        ArrayList<String> result = new ArrayList<>(); // Store unique items in result.
        HashSet<String> set = new HashSet<>(); // Record encountered Strings in HashSet.
        // Loop over argument list.
        for (String item : list) {
            // If String is not in set, add it to the list and the set.
            if (!set.contains(item)) {
                result.add(item);
                set.add(item);
            }
        }
        return result;
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
    /** A class, to download Google Place Details */
    private class PlacesTask extends AsyncTask<String, Integer, String>{
        String data = null;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        // *** the parameter "result" is exaclty same with "data" returned by "doInBackground" method ***
        @Override
        protected void onPostExecute(String result){
            ParserTask parserTask = new ParserTask();

            // Start parsing the Google place details in JSON format
            // Invokes the "doInBackground()" method of the class ParseTask
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Place Details in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, HashMap<String,String>>{

        JSONObject jObject;

        // Invoked by execute() method of this object
        @Override
        protected HashMap<String,String> doInBackground(String... jsonData) {

            HashMap<String, String> hPlaceDetails = null;
            Place_Info_JSON placeInfoJson = new Place_Info_JSON();

            try{
                jObject = new JSONObject(jsonData[0]);

                // Start parsing Google place details in JSON format
                hPlaceDetails = placeInfoJson.parse(jObject);

            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            return hPlaceDetails;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(HashMap<String,String> hPlaceDetails){

            String name = hPlaceDetails.get("name");
            String icon = hPlaceDetails.get("icon");
            String vicinity = hPlaceDetails.get("vicinity");
            String lat = hPlaceDetails.get("lat");
            String lng = hPlaceDetails.get("lng");
            String formatted_address = hPlaceDetails.get("formatted_address");
            String formatted_phone = hPlaceDetails.get("formatted_phone");
            String website = hPlaceDetails.get("website");
            String rating = hPlaceDetails.get("rating");
            String international_phone_number = hPlaceDetails.get("international_phone_number");
            String url = hPlaceDetails.get("url");

            String mimeType = "text/html";
            String encoding = "utf-8";


            p_name = name;
            p_vincinity = vicinity;
            p_address = formatted_address;
            p_phone = formatted_phone;
            p_website = website;
            p_rating = rating;
            p_url = url;

            String data = "<html>"+
                    "<body><img style='float:left' src="+icon+" /><h1><center>"+name+"</center></h1>" +
                    "<br style='clear:both' />" +
                    "<hr />"+
                    "<p><b>Vicinity : </b>" + vicinity + "</p>" +
                    "<hr>"+
                    "<p><b>Location : </b>" + lat + "," + lng + "</p>" +
                    "<hr>"+
                    "<p><b>Address : </b>" + formatted_address + "</p>" +
                    "<hr>"+
                    "<p><b>Phone : </b>" + formatted_phone + "</p>" +
                    "<hr>"+
                    "<p><b>Website : </b>" + website + "</p>" +
                    "<hr>"+
                    "<p><b>Rating : </b>" + rating + "</p>" +
                    "<hr>"+
                    "<p><b>International Phone : </b>" + international_phone_number + "</p>" +
                    "<hr>"+
                    "<p><b>URL : </b><a href='" + url + "'>" + url + "</p>" +
                    "<hr>"+
                    "</body></html>";

            // Setting the data in WebView
        mWvPlaceDetails.loadDataWithBaseURL("", data, mimeType, encoding, "");
        mShareActionProvider.setShareIntent(new ParserTask().getDefaultShareIntent(name, url, formatted_phone));

    }

    public Intent getDefaultShareIntent(String name1, String url1, String phone1) {
        // TODO Auto-generated method stub

        String msg = "Name: "+name1+" \nURL: " +url1+ " \nPhone No: " +phone1;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, name1.toUpperCase());
        intent.putExtra(Intent.EXTRA_TEXT,"\n" +msg);
        return intent;
    }
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /** Inflating the current activity's menu with res/menu/items.xml */
        getMenuInflater().inflate(R.menu.items, menu);

        /** Getting the actionprovider associated with the menu item whose id is share */
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu.findItem(R.id.share));
        /** Setting a share intent */
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.share:
                Toast.makeText(this, "Share details with a friend!!!", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.add_fav:
                text  = "➤ " +p_name + " | " + p_vincinity  + " | " + p_phone + " | " + p_website + " | " + p_rating + " | " + p_url;
                favourite_list.add(text);
                sharedPreference.save(context, removeDuplicates(favourite_list)); // Save the text in SharedPreference
                Log.d(TAG1,"Yo1");
                Toast.makeText(this, "Added to favourites!!!", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.fav_list:
                Intent intent = new Intent(context, fav_activity.class);
                // Start next activity
                startActivity(intent);
                Log.d(TAG1,"Yo2");
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}