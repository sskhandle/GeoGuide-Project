package com.cs442_skatkar.geoguidemod1;

/**
 * Created by Trofey on 4/21/2015.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.io.IOException;
import java.util.ArrayList;

public class SharedPreference {

    public static final String PREFS_NAME = "AOP_PREFS";
    public static final String PREFS_KEY = "AOP_PREFS_String";

    public SharedPreference() {
        super();
    }

    public void save(Context context, ArrayList<String> currentTasks) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        try {
            editor.putString(PREFS_KEY, ObjectSerializer.serialize(currentTasks)); //3
        } catch (IOException e) {
            e.printStackTrace();
        }

        editor.commit(); //4
    }

    public ArrayList<String> getValue(Context context) {
        SharedPreferences settings;
        String text;
        ArrayList<String> favorites = new ArrayList<String>();

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        try {
            favorites = (ArrayList<String>) ObjectSerializer.deserialize(settings.getString(PREFS_KEY, ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return favorites;
    }

    public void clearSharedPreference(Context context) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    public void removeValue(Context context) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(PREFS_KEY);
        editor.commit();
    }
}
