package com.dualism.proj1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;

public class TranslationWordActivity extends AppCompatActivity {

    private TextView tvTranslation;
    private TextView truwnost;

    private Button btnWord1;
    private Button btnWord2;
    private Button btnWord3;
    private Button btnWord4;
    private Button nextBtn;

    private WordTransl[] TranslTest = new WordTransl[] {
            new WordTransl("dog", "собака"),
            new WordTransl("cat", "кошка"),
            new WordTransl("frog", "лягушка"),
            new WordTransl("bird", "птица"),
    };

    private int CurrentIndex;

    private void updateQuestion() {
        String question = TranslTest[CurrentIndex].getTranslation();
        tvTranslation.setText(question);

        String[] words = new String[4];
        for (int i = 0; i < words.length; i++) {
            words[i] = TranslTest[i].getWord();
        }

        Collections.shuffle(Arrays.asList(words));

        btnWord1.setText(words[0]);
        btnWord2.setText(words[1]);
        btnWord3.setText(words[2]);
        btnWord4.setText(words[3]);
    }

    private void checkAnswer(String answer){
        int messageResId = 0;

        for (int i = 0; i < TranslTest.length; i++) {
            if(tvTranslation.getText().toString().equals(TranslTest[i].getTranslation())) {
                if (answer.equals(TranslTest[i].getWord())) {
                    messageResId = R.string.correct_toast;
                    break;
                }
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation_word);

        tvTranslation = (TextView) findViewById(R.id.tvTranslation);


        btnWord1 = (Button) findViewById(R.id.btnWord1);
        btnWord1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(btnWord1.getText().toString());
            }
        });

        btnWord2 = (Button) findViewById(R.id.btnWord2);
        btnWord2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(btnWord2.getText().toString());
            }
        });

        btnWord3 = (Button) findViewById(R.id.btnWord3);
        btnWord3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(btnWord3.getText().toString());
            }
        });

        btnWord4 = (Button) findViewById(R.id.btnWord4);
        btnWord4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(btnWord4.getText().toString());
            }
        });

        nextBtn = (Button) findViewById(R.id.btnNext2);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentIndex = (CurrentIndex +1) % TranslTest.length;
                updateQuestion();
            }
        });
        updateQuestion();
    }
}
