package com.example.nazanin.notepad.controller;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class LayoutManager {


    public void storeMode(Context context,boolean viewMode){
        SharedPreferences layout=context.getSharedPreferences("viewMode",MODE_PRIVATE);
        SharedPreferences.Editor editor=layout.edit();
        editor.putBoolean("viewMode",viewMode);
        editor.apply();
    }

    public boolean listLayout(Context context){
        SharedPreferences loginData=context.getSharedPreferences("viewMode",MODE_PRIVATE);
        boolean viewMode=loginData.getBoolean("viewMode",false);
        return viewMode;
    }

}
