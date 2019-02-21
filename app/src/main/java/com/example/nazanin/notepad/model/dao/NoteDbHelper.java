package com.example.nazanin.notepad.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nazanin.notepad.model.dto.CheckList;
import com.example.nazanin.notepad.model.dto.Note;

import java.util.ArrayList;

public class NoteDbHelper {

    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private ArrayList<Note> notes;
    public static final String CREATE_TABLE_NOTES="CREATE TABLE NOTES(ID INTEGER PRIMARY KEY AUTOINCREMENT,PATH TEXT,TITLE TEXT,DATEANDTIME TEXT)";

    public NoteDbHelper(Context context) {

        this.context=context;
        dbHelper=new DbHelper(context);
        notes=new ArrayList<>();
    }

    public void insertNote(Note note){
        db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("title",note.getTitle());
        values.put("path",note.getPath());
        values.put("dateandtime",note.getDate());
        db.insert("NOTES",null,values);
    }

    public void delete(String name){
        db=dbHelper.getWritableDatabase();
        db.delete("NOTES","TITLE='"+name+"'",null);
    }

}
