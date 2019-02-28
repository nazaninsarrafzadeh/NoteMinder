package com.example.nazanin.notepad.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.nazanin.notepad.model.dto.Category;
import com.example.nazanin.notepad.model.dto.Note;

import java.util.ArrayList;

public class CategoryDbHelper {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private ArrayList<String> categories;

    public static final String CREATE_TABLE_CATEGORIES="CREATE TABLE CATEGORIES(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT)";

    public CategoryDbHelper(Context context) {

        this.context=context;
        dbHelper=new DbHelper(context);
        categories=new ArrayList<>();
    }

    public int getCategoryByName(String name){
        db=dbHelper.getReadableDatabase();
        int category_id=0;
        Cursor categoryCursor=db.rawQuery("SELECT ID FROM CATEGORIES WHERE NAME='"+name+"'",null);

        categoryCursor.moveToFirst();
        category_id = categoryCursor.getInt(categoryCursor.getColumnIndex("ID"));
        return category_id;
    }

    public void insert(Category category){
        db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",category.getName());
        db.insert("CATEGORIES",null,values);
    }

    public ArrayList<String> getcategories(){
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT NAME FROM CATEGORIES",null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name= cursor.getString(cursor.getColumnIndex("NAME"));
                categories.add(name);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return categories;
    }


}
