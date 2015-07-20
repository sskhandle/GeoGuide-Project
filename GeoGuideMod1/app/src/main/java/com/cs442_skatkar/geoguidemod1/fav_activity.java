package com.cs442_skatkar.geoguidemod1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class fav_activity extends ActionBarActivity {


    private TextView textTxt;

    private String text;

    private ArrayList<String> favourite_list;

    private SharedPreference sharedPreference;

    ArrayAdapter<String> adapter;

    Activity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_activity);

        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.drawable.ic_launcher);
        menu.setDisplayUseLogoEnabled(true);
        menu.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2E9AFE")));


        sharedPreference = new SharedPreference();

        //TextView tv = (TextView) findViewById(R.id.result_tv);

        ListView fav_list = (ListView)findViewById(R.id.list_favorite_place);

        //text = sharedPreference.getValue(context);
        favourite_list = sharedPreference.getValue(context);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, favourite_list);
        fav_list.setAdapter(adapter);


        fav_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View itemView, int itemPosition, long itemId)
            {
                String fav_item_data=(String)listView.getItemAtPosition(itemPosition);
                //Toast.makeText(fav_activity.this, "Long Click to Remove the Place from List", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(fav_activity.this, FavoriteItem.class);
                Bundle b = new Bundle();
                b.putString("url_info",fav_item_data);
                i.putExtras(b);
                startActivity(i);
            }
        });

        fav_list.setClickable(true);
        fav_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String toRemove = adapter.getItem(position);
                adapter.remove(toRemove);
                adapter.notifyDataSetChanged();
                Place_Info.favourite_list.remove(toRemove);
                sharedPreference.clearSharedPreference(context);
                sharedPreference.save(context, Place_Info.favourite_list);

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fav_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
