package com.example.nazanin.notepad.controller.activities;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nazanin.notepad.R;
import com.example.nazanin.notepad.controller.FileManager;
import com.example.nazanin.notepad.controller.Fragments.DialogFragment;
import com.example.nazanin.notepad.controller.Fragments.EditDialogFragment;
import com.example.nazanin.notepad.model.dao.NoteDbHelper;
import com.example.nazanin.notepad.model.dto.Note;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ViewNoteActivity extends AppCompatActivity implements View.OnClickListener,DialogFragment.OnCallBackListener,EditDialogFragment.OnCallBackEditListener{

    private TextView titletxt;
    private EditText bodytxt;
    private boolean editEnabled;
    private ImageButton confirmChangesButton;
    private ImageButton editImageButton,shareImageButton,deleteImageButton,copyImageButton;
    private String fileName,fileText;
    private NoteDbHelper noteDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        noteDbHelper=new NoteDbHelper(this);
        editImageButton=findViewById(R.id.edit);
        copyImageButton=findViewById(R.id.copy);
        deleteImageButton=findViewById(R.id.delete);
        shareImageButton=findViewById(R.id.share);
        confirmChangesButton=findViewById(R.id.confirmChangesButton);
        confirmChangesButton.setVisibility(View.INVISIBLE);
        editImageButton.setOnClickListener(this);
        copyImageButton.setOnClickListener(this);
        deleteImageButton.setOnClickListener(this);
        shareImageButton.setOnClickListener(this);
        titletxt=findViewById(R.id.title);
        bodytxt=findViewById(R.id.note);
        bodytxt.setEnabled(false);
        loadWantedNote();
    }

    private void loadWantedNote(){
        if (getIntent().hasExtra("note")){
            Note note=getIntent().getExtras().getParcelable("note");
            fileName=note.getTitle();
            fileText=note.getText();
            titletxt.setText(fileName);
            bodytxt.setText(fileText);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.delete:
                DialogFragment dialogFragment=new DialogFragment();
                dialogFragment.show(getSupportFragmentManager(),"delete");

                break;
            case R.id.edit:
                bodytxt.setEnabled(true);
                editEnabled=true;
                confirmChangesButton.setVisibility(View.VISIBLE);
                break;
            case R.id.copy:
                copy();
                break;

            case R.id.share:
                share();
                break;

        }
    }


    public void share(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT,fileName);
        shareIntent.putExtra(Intent.EXTRA_TEXT,fileText);
        startActivity(Intent.createChooser(shareIntent,"share"));
    }

    public void edit(View view) {
        FileManager fileManager=new FileManager();
        fileText=bodytxt.getText().toString();
        fileManager.editFile(fileName,fileText,this);
        Toast.makeText(this,"تغییرات ذخیره شد",Toast.LENGTH_SHORT).show();
        editEnabled=false;
        bodytxt.setEnabled(false);
    }
    private void copy(){
        ClipboardManager clipboardManager=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.setText(fileText);
        Toast.makeText(this,"متن کپی شد",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void decide(boolean accepted) {
        if (accepted){
            FileManager fileManager=new FileManager();
            if (fileManager.delete(fileName,this)){
                noteDbHelper.delete(fileName);
                Toast.makeText(ViewNoteActivity.this,"یادداشت مورد نظر حذف شد",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {

        if (editEnabled){
            EditDialogFragment fragment=new EditDialogFragment();
            fragment.show(getSupportFragmentManager(),"edit");
        }
        if (!editEnabled)
            super.onBackPressed();
    }

    @Override
    public void confirmChanges(boolean accepted) {
        if (accepted){
            FileManager fileManager=new FileManager();
            fileText=bodytxt.getText().toString();
            fileManager.editFile(fileName,fileText,this);
            Toast.makeText(this,"تغییرات ذخیره شد",Toast.LENGTH_SHORT).show();
            editEnabled=false;
            bodytxt.setEnabled(false);
            super.onBackPressed();
        }
        if (!accepted){
            finish();
        }
    }
}
