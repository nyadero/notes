package com.bronyst.sqlitedb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bronyst.sqlitedb.DatabaseHelper.DatabaseHelper;
import com.bronyst.sqlitedb.models.Note;

public class EditNoteActivity extends AppCompatActivity {
    private EditText noteTitle, noteContent;
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private Intent intent;
    private Button editNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

//        initialize views
        initViews();

//        receive note id from intent
        intent = getIntent();
        if(null != intent) {
            int noteId = intent.getIntExtra("noteId", -1);
//            get note by id
            Note note = databaseHelper.getNoteById(noteId);
//            populate edittexts with result
            noteTitle.setText(note.getNoteTitle());
            noteContent.setText(note.getNoteContent());
            editNoteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Note noteToEdit = new Note();
                    //            get new data from the edittexts
                    if(noteTitle.getText().toString().isEmpty() || noteContent.getText().toString().isEmpty()){
                        Toast.makeText(EditNoteActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                    }else{
                        noteToEdit = new Note(noteId, noteTitle.getText().toString(), noteContent.getText().toString());
                        boolean success = databaseHelper.editNote(noteToEdit);
                        Toast.makeText(EditNoteActivity.this, "note " + noteToEdit, Toast.LENGTH_SHORT).show();
                        if (success) {
                            intent = new Intent(EditNoteActivity.this, MainActivity.class);
                            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                }
            });
        }
    }


//    initialize views

    public void initViews() {
        noteTitle = findViewById(R.id.edt_note_title);
        noteContent = findViewById(R.id.edt_note_content);
        editNoteButton = findViewById(R.id.btn_edit_note);
    }
}