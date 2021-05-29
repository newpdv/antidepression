package com.example.antidepression;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {
    public static final String APP_PREFERENCES_THEME = "theme"; //??
    public static final String IS_DARK_THEME = "isDarkTheme"; //??
    private SharedPreferences settings;

    public int finalSum = 0;

    private Question[] _questions;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadSettings();
        loadTheme();
        loadTest();

        setContentView(R.layout.activity_test);

        LinearLayout basicLayout = (LinearLayout) findViewById(R.id.basicLayout);
        for (Question question : _questions) {
            LinearLayout questionLayout = CreateQuestion(question);
            basicLayout.addView(questionLayout);
        }

        Button button = createCompleteButton();
        basicLayout.addView(button);

    }

    private Button createCompleteButton() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;

        Button button = new Button(this);

        button.setLayoutParams(params);
        button.setGravity(Gravity.CENTER);
        button.setText("Готово");
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String title;
                String text;

                if (finalSum < 13) {
                    title = "Ответьте на все вопросы";
                    text = "Нажмите на ОК чтобы продолжить";
                } else {
                    title = "Результаты теста";
                    if (finalSum < 26) {
                        text = "У вас все хорошо";
                    } else if (finalSum < 32) {
                        text = "У вас легкая депрессия";
                    }  else if (finalSum < 41) {
                        text = "У вас средняя депрессия";
                    } else if (finalSum >= 41) {
                        text = "У вас серьёзная депрессия. Пожалуйста обратитесь к специалисту.";
                    }
                }
                TestResultDialogFragment dialog = new TestResultDialogFragment(title, text);
                dialog.show(getSupportFragmentManager(), "custom");
            }
        });

        return button;
    }

    private LinearLayout CreateQuestion(Question question) {
        LinearLayout parent = new LinearLayout(this);
        parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        parent.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams parentParams = (LinearLayout.LayoutParams) parent.getLayoutParams();
        parentParams.setMargins(30, 30, 30, 30);

        TextView questionText = new TextView(this);
        questionText.setText(question.Question);

        LinearLayout answers = new LinearLayout(this);
        answers.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        answers.setOrientation(LinearLayout.VERTICAL);

        final CheckBox checkBox1 = new CheckBox(this);
        checkBox1.setText(question.Answer1);
        answers.addView(checkBox1);

        final CheckBox checkBox2 = new CheckBox(this);
        checkBox2.setText(question.Answer2);
        answers.addView(checkBox2);

        final CheckBox checkBox3 = new CheckBox(this);
        checkBox3.setText(question.Answer3);
        answers.addView(checkBox3);

        final CheckBox checkBox4 = new CheckBox(this);
        checkBox4.setText(question.Answer4);
        answers.addView(checkBox4);


        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);

                    finalSum += 1;
                } else {
                    finalSum -= 1;
                }
            }
        });

        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox1.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);

                    finalSum += 2;
                } else {
                    finalSum -= 2;
                }
            }
        });

        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox4.setChecked(false);

                    finalSum += 3;
                } else {
                    finalSum -= 3;
                }
            }
        });

        checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);

                    finalSum += 4;
                } else {
                    finalSum -= 4;
                }
            }
        });

        parent.addView(questionText);
        parent.addView(answers);

        return parent;
    }

    private void loadTest() {
        this._questions = new Question[]{
                new Question ("Как вы себя чувствовали на этой неделе и сегодня?",
                        "Я не расстроен, грустен",
                        "Мне грустно.",
                        "Я все время расстроен и не могу отключиться от этого",
                        "Я так расстроен и несчастен, что не могу этого вынести"),
                new Question ("Как вы себя чувствовали на этой неделе и сегодня?",
                        "Я не потерял интереса к другим людям",
                        "Меня меньше интересуют другие люди, чем раньше",
                        "Я почти потерял интерес к другим людям и почти не испытываю к ним чувств",
                        "Я потерял всякий интерес к другим людям, и они меня совершенно не беспокоят"),
                new Question ("Как вы себя чувствовали на этой неделе и сегодня?",
                        "Я смотрю в будущее без особого разочарования",
                        "Я разочарован в будущем",
                        "Я чувствую, что мне нечего ожидать",
                        "Я чувствую, что будущее безнадежно и не может быть никаких изменений к лучшему"),
                new Question ("Как вы себя чувствовали на этой неделе и сегодня?",
                        "Я принимаю решения так же легко, как и раньше",
                        "Я пытаюсь отложить принятие решения.",
                        "Принятие решений для меня - огромная проблема",
                        "Я больше не могу принимать решения"),
                new Question ("Как вы себя чувствовали на этой неделе и сегодня?",
                        "Я не чувствую себя неудачником",
                        "Я чувствую, что неудачи случались со мной чаще, чем с другими людьми",
                        "Когда я оглядываюсь на свою жизнь, я вижу только цепь неудач",
                        "Я чувствую, что потерпел неудачу как личность (родитель, муж, жена)"),
                new Question ("Как вы себя чувствовали на этой неделе и сегодня?",
                        "Я не чувствую, что выгляжу хуже, чем обычно",
                        "Меня беспокоит то, что я выгляжу старым и непривлекательным.",
                        "Я чувствую, что моя внешность постоянно меняется, что делает меня непривлекательной",
                        "Я чувствую, что выгляжу мерзко или отталкивающе"),
                new Question ("Как вы себя чувствовали на этой неделе и сегодня?",
                        "Особой неудовлетворенности не испытываю",
                        "Ничто не радует меня так, как раньше.",
                        "Больше ничего не доставляет мне удовлетворения.",
                        "Меня не все устраивает"),
                new Question ("Как вы себя чувствовали на этой неделе и сегодня?",
                        "Я могу работать так же хорошо, как и раньше",
                        "Мне нужно приложить дополнительные усилия, чтобы что-то сделать",
                        "Я устал от работы",
                        "Я не могу работать"),
                new Question ("Как вы себя чувствовали на этой неделе и сегодня?",
                        "Я не чувствую особой вины",
                        "Большую часть времени я чувствую себя мерзким и никчемным",
                        "У меня довольно сильное чувство вины",
                        "Я чувствую себя очень противным и никчёмным"),
                new Question ("Как вы себя чувствовали на этой неделе и сегодня?",
                        "Я устаю не больше обычного",
                        "Я устаю быстрее, чем раньше.",
                        "Я устаю от любой деятельности.",
                        "Я устал делать что угодно"),
                new Question ("Как вы себя чувствовали на этой неделе и сегодня?",
                        "Я не разочарован в себе.",
                        "Я разочарован в себе.",
                        "Я испытываю отвращение к себе.",
                        "Я ненавижу себя."),
                new Question ("Как вы себя чувствовали на этой неделе и сегодня?",
                        "Мой аппетит не хуже обычного",
                        "Мой аппетит уже не так хорош, как раньше",
                        "Мой аппетит стал намного хуже",
                        "У меня вообще нет аппетита"),
                new Question ("Как вы себя чувствовали на этой неделе и сегодня?",
                        "Я не думаю о членовредительстве",
                        "Я чувствую, что мне лучше умереть",
                        "У меня есть определенные планы на самоубийство",
                        "Я убью себя как можно скорее"),
        };
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

    private class Question {
        public String Question;
        public String Answer1;
        public String Answer2;
        public String Answer3;
        public String Answer4;

        public Question(String question, String answer1, String answer2, String answer3, String answer4) {
            this.Question = question;
            this.Answer1 = answer1;
            this.Answer2 = answer2;
            this.Answer3 = answer3;
            this.Answer4 = answer4;
        }
    }
}