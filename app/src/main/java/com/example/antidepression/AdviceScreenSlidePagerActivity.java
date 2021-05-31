package com.example.antidepression;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class AdviceScreenSlidePagerActivity extends FragmentActivity {
    public static final String APP_PREFERENCES_THEME = "theme";
    public static final String IS_DARK_THEME = "isDarkTheme";
    private SharedPreferences settings;

    String[] advices = new String[] {
            "Физическая нагрузка – утренняя зарядка или пробежка не занимает много времени, при этом способствует выработке гормонов «радости». Вечерние занятия спортом улучшают сон и снимают напряжение, накопленное в течение дня. Лучше всего привлекать друзей к занятиям спортом, попросить составить компанию. Это повысит мотивацию, позволит почувствовать себя значимым и даст возможность лишний раз пообщаться.",
            "Правильное питание – на переваривание жирной пищи организм тратит много сил, что совсем некстати при депрессии. Питаться лучше лёгкими блюдами, но разнообразно. Важно не переедать.",
            "Распорядок дня и сон – отдых очень нужен при любой болезни, в том числе при депрессии. Вставать и ложиться в одно и то же время, спать не менее 8 часов в сутки поможет справиться со слабостью, даст организму сигнал, что всё в порядке, предсказуемо и безопасно.",
            "Больше гулять, в особенности в солнечные дни.",
            "Расслабление – ежедневно рекомендуется уделять хотя бы 2 минуты дыхательным практикам и ещё 7 минут релаксации по Джекобсону - это метод релаксации, который заключается в напряжении и расслаблении всех групп мышц тела в определенной последовательности. Всё это поможет успокоить центры стресса в головном мозге и снять излишнюю тревожность.",
            "Не замыкаться в себе, стараться общаться с друзьями, заводить новых знакомых.",
            "Начать заниматься йогой - согласно исследованиям, она может быть эффективна при депрессивных расстройствах как сопутствующий метод терапии.",
            "Если все предыдущие советы Вам не помогли, то позвоните в бесплатную всероссийскую службу психологической помощи: 8-800-333-44-34",
    };

    private static final int NUM_PAGES = 8;

    private ViewPager mPager;


    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadSettings();
        loadTheme();

        setContentView(R.layout.advice_activity_screen_slide);

        mPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {

            super.onBackPressed();
        } else {

            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new AdviceScreenSlidePageFragment(advices[position], position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
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
        AdviceScreenSlidePagerActivity.this.recreate();
    }

    private int getThemeFromPreferences() {
        Boolean darkTheme = settings.getBoolean(IS_DARK_THEME, false);
        return darkTheme ? R.style.DarkTheme : R.style.LightTheme;
    }
}