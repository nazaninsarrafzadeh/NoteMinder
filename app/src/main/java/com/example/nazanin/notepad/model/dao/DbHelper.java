package com.example.nazanin.notepad.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="notepad.db";
    public static final int DATABASE_VERSION=1;
    private SQLiteDatabase db;
    private ArrayList<String> categoryTitles;

    public DbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db=db;
        db.execSQL(CheckListDbHelper.CREATE_TABLE_CheckLists);
        db.execSQL(NoteDbHelper.CREATE_TABLE_NOTES);
        db.execSQL(CategoryDbHelper.CREATE_TABLE_CATEGORIES);
        fillCategoriesTable();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void fillCategoriesTable(){
        categoryTitles = new ArrayList<>();
        categoryTitles.add("همه");
        categoryTitles.add("کار");
        categoryTitles.add("خرید");
        categoryTitles.add("شخصی");

        for (String cat:categoryTitles){
            ContentValues values=new ContentValues();
            values.put("name",cat);
            db.insert("CATEGORIES",null,values);
        }

    }
}
