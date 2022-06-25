package com.bronyst.sqlitedb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bronyst.sqlitedb.DatabaseHelper.DatabaseHelper;
import com.bronyst.sqlitedb.models.Note;

public class NoteActivity extends AppCompatActivity {
    private Intent intent;

    private DatabaseHelper databaseHelper = new DatabaseHelper(this);

    private TextView noteTitle, noteContent;
    private Button deleteNoteBtn, editNoteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

//        initialize views
        initViews();

        intent = getIntent();

        if(null != intent) {
           int noteId = intent.getIntExtra("note_id", -1);
           if (noteId != -1) {
               try {
                   Note note = databaseHelper.getNoteById(noteId);
                   noteTitle.setText(note.getNoteTitle());
                   noteContent.setText(note.getNoteContent());
               }catch (Exception e) {
                   Toast.makeText(this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
               }
           }


            //        delete note
            deleteNoteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoteActivity.this);
                    builder.setMessage("Are you sure?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                boolean success = databaseHelper.deleteNote(noteId);
                                if(success) {
                                    Intent intent = new Intent(NoteActivity.this, MainActivity.class);
                                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(NoteActivity.this, "Error deleting note", Toast.LENGTH_SHORT).show();
                                }
                            }catch(Exception e) {
                                Toast.makeText(NoteActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    builder.create().show();
                }
            });

//           edit note
            editNoteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NoteActivity.this, EditNoteActivity.class);
                    intent.putExtra("noteId", noteId);
                    startActivity(intent);
                }
            });
        }

    }

//    init views
    public void initViews() {
        noteTitle = findViewById(R.id.txt_note_title);
        noteContent = findViewById(R.id.txt_note_content);
        deleteNoteBtn = findViewById(R.id.btn_delete_note);
        editNoteBtn = findViewById(R.id.btn_edit_note);
    }

}