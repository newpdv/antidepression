package com.example.antidepression.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context){
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_TEXT, DatabaseHelper.COLUMN_STATE};
        return  database.query(DatabaseHelper.NOTES_TABLE, columns, null, null, null, null, null);
    }

    public List<Note> getNotes(){
        ArrayList<Note> notes = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String text = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEXT));
                int state = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_STATE));
                notes.add(new Note(id, text, state));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  notes;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DatabaseHelper.NOTES_TABLE);
    }

    public Note getNote(long id){
        Note note = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.NOTES_TABLE, DatabaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String text = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEXT));
            int state = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_STATE));
            note = new Note(id, text, state);
        }
        cursor.close();
        return  note;
    }

    public long insert(Note note){

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_TEXT, note.getText());
        cv.put(DatabaseHelper.COLUMN_STATE, note.getState());

        return  database.insert(DatabaseHelper.NOTES_TABLE, null, cv);
    }

    public long delete(long noteId){

        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(noteId)};
        return database.delete(DatabaseHelper.NOTES_TABLE, whereClause, whereArgs);
    }

    public long update(Note note){

        String whereClause = DatabaseHelper.COLUMN_ID + "=" + String.valueOf(note.getId());
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_TEXT, note.getText());
        cv.put(DatabaseHelper.COLUMN_STATE, note.getState());
        return database.update(DatabaseHelper.NOTES_TABLE, cv, whereClause, null);
    }
}