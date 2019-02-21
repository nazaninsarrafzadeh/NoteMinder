package com.example.nazanin.notepad.controller.activities;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;

import com.example.nazanin.notepad.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.nazanin.notepad.controller.FileManager;
import com.example.nazanin.notepad.controller.LayoutManager;
import com.example.nazanin.notepad.controller.adapters.NoteAdapter;
import com.example.nazanin.notepad.model.dao.NoteDbHelper;
import com.example.nazanin.notepad.model.dto.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteManagerActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView notesRcyclerView;
    private NoteAdapter noteAdapter;
    private ArrayList<Note> notesList=new ArrayList<>();
    private Context context;
    private FileManager fileManager;
    private Toolbar toolbar;
    private boolean viewMode;
    private NoteDbHelper noteDbHelper;
    private LayoutManager layoutManager;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_manager);
        context=this;
        fileManager=new FileManager();
        noteDbHelper = new NoteDbHelper(context);
        layoutManager = new LayoutManager();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        notesList=fileManager.prepareNotes(context);

        notesRcyclerView=findViewById(R.id.notesView);
        noteAdapter=new NoteAdapter(notesList,context);
        if (layoutManager.listLayout(context)){
            mLayoutManager = new LinearLayoutManager(getApplicationContext());
        }
        else {
            mLayoutManager = new GridLayoutManager(NoteManagerActivity.this, 3);
        }
        notesRcyclerView.setLayoutManager(mLayoutManager);
        notesRcyclerView.setItemAnimator(new DefaultItemAnimator());
        notesRcyclerView.setAdapter(noteAdapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                remove((int)viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(notesRcyclerView);
    }
    private void remove(int id){
        String filename = notesList.get(id).getTitle();
        noteDbHelper.delete(filename);
        fileManager.delete(filename,context);
        notesList.remove(id);
        noteAdapter.notifyItemRemoved(id);
        noteAdapter.notifyItemRangeChanged(id, noteAdapter.getItemCount());
    }


    public void makeNewNotepad(View view) {
        Intent intent=new Intent(NoteManagerActivity.this,NotepadActivity.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.normal, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.list:
                viewMode = true;
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                break;
            case R.id.grid:
                viewMode = false;
                mLayoutManager = new GridLayoutManager(NoteManagerActivity.this, 3);
                break;

        }
        notesRcyclerView.setLayoutManager(mLayoutManager);
        layoutManager.storeMode(context,viewMode);
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        notesList.clear();
        notesList=fileManager.prepareNotes(context);
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String userinput = s.toLowerCase();
        List<Note> newList = new ArrayList<>();
        for (Note myNote:notesList){
            if (myNote.getTitle().toLowerCase().contains(userinput)){
                newList.add(myNote);
            }
        }
        noteAdapter.searchFilter(newList);
        return true;

    }
}
