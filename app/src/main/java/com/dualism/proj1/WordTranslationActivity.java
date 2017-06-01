package com.dualism.proj1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dualism.proj1.Services.PlayAudioService;

import java.util.Arrays;
import java.util.Collections;

public class WordTranslationActivity extends AppCompatActivity {

    private TextView tvWord;

    private Button transl1;
    private Button transl2;
    private Button transl3;
    private Button transl4;
    private Button nextBtn;

    private Intent intent1;

    private boolean isTrue;

    private WordTranslation[] TranslTest = new WordTranslation[] {
            new WordTranslation("dog", "собака"),
            new WordTranslation("cat", "кошка"),
            new WordTranslation("frog", "лягушка"),
            new WordTranslation("bird", "птица"),
    };


    private int CurrentIndex;

    private void updateQuestion() {
        String question = TranslTest[CurrentIndex].getWord();
        tvWord.setText(question);

        String[] translations = new String[4];
        for (int i = 0; i < translations.length; i++) {
            translations[i] = TranslTest[i].getTranslation();
        }

        Collections.shuffle(Arrays.asList(translations));

        transl1.setText(translations[0]);
        transl2.setText(translations[1]);
        transl3.setText(translations[2]);
        transl4.setText(translations[3]);

        transl1.setEnabled(true);
        transl2.setEnabled(true);
        transl3.setEnabled(true);
        transl4.setEnabled(true);
        nextBtn.setEnabled(false);

        transl1.setBackgroundResource(android.R.drawable.btn_default);
        transl2.setBackgroundResource(android.R.drawable.btn_default);
        transl3.setBackgroundResource(android.R.drawable.btn_default);
        transl4.setBackgroundResource(android.R.drawable.btn_default);

        Intent intent = getIntent();
        intent1 = new Intent(this, PlayAudioService.class);
        intent1.putExtra("word", tvWord.getText().toString());
        intent1.putExtra("credentials", intent.getStringExtra("credentials"));
        startService(intent1);

        isTrue = false;
    }

    private void checkAnswer(String answer){
        int messageResId = 0;

        // checki right translation
        for (int i = 0; i < TranslTest.length; i++) {
            if(tvWord.getText().toString().equals(TranslTest[i].getWord())) {
                if (answer.equals(TranslTest[i].getTranslation())) {
                    messageResId = R.string.correct_toast;
                    isTrue = true;
                    break;
                }
                messageResId = R.string.incorrect_toast;
            }
        }
        transl1.setEnabled(false);
        transl2.setEnabled(false);
        transl3.setEnabled(false);
        transl4.setEnabled(false);
        nextBtn.setEnabled(true);
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    private void setBackgroundColor(Button button, boolean isTrue) {
        if(isTrue) {
            button.setBackgroundColor(getResources().getColor(R.color.trueAnswer));
        } else {
            button.setBackgroundColor(getResources().getColor(R.color.falseAnswer));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_translation);
        //String[] appCategoryDetail = mDatabaseHandler.getAppCategoryDetail();
        getSupportActionBar().setTitle("Word-Translation");

        isTrue = false;

        tvWord = (TextView) findViewById(R.id.word);
        tvWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intent1);
            }
        });


        transl1 = (Button) findViewById(R.id.trans1);
        transl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(transl1.getText().toString());
                setBackgroundColor(transl1, isTrue);
            }
        });

        transl2 = (Button) findViewById(R.id.trans2);
        transl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(transl2.getText().toString());
                setBackgroundColor(transl2, isTrue);
            }
        });

        transl3 = (Button) findViewById(R.id.trans3);
        transl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(transl3.getText().toString());
                setBackgroundColor(transl3, isTrue);
            }
        });

        transl4 = (Button) findViewById(R.id.trans4);
        transl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(transl4.getText().toString());
                setBackgroundColor(transl4, isTrue);
            }
        });

        nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentIndex = (CurrentIndex +1) % TranslTest.length;
                updateQuestion();
            }
        });
        nextBtn.setBackgroundResource(android.R.drawable.btn_default);

        updateQuestion();
    }
}
