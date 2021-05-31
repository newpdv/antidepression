package com.example.antidepression;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class AboutDepressionActivity extends AppCompatActivity {
    public static final String APP_PREFERENCES_THEME = "theme";
    public static final String IS_DARK_THEME = "isDarkTheme";
    private SharedPreferences settings;

    VideoView videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadSettings();
        loadTheme();

        setContentView(R.layout.activity_about_depression);

        videoview = findViewById(R.id.videoview);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.film);
        videoview.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        videoview.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoview);
    }

    public void play(View view) {
        videoview.start();
    }

    public void pause(View view) {
        videoview.pause();
    }

    public void stop(View view) {
        videoview.stopPlayback();
        videoview.resume();
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
