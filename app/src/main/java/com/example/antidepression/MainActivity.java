package com.example.antidepression;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class MainActivity extends ListActivity {

    public static final String APP_PREFERENCES_THEME = "theme";
    public static final String IS_DARK_THEME = "isDarkTheme";
    private SharedPreferences settings;

    String[] activities = {"Про депрессию", "Тест", "Помощь специалиста", "Дневник мыслей", "Терапия удовольствием"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadSettings();
        loadTheme();

        setContentView(R.layout.activity_main);

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, activities);
        setListAdapter(adapter);

        AdapterView.OnItemClickListener itemListener = (parent, v, position, id) -> {
            String selectedItem = parent.getItemAtPosition(position).toString();
            Intent intent;
            switch (selectedItem) {
                case "Про депрессию":
                    intent = new Intent(getApplicationContext(), AboutDepressionActivity.class);
                    break;
                case "Тест":
                    intent = new Intent(getApplicationContext(), TestActivity.class);
                    break;
//                case "Помощь специалиста":
//                    intent = new Intent(getApplicationContext(), AdviceScreenSlidePagerActivity.class);
//                    break;
                case "Дневник мыслей":
                    intent = new Intent(getApplicationContext(), NotesActivity.class);
                    break;
                case "Терапия удовольствием":
                    intent = new Intent(getApplicationContext(), PleasureActivity.class);
                    break;
                default:
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    break;
            }
            startActivity(intent);
        };
        getListView().setOnItemClickListener(itemListener);
        ImageView img = findViewById(R.id.main_img);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.main);
        img.setImageURI(uri);
    }

    private void loadSettings() {
        settings = this.getSharedPreferences(APP_PREFERENCES_THEME, Context.MODE_PRIVATE);
    }

    private void loadTheme() {
        int theme = getThemeFromPreferences();
        setTheme(theme);
    }

    private void reloadTheme() {
        int theme = getThemeFromPreferences();
        setTheme(theme);
        MainActivity.this.recreate();
    }

    private int getThemeFromPreferences() {
        boolean darkTheme = settings.getBoolean(IS_DARK_THEME, false);
        return darkTheme ? R.style.DarkTheme : R.style.LightTheme;
    }
}