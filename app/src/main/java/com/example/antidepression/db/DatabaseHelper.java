package com.example.antidepression.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "antidepression.db"; // название бд
    private static final int SCHEMA = 5; // версия базы данных
    static final String NOTES_TABLE = "notes"; // название таблицы в бд

    static final String PLEASURE_TABLE = "pleasure"; // название таблицы в бд
    static final String[] PLEASURE_LIST = new String[]{
            "Съесть что-то вкусное", "Поиграть с питомцем", "Сделать зарядку",
            "Прогуляться на свежем воздухе", "Встретиться с друзьями", "Посмотреть любимый фильм",
            "Сходить на концерт", "Послушать любимую музыку", "Найти новое хобби"
    };

    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_STATE = "state";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + NOTES_TABLE + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TEXT
                + " TEXT, " + COLUMN_STATE + " INTEGER);");
        // добавление начальных данных
        db.execSQL("INSERT INTO "+ NOTES_TABLE +" (" + COLUMN_TEXT
                + ", " + COLUMN_STATE + ") VALUES ('My first note', 5);");

        db.execSQL("CREATE TABLE " + PLEASURE_TABLE + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TEXT
                + " TEXT);");
        for (String pleasure : PLEASURE_LIST) {
            db.execSQL("INSERT INTO "+ PLEASURE_TABLE +" (" + COLUMN_TEXT
                    + ") VALUES ('" + pleasure + "');");
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ NOTES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ PLEASURE_TABLE);
        onCreate(db);
    }

    public void recreatePleasure(SQLiteDatabase db) {
        for (String pleasure : PLEASURE_LIST) {
            db.execSQL("INSERT INTO "+ PLEASURE_TABLE +" (" + COLUMN_TEXT
                    + ") VALUES ('" + pleasure + "');");
        }
    }
}
