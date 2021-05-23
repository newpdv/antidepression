package com.example.antidepression;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.antidepression.db.DatabasePleasureAdapter;
import com.example.antidepression.db.Pleasure;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PleasureActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES_THEME = "theme";
    public static final String IS_DARK_THEME = "isDarkTheme";
    private SharedPreferences settings;

    private ListView pleasureList;
    private Pleasure selectedPleasure;
    private View selectedView;
    ArrayAdapter<Pleasure> arrayAdapter;
    List<Pleasure> currentItems;

    private Button deleteButton;
    private Button laterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadSettings();
        loadTheme();

        setContentView(R.layout.activity_pleasure);

        pleasureList = (ListView)findViewById(R.id.list);
        deleteButton = (Button)findViewById(R.id.delete);
        laterButton = (Button)findViewById(R.id.later);

        pleasureList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPleasure = arrayAdapter.getItem(position);

            if(selectedPleasure!=null) {
                showButtons();
                if (selectedView != null) {
                    selectedView.setBackgroundColor(Color.TRANSPARENT);
                }
                view.setBackgroundColor(Color.parseColor("#d0d1d1"));
            } else {
                hideButtons();
                if (selectedView != null) {
                    selectedView.setBackgroundColor(Color.TRANSPARENT);
                }
            }

            selectedView = view;
        });

        hideButtons();
    }

    private void showButtons() {
        laterButton.setVisibility(View.VISIBLE);
        deleteButton.setVisibility(View.VISIBLE);
    }

    private void hideButtons() {
        laterButton.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }
    // по нажатию на кнопку запускаем PleasureActivity для добавления данных
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void delete(View view){
        DatabasePleasureAdapter adapter = new DatabasePleasureAdapter(this);
        adapter.open();
        adapter.delete(selectedPleasure.getId());
        currentItems.remove(selectedPleasure);

        List<Pleasure> pleasures = adapter.getAllPleasure();
        List<Long> pleasuresIds = pleasures.stream().map(pl -> pl.getId()).filter(pl -> !currentItems.stream().map(s -> s.getId()).anyMatch(t -> t == pl)).collect(Collectors.toList());


        adapter.close();

        hideButtons();
        if (selectedView != null) {
            selectedView.setBackgroundColor(Color.TRANSPARENT);
        }

        if (currentItems.size() == 0) {
            adapter.open();
            adapter.recreate();
            pleasures = adapter.getAllPleasure();
            pleasures.subList(0, Integer.min(4, pleasures.size())).forEach(p -> currentItems.add(p));
            adapter.close();
            arrayAdapter.notifyDataSetChanged();

            return;
        }

        arrayAdapter.notifyDataSetChanged();


        selectedPleasure = null;
        selectedView = null;

        if (pleasuresIds.size() == 0) {
            return;
        }

        adapter.open();

        Random random = new Random();
        Integer nextId = random.nextInt(pleasuresIds.size());
        Pleasure nextPleasure = adapter.getPleasure(pleasuresIds.get(nextId));

        currentItems.add(nextPleasure);
        arrayAdapter.notifyDataSetChanged();
        adapter.close();



    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void remind(View view){
        DatabasePleasureAdapter adapter = new DatabasePleasureAdapter(this);
        adapter.open();
        currentItems.remove(selectedPleasure);

        List<Pleasure> pleasures = adapter.getAllPleasure();
        List<Long> pleasuresIds = pleasures.stream().map(pl -> pl.getId()).filter(pl -> !currentItems.stream().map(s -> s.getId()).anyMatch(t -> t == pl)).collect(Collectors.toList());


        adapter.close();

        hideButtons();
        if (selectedView != null) {
            selectedView.setBackgroundColor(Color.TRANSPARENT);
        }

        arrayAdapter.notifyDataSetChanged();


        selectedPleasure = null;
        selectedView = null;

        if (pleasuresIds.size() == 0) {
            return;
        }
        adapter.open();


        Random random = new Random();
        Integer nextId = random.nextInt(pleasuresIds.size());
        Pleasure nextPleasure = adapter.getPleasure(pleasuresIds.get(nextId));

        currentItems.add(nextPleasure);
        arrayAdapter.notifyDataSetChanged();
        adapter.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void refreshList() {
        DatabasePleasureAdapter adapter = new DatabasePleasureAdapter(this);
        adapter.open();

        List<Pleasure> list = adapter.getAllPleasure();
        currentItems = list.subList(0, Integer.min(4, list.size()));
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currentItems);
        pleasureList.setAdapter(arrayAdapter);
        adapter.close();
    }

    private void loadSettings() {
        settings = this.getSharedPreferences(APP_PREFERENCES_THEME, Context.MODE_PRIVATE);
    }

    private void loadTheme() {
        int theme = getThemeFromPreferences();
        setTheme(theme);
    }

    private int getThemeFromPreferences() {
        boolean darkTheme = settings.getBoolean(IS_DARK_THEME, false);
        return darkTheme ? R.style.DarkTheme : R.style.LightTheme;
    }
}