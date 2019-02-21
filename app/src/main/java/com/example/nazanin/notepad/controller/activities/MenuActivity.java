package com.example.nazanin.notepad.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.nazanin.notepad.R;

import ir.adad.client.Adad;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Adad.initialize(getApplicationContext());
        setContentView(R.layout.activity_menu);

    }


    public void showNotepadlist(View view) {
        Intent intent=new Intent(MenuActivity.this,NoteManagerActivity.class);
        startActivity(intent);

    }

    public void showChecklist(View view) {
        Intent intent=new Intent(MenuActivity.this,CheckListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
