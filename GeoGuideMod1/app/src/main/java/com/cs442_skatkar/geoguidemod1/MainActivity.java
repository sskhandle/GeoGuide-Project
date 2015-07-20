package com.cs442_skatkar.geoguidemod1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cs442_skatkar.geoguidemod1.Adapter.CategoryListAdapter;
import com.cs442_skatkar.geoguidemod1.beans.Category;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import android.support.v7.app.ActionBarActivity;
import android.view.MenuInflater;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity implements LocationListener{


    List<Category> categories;

    private static final String TAG1 = "MainActivity";


    double mLatitude=0;
    double mLongitude=0;

    public float main_radius = 0;

    // used to store app title
    private CharSequence mTitle;



    LocationManager mLocationManager;

    GoogleMap mMap;

    private void setCategories() {

        Category c1 = new Category(1, "ATM");
        Category c2 = new Category(2, "Bank");
        Category c3 = new Category(3, "Airport");
        Category c4 = new Category(4, "Church");
        Category c5 = new Category(5, "Doctor");
        Category c6 = new Category(6, "Hospital");
        Category c7 = new Category(7, "Bus Station");
        Category c8 = new Category(8, "Movie Theater");
        Category c9 = new Category(9, "Restaurant");

        categories = new ArrayList<Category>();
        categories.add(c1);
        categories.add(c2);
        categories.add(c3);
        categories.add(c4);
        categories.add(c5);
        categories.add(c6);
        categories.add(c7);
        categories.add(c8);
        categories.add(c9);

    }


    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.drawable.ic_launcher);
        menu.setDisplayUseLogoEnabled(true);
        menu.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2E9AFE")));


        main_radius = 3000;


        final ListView productListView = (ListView)findViewById(R.id.list_category);
        setCategories();
        CategoryListAdapter categoryListAdapter = new CategoryListAdapter(this, categories);
        productListView.setAdapter(categoryListAdapter);

        //Button btnFav = (Button)findViewById(R.id.button_fav);
        TextView current_address_tv = (TextView)findViewById(R.id.address_tv);

        // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location From GPS
        Location location = getLastKnownLocation();


        if(location!=null){
            onLocationChanged(location);
        }

        locationManager.requestLocationUpdates(provider, 20000, 0, this);

        Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());

        List<Address> matches = null;
        try {
            System.out.println("the lat is : " + mLatitude);
            System.out.println("the long is : " + mLongitude);

            matches = geoCoder.getFromLocation(mLatitude, mLongitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // System.out.println("the matches is : " + matches.get(0));

        Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
        String address = null;
        if (bestMatch != null) {
            address = bestMatch.getAddressLine(0);
        }
        //System.out.println("the adress is : " + address);
        current_address_tv.setText("You are Near: " + address);

        productListView.setClickable(true);
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Category c = (Category) parent.getAdapter().getItem(position);
                String tmp = c.getName().toLowerCase();
                String selectedType = tmp.replaceAll(" ", "_").toLowerCase();

                //Toast.makeText(getBaseContext(), selectedType, Toast.LENGTH_SHORT).show();

                StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                sb.append("location="+mLatitude+","+mLongitude);
                sb.append("&radius="+main_radius);
                sb.append("&types="+selectedType);
                sb.append("&sensor=true");
                sb.append("&key=AIzaSyB5J9XOlB5aVwiDBKuabLUSt3nVEmmVpEE");

                Intent mapIntent = new Intent(getApplicationContext(), MapActivity.class);
                Bundle b = new Bundle();
                b.putString("url_link",sb.toString());
                mapIntent.putExtras(b);
                startActivity(mapIntent);
            }
        });


        Button btnFind;

        // Getting reference to Find Button
        btnFind = ( Button ) findViewById(R.id.btn_find);

        btnFind.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText user_input = (EditText)findViewById(R.id.user_input);
                String type = user_input.getText().toString().toLowerCase();

                StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                sb.append("location="+mLatitude+","+mLongitude);
                sb.append("&radius="+main_radius);
                sb.append("&types="+type);
                sb.append("&sensor=true");
                sb.append("&key=AIzaSyB5J9XOlB5aVwiDBKuabLUSt3nVEmmVpEE");
                //AIzaSyB5J9XOlB5aVwiDBKuabLUSt3nVEmmVpEE

                Intent mapIntent = new Intent(getApplicationContext(), MapActivity.class);
                Bundle b = new Bundle();
                b.putString("url_link",sb.toString());
                mapIntent.putExtras(b);
                startActivity(mapIntent);

            }
        });

        Intent mIntent = getIntent();
        main_radius = mIntent.getFloatExtra("radius", main_radius);

        //TextView
        TextView tV = (TextView)findViewById(R.id.textView_main);
//        tV.setText("Current Radius is: " +Integer.toString(main_radius));
        tV.setText("Current Radius is: " +Float.toString(main_radius/1000)+ "km");



        // might cause some probs
        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        }else { // Google Play Services are available

            // Getting LocationManager object from System Service LOCATION_SERVICE
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            criteria = new Criteria();

            // Getting the name of the best provider
            provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location From GPS
            location = locationManager.getLastKnownLocation(provider);

            if (location != null) {
                onLocationChanged(location);
            }

            locationManager.requestLocationUpdates(provider, 20000, 0, this);

        }


        if (location != null) {
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();
            Log.d(TAG1, "ZZZZZZZZZZ: " + new LatLng(mLatitude, mLongitude));
        }


    }


    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                // fragment = new HomeFragment();
                Intent i= new Intent(MainActivity.this,MainActivity.class);
                startActivity(i);
                break;
            case 1:
                System.out.println("Hello!!!!!");

                // fragment = new FindPeopleFragment();
                Intent j= new Intent(MainActivity.this,fav_activity.class);
                startActivity(j);
                break;
            case 2:
                // fragment = new PhotosFragment();
                break;

            default:
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
        LatLng latLng = new LatLng(mLatitude, mLongitude);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater mif = getMenuInflater();
        mif.inflate(R.menu.main_activity_action, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //method returned when the user chooses something from the options menu
        //option for changing the Map Type programatically
        switch (item.getItemId()) {
            case R.id.change_radius:
                Intent radius_intent = new Intent(getApplicationContext(), HelloWorldActivity.class);
                radius_intent.putExtra("radius", main_radius);
                radius_intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                this.startActivity(radius_intent);
                finish();
                break;

            case R.id.fav_list:
                Intent intent = new Intent(getApplicationContext(), fav_activity.class);
                // Start next activity
                startActivity(intent);
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}