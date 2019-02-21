package com.example.nazanin.notepad.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nazanin.notepad.R;
import com.example.nazanin.notepad.model.dao.CheckListDbHelper;
import com.example.nazanin.notepad.model.dto.CheckList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CheckListReminderActivity extends AppCompatActivity {

    private EditText whatToDoEditText,timeEditText,dateEditText;
    private String whatToDo,dateToDo,timeToDo;
    private CheckListDbHelper checkListDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        whatToDoEditText=findViewById(R.id.whatToDo);



    }
    private boolean isEmpty(String whatToDo){
        if (TextUtils.isEmpty(whatToDo)){
            Toast.makeText(this,"فیلد مورد نظر خالی است",Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void save(View view) {
        whatToDo=whatToDoEditText.getText().toString();
        dateToDo=dateEditText.getText().toString();
        timeToDo=timeEditText.getText().toString();
        if (!isEmpty(whatToDo)) {
            checkListDbHelper=new CheckListDbHelper(this);
            CheckList checkList=new CheckList();
            checkList.setDone(false);
            checkList.setWhatToDo(whatToDo);
            if (!TextUtils.isEmpty(dateToDo)){
                checkList.setDate(dateToDo);
            }
            if (!TextUtils.isEmpty(timeToDo)){
                checkList.setDate(timeToDo);
            }
            checkListDbHelper.insertCheckList(checkList);
            finish();
        }
    }
}
