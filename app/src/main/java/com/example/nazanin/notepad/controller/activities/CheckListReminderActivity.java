package com.example.nazanin.notepad.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nazanin.notepad.R;
import com.example.nazanin.notepad.model.dao.CategoryDbHelper;
import com.example.nazanin.notepad.model.dao.CheckListDbHelper;
import com.example.nazanin.notepad.model.dto.Category;
import com.example.nazanin.notepad.model.dto.CheckList;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CheckListReminderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText whatToDoEditText;
    private Button dateBtn,timeBtn;
    private Spinner spinner;
    private String whatToDo,dateToDo,timeToDo;
    private CheckListDbHelper checkListDbHelper;
    private ArrayList<String> categoryChoices;
    private CategoryDbHelper categoryDbHelper;
    private Category category;
    private CheckList checkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        categoryDbHelper = new CategoryDbHelper(this);
        category = new Category();
        checkList = new CheckList();
        categoryChoices = categoryDbHelper.getcategories();
        whatToDoEditText=findViewById(R.id.whatToDo);
        dateBtn = findViewById(R.id.dateToDo);
        timeBtn = findViewById(R.id.timeToDo);
        spinner = findViewById(R.id.categorySpinner);
        final ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,categoryChoices);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);


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
        dateToDo=dateBtn.getText().toString();
        timeToDo=timeBtn.getText().toString();
        if (!isEmpty(whatToDo)) {
            checkListDbHelper=new CheckListDbHelper(this);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String cat = parent.getItemAtPosition(position).toString();
        checkList.setCategory(categoryDbHelper.getCategoryByName(cat));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
