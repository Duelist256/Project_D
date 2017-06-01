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

public class TranslationWordActivity extends AppCompatActivity {

    private TextView tvTranslation;

    private Button btnWord1;
    private Button btnWord2;
    private Button btnWord3;
    private Button btnWord4;

    Button nextBtn;

    private Intent intent1;

    private boolean isTrue;

    private WordTranslation[] TranslTest = new WordTranslation[] {
            new WordTranslation("dog", "собака"),
            new WordTranslation("cat", "кошка"),
            new WordTranslation("fox", "лиса"),
            new WordTranslation("bird", "птица"),
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

        btnWord1.setEnabled(true);
        btnWord2.setEnabled(true);
        btnWord3.setEnabled(true);
        btnWord4.setEnabled(true);
        nextBtn.setEnabled(false);

        btnWord1.setBackgroundResource(android.R.drawable.btn_default);
        btnWord2.setBackgroundResource(android.R.drawable.btn_default);
        btnWord3.setBackgroundResource(android.R.drawable.btn_default);
        btnWord4.setBackgroundResource(android.R.drawable.btn_default);

        isTrue = false;
    }

    private void checkAnswer(String answer){
        int messageResId = 0;

        for (int i = 0; i < TranslTest.length; i++) {
            if(tvTranslation.getText().toString().equals(TranslTest[i].getTranslation())) {
                if (answer.equals(TranslTest[i].getWord())) {
                    messageResId = R.string.correct_toast;
                    Intent intent = getIntent();
                    intent1 = new Intent(this, PlayAudioService.class);
                    intent1.putExtra("word", TranslTest[i].getWord());
                    intent1.putExtra("credentials", intent.getStringExtra("credentials"));
                    startService(intent1);
                    isTrue = true;
                    break;
                }
                messageResId = R.string.incorrect_toast;
            }
        }
        btnWord1.setEnabled(false);
        btnWord2.setEnabled(false);
        btnWord3.setEnabled(false);
        btnWord4.setEnabled(false);
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
        setContentView(R.layout.activity_translation_word);

        getSupportActionBar().setTitle("Translation-Word");

        isTrue = false;

        tvTranslation = (TextView) findViewById(R.id.tvTranslation);


        btnWord1 = (Button) findViewById(R.id.btnWord1);
        btnWord1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(btnWord1.getText().toString());
                setBackgroundColor(btnWord1, isTrue);
            }
        });

        btnWord2 = (Button) findViewById(R.id.btnWord2);
        btnWord2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(btnWord2.getText().toString());
                setBackgroundColor(btnWord2, isTrue);
            }
        });

        btnWord3 = (Button) findViewById(R.id.btnWord3);
        btnWord3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(btnWord3.getText().toString());
                setBackgroundColor(btnWord3, isTrue);
            }
        });

        btnWord4 = (Button) findViewById(R.id.btnWord4);
        btnWord4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(btnWord4.getText().toString());
                setBackgroundColor(btnWord4, isTrue);
            }
        });

        // "Next" button is here
        nextBtn = (Button) findViewById(R.id.btnNext2);
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
