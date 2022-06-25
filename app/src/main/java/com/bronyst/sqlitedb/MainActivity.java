package com.bronyst.sqlitedb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bronyst.sqlitedb.DatabaseHelper.DatabaseHelper;
import com.bronyst.sqlitedb.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView notesRecyclerView;
    private FloatingActionButton addNoteBtn;
    private Intent intent;
    private EditText searchTerm;
    private ImageButton searchBtn;

    private DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initialize views
        initViews();

//        get data from db
        ArrayList<Note> notes = getNotes();

//        navigate to create note activity on floating action bar click
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(intent);
            }
        });

//        recycler view
        NotesRecAdapter adapter = new NotesRecAdapter(MainActivity.this);
        adapter.setNotes(notes);

        notesRecyclerView.setAdapter(adapter);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        search notes by title from database
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchTerm.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Search term cannot be empty", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        String noteTitle = searchTerm.getText().toString();
                        List<Note> notes = databaseHelper.notesByTitle(noteTitle);
                        Toast.makeText(MainActivity.this, "notes " + notes.toString(), Toast.LENGTH_SHORT).show();
                        adapter.setNotes(notes);
                    }catch(Exception e) {
                        Toast.makeText(MainActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private ArrayList<Note> getNotes() {
        ArrayList<Note> notes = (ArrayList<Note>) databaseHelper.allNotes();
        return notes;
    }

    //    initialize views
    public void initViews() {
        notesRecyclerView = findViewById(R.id.notes_rec_view);
        addNoteBtn = findViewById(R.id.toolbar_add_note);
        searchTerm = findViewById(R.id.edt_search_term);
        searchBtn = findViewById(R.id.btn_search);
    }
}