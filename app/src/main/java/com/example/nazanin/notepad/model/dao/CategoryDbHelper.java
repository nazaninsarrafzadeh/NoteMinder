package com.example.nazanin.notepad.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.nazanin.notepad.model.dto.Category;
import com.example.nazanin.notepad.model.dto.Note;

import java.util.ArrayList;

public class CategoryDbHelper {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private ArrayList<Note> notes;
    public static final String CREATE_TABLE_NOTES="CREATE TABLE CATEGORIES(NAME TEXT,ID INTEGER)";

    public CategoryDbHelper(Context context) {

        this.context=context;
        dbHelper=new DbHelper(context);
        notes=new ArrayList<>();
    }

    public void getCategoryByName(String name){
        db=dbHelper.getReadableDatabase();

    }

    public void insert(Category category){
        db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("id",category.getId());
        values.put("name",category.getName());
        db.insert("CATEGORIES",null,values);
    }

}
