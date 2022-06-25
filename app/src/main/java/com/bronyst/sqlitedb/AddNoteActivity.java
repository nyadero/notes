package com.bronyst.sqlitedb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bronyst.sqlitedb.DatabaseHelper.DatabaseHelper;
import com.bronyst.sqlitedb.models.Note;

public class AddNoteActivity extends AppCompatActivity {
    private EditText noteTitle, noteContent;
    private Button addNoteBtn;

    DatabaseHelper databaseHelper = new DatabaseHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        initViews();

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = new Note(-1, noteTitle.getText().toString(), noteContent.getText().toString());
                if (noteTitle.getText().toString().isEmpty() || noteContent.getText().toString().isEmpty()){
                    Toast.makeText(AddNoteActivity.this, "Error creating note", Toast.LENGTH_SHORT).show();
                }else{
                    boolean success = databaseHelper.addNote(note);
                    Toast.makeText(AddNoteActivity.this, "note created = " + success, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
//                    intent.putExtra("note", (Parcelable) note);
                    startActivity(intent);
                }
            }
        });
    }


    public void initViews() {
        noteTitle = findViewById(R.id.edt_note_title);
        noteContent = findViewById(R.id.edt_note_details);
        addNoteBtn = findViewById(R.id.btn_add_note);
    }
}