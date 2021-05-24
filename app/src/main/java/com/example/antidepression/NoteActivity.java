package com.example.antidepression;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.antidepression.db.DatabaseAdapter;
import com.example.antidepression.db.Note;
import com.example.antidepression.helpers.InputFilterMinMax;

public class NoteActivity extends AppCompatActivity {
    public static final String APP_PREFERENCES_THEME = "theme";
    public static final String IS_DARK_THEME = "isDarkTheme";
    private SharedPreferences settings;

    private EditText textBox;
    private EditText stateBox;
    private Button delButton;
    private Button saveButton;

    private DatabaseAdapter adapter;
    private long noteId =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadSettings();
        loadTheme();

        setContentView(R.layout.activity_note);

        textBox = (EditText) findViewById(R.id.text);
        stateBox = (EditText) findViewById(R.id.state);
        delButton = (Button) findViewById(R.id.deleteButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        adapter = new DatabaseAdapter(this);

        stateBox.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "5")});

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            noteId = extras.getLong("id");
        }
        // если 0, то добавление
        if (noteId > 0) {
            // получаем элемент по id из бд
            adapter.open();
            Note note = adapter.getNote(noteId);
            textBox.setText(note.getText());
            stateBox.setText(String.valueOf(note.getState()));
            adapter.close();
        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
        }
    }

    public void save(View view){
        String text = textBox.getText().toString();
        String stateText = stateBox.getText().toString();
        if (stateText.isEmpty()) {
            stateText = "5";
        }
        int state = Integer.parseInt(stateText);
        Note note = new Note(noteId, text, state);

        adapter.open();
        if (noteId > 0) {
            adapter.update(note);
        } else {
            adapter.insert(note);
        }
        adapter.close();
        goHome();
    }

    public void delete(View view){

        adapter.open();
        adapter.delete(noteId);
        adapter.close();
        goHome();
    }
    private void goHome(){
        // переход к главной activity
        Intent intent = new Intent(this, NotesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
