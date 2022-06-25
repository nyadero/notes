package com.bronyst.sqlitedb.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.bronyst.sqlitedb.models.Note;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String NOTES_TABLE = "NOTES_TABLE";
    public static final String ID = "ID";
    public static final String NOTES_TITLE = "NOTES_TITLE";
    public static final String NOTES_CONTENT = "NOTES_CONTENT";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "notes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + NOTES_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOTES_TITLE + " TEXT, " + NOTES_CONTENT + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

//    method to add note to database
    public boolean addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NOTES_TITLE, note.getNoteTitle());
        cv.put(NOTES_CONTENT, note.getNoteContent());

        long insert = db.insert(NOTES_TABLE, null, cv);

        if (insert == -1) {
            return false;
        }else{
            return true;
        }
    }

//    method to get all notes from database
    public List<Note> allNotes(){
        List<Note> allNotes = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + NOTES_TABLE;

        Cursor cursor = db.rawQuery(query, null);

//        check if there is data and then loop through the data
        if (cursor.moveToFirst()){
            // loop
            do {
              int noteId = cursor.getInt(0);
              String noteTitle = cursor.getString(1);
              String  noteContent = cursor.getString(2);

              Note newNote = new Note(noteId, noteTitle, noteContent);
              allNotes.add(newNote);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return  allNotes;
    }


//    get note by id
    public Note getNoteById(int noteId) {
        Note returnNote = new Note();
        String query = "SELECT * FROM NOTES_TABLE WHERE ID = " + noteId;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String noteTitle = cursor.getString(1);
            String noteContent = cursor.getString(2);

            returnNote.setId(id);
            returnNote.setNoteTitle(noteTitle);
            returnNote.setNoteContent(noteContent);
        }

        cursor.close();
        db.close();
        return returnNote;
    }

//    get note by search using note title
    public List<Note> notesByTitle(String noteTitle) {
        List<Note> returnNotes = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM NOTES_TABLE WHERE NOTES_TITLE LIKE ? " + noteTitle;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do {
                int noteId = cursor.getInt(0);
                String note_title = cursor.getString(1);
                String noteContent = cursor.getString(2);

                Note newNote = new Note(noteId, note_title, noteContent);
                returnNotes.add(newNote);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return returnNotes;
    }

//    delete note
    public boolean deleteNote(int noteId) {
        SQLiteDatabase db = this.getWritableDatabase();

        int delete = db.delete("NOTES_TABLE", " ID = " + noteId, null);

        if (delete != -1) {
            return true;
        }else{
            return false;
        }
    }

//    edit note
    public boolean editNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NOTES_TITLE, note.getNoteTitle());
        cv.put(NOTES_CONTENT, note.getNoteContent());

        int success = db.update(NOTES_TABLE, cv, "ID = " + note.getId(), null);
        if (success != -1) {
            return true;
        }else{
            return false;
        }
    }

}
