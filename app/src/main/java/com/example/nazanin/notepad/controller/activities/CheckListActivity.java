package com.example.nazanin.notepad.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nazanin.notepad.R;
import com.example.nazanin.notepad.controller.adapters.CheckListAdapter;
import com.example.nazanin.notepad.model.dao.CategoryDbHelper;
import com.example.nazanin.notepad.model.dto.CheckList;
import com.example.nazanin.notepad.model.dao.CheckListDbHelper;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class CheckListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private ArrayList<CheckList> checkLists=new ArrayList<>();
    private CheckListDbHelper checkListDbHelper;
    private CheckListAdapter checkListAdapter;
    private Spinner spinner;
    private String[] categoryChoices={"همه","کار","خرید","درسی","شخصی"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        recyclerView=findViewById(R.id.checkListsView);
        spinner = findViewById(R.id.categorySpinner);
        final ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,categoryChoices);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
        checkListDbHelper=new CheckListDbHelper(this);
        checkLists=checkListDbHelper.getCheckLists();

        checkListAdapter=new CheckListAdapter(checkLists,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(checkListAdapter);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkLists.clear();
        checkLists=checkListDbHelper.getCheckLists();
        Toast.makeText(this,String.valueOf(checkLists.size()), Toast.LENGTH_SHORT).show();
        checkListAdapter.notifyDataSetChanged();
    }


    public void makeNewCheklist(View view) {
       Intent intent=new Intent(this,CheckListReminderActivity.class);
       startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CategoryDbHelper categoryDbHelper = new CategoryDbHelper(getApplicationContext());
        String categoryName=parent.getItemAtPosition(position).toString();
     //   checkList.setCategory(categoryDbHelper.getCategoryByName(categoryName));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
