package com.example.nazanin.notepad.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="notepad.db";
    public static final int DATABASE_VERSION=1;
    private SQLiteDatabase db;

    public DbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db=db;
        db.execSQL(CheckListDbHelper.CREATE_TABLE_CheckLists);
        db.execSQL(NoteDbHelper.CREATE_TABLE_NOTES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
