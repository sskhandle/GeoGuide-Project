package com.cs442_skatkar.geoguidemod1;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.webkit.WebView;
import java.util.ArrayList;
import java.util.Arrays;



public class FavoriteItem extends ActionBarActivity {

    WebView FavoriteItem_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_item);

        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.drawable.ic_launcher);
        menu.setDisplayUseLogoEnabled(true);
        menu.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2E9AFE")));

        // Getting reference to WebView ( wv_place_details ) of the layout activity_place_details
        FavoriteItem_details = (WebView) findViewById(R.id.fav_place_details);

        FavoriteItem_details.getSettings().setUseWideViewPort(false);

        // Getting place reference from the map
        String url_infor = getIntent().getStringExtra("url_info");

        System.out.println("The url infor is : " + url_infor);

        ArrayList<String> infor_list = new ArrayList (Arrays.asList(url_infor.split("\\|")));


        String data = "<html>"+
                "<body><h1><center>"+infor_list.get(0)+"</center></h1>" +
                "<br style='clear:both' />" +
                "<hr />"+
                "<p>Address : " + infor_list.get(1) + "</p>" +
                "<hr>"+
                "<p>Phone : " + infor_list.get(2) + "</p>" +
                "<hr>"+
                "<p>Website : " + infor_list.get(3) + "</p>" +
                "<hr>"+
                "<p>Rating : " + infor_list.get(4) + "</p>" +
                "<hr>"+
                "<p>URL : <a href='" + infor_list.get(5) + "'>" + infor_list.get(5) + "</p>" +
                "</body></html>";

        String mimeType = "text/html";
        String encoding = "utf-8";

        // Setting the data in WebView
        FavoriteItem_details.loadDataWithBaseURL("", data, mimeType, encoding, "");

    }

}
