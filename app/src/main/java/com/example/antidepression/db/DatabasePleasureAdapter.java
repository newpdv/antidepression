package com.example.antidepression.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabasePleasureAdapter {
    private final DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabasePleasureAdapter(Context context) {
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    private Cursor getAllEntries() {
        String[] columns = new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_TEXT};
        return database.query(DatabaseHelper.PLEASURE_TABLE, columns, null, null, null, null, null);
    }

    public List<Pleasure> getAllPleasure() {
        ArrayList<Pleasure> Pleasures = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String text = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEXT));
                Pleasures.add(new Pleasure(id, text));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return Pleasures;
    }

    public Pleasure getPleasure(long id) {
        Pleasure pleasure = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?", DatabaseHelper.PLEASURE_TABLE, DatabaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            String text = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEXT));
            pleasure = new Pleasure(id, text);
        }
        cursor.close();
        return pleasure;
    }

    public void delete(long PleasureId) {
        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(PleasureId)};
        database.delete(DatabaseHelper.PLEASURE_TABLE, whereClause, whereArgs);
    }

    public void recreate() {
        dbHelper.recreatePleasure(database);
    }
}
