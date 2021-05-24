package com.example.antidepression;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.antidepression.db.DatabaseAdapter;
import com.example.antidepression.db.Note;

import java.util.List;

public class NotesActivity extends AppCompatActivity {
    public static final String APP_PREFERENCES_THEME = "theme";
    public static final String IS_DARK_THEME = "isDarkTheme";
    private SharedPreferences settings;

    private ListView noteList;
    ArrayAdapter<Note> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadSettings();
        loadTheme();

        setContentView(R.layout.activity_notes);

        noteList = (ListView)findViewById(R.id.list);

        noteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note =arrayAdapter.getItem(position);
                if(note!=null) {
                    Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                    intent.putExtra("id", note.getId());
                    intent.putExtra("click", 25);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();

        List<Note> notes = adapter.getNotes();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        noteList.setAdapter(arrayAdapter);
        adapter.close();
    }
    // по нажатию на кнопку запускаем NoteActivity для добавления данных
    public void add(View view){
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }

    private void loadSettings() {
        settings = this.getSharedPreferences(APP_PREFERENCES_THEME, Context.MODE_PRIVATE);
    }

    private void loadTheme() {
        int theme = getThemeFromPreferences();
        setTheme(theme);
    }

    private int getThemeFromPreferences() {
        Boolean darkTheme = settings.getBoolean(IS_DARK_THEME, false);
        return darkTheme ? R.style.DarkTheme : R.style.LightTheme;
    }
}
