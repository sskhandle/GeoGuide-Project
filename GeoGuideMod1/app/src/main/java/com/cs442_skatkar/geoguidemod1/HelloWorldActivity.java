package com.cs442_skatkar.geoguidemod1;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * Created by Saurabh on 4/17/2015.
 */
public class HelloWorldActivity extends ActionBarActivity implements OnSeekBarChangeListener{

    private static final String TAG1 = "MainActivity";
//==================================================================================================
    private SeekBar bar; // declare seekbar object variable
    // declare text label objects
    private TextView textProgress,textAction;
//==================================================================================================

    float radius_value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radius_seekbar);

        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.drawable.ic_launcher);
        menu.setDisplayUseLogoEnabled(true);
        menu.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2E9AFE")));


        Intent mIntent = getIntent();
        radius_value = mIntent.getFloatExtra("radius", 0);
        Log.d(TAG1, "TTTTTTT: "+radius_value);

        //TextView
        TextView tV = (TextView)findViewById(R.id.textView1);
//        tV.setText("Current Radius is: " +Integer.toString(radius_value));
        tV.setText("Current Radius is: " +Float.toString(radius_value/1000)+ "km");


//==================================================================================================
        //SeekBar
        bar = (SeekBar)findViewById(R.id.seekBar1); // make seekbar object
        bar.setOnSeekBarChangeListener(this); // set seekbar listener.
        // since we are using this class as the listener the class is "this"

        // make text label for progress value
        textProgress = (TextView)findViewById(R.id.textViewProgress);
        // make text label for action
        textAction = (TextView)findViewById(R.id.textViewAction);

        float value = bar.getProgress();
        Log.d(TAG1, "Progress Radius "+value);
//==================================================================================================
        //Button
        Button save_button = (Button)findViewById(R.id.save_radius_btn);

        save_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                radius_value = bar.getProgress();
                Intent radius_intent = new Intent(getApplicationContext(), MainActivity.class);
                radius_intent.putExtra("radius", radius_value);
                startActivity(radius_intent);
                finish();
            }
        });

        Button cancel_button = (Button)findViewById(R.id.cancel_button);

        cancel_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent cancel_intent = new Intent(getApplicationContext(), MainActivity.class);
                cancel_intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(cancel_intent);
                finish();
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {

        // change progress text label with current seekbar value
        textProgress.setText("The value is: "+(float)progress/1000+ " km");
        // change action text label to changing
        textAction.setText("changing");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub
        textAction.setText("starting to track touch");

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub
        seekBar.setSecondaryProgress(seekBar.getProgress());
        textAction.setText("ended tracking touch");
    }
//==================================================================================================
}
