package com.example.nazanin.notepad.controller.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nazanin.notepad.R;
import com.example.nazanin.notepad.controller.FileManager;
import com.example.nazanin.notepad.model.dao.NoteDbHelper;
import com.example.nazanin.notepad.model.dto.Note;

import java.text.SimpleDateFormat;
import java.util.Date;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class NotepadActivity extends AppCompatActivity {
    private EditText noteTextView;
    private EditText titleTextView;
    private String filename,note;
    private FileManager fileManager;
    private Context context;
    private Note notepad;
    private SimpleDateFormat dateFormat;
    private NoteDbHelper noteDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        context=this;
        dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fileManager=new FileManager();
        notepad=new Note();
        noteDbHelper=new NoteDbHelper(context);
        //find
        titleTextView=findViewById(R.id.titleTextView);
        noteTextView=findViewById(R.id.note);
        //


    }


    public void save(View view) {
        filename = titleTextView.getText().toString();
        note = noteTextView.getText().toString();
        notepad.setText(note);
        notepad.setTitle(filename);
        notepad.setDate(dateFormat.format(new Date()));

        if (!isEmpty(filename,note)) {
            notepad.setPath(fileManager.saveFile(filename, note, context));
            noteDbHelper.insertNote(notepad);
            Toast.makeText(this, "یادداشت ذخیره شد", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private boolean isEmpty(String filename,String note){
        if (TextUtils.isEmpty(filename)) {
            Toast.makeText(this, "لطفا عنوان یادداشت را انتخاب کنید", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(note)) {
            Toast.makeText(this, "لطفا متن یادداشت را بنویسید", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }


}
