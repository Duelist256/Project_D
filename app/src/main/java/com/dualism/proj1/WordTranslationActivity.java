package com.dualism.proj1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dualism.proj1.DB.DatabaseHandler;

import java.util.Arrays;
import java.util.Collections;

public class WordTranslationActivity extends AppCompatActivity {

    private TextView tvWord;

    private Button transl1;
    private Button transl2;
    private Button transl3;
    private Button transl4;
    private Button nextBtn;

    private WordTranslation[] TranslTest = new WordTranslation[] {
            new WordTranslation("dog", "собака"),
            new WordTranslation("cat", "кошка"),
            new WordTranslation("frog", "лягушка"),
            new WordTranslation("bird", "птица"),
    };

    //DatabaseHandler mDatabaseHandler = new DatabaseHandler(this);

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
    }

    private void checkAnswer(String answer){
        int messageResId = 0;

        for (int i = 0; i < TranslTest.length; i++) {
            if(tvWord.getText().toString().equals(TranslTest[i].getWord())) {
                if (answer.equals(TranslTest[i].getTranslation())) {
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
        setContentView(R.layout.activity_word_translation);
        //String[] appCategoryDetail = mDatabaseHandler.getAppCategoryDetail();

        tvWord = (TextView) findViewById(R.id.word);


        transl1 = (Button) findViewById(R.id.trans1);
        transl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(transl1.getText().toString());
            }
        });

        transl2 = (Button) findViewById(R.id.trans2);
        transl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(transl2.getText().toString());
            }
        });

        transl3 = (Button) findViewById(R.id.trans3);
        transl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(transl3.getText().toString());
            }
        });

        transl4 = (Button) findViewById(R.id.trans4);
        transl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(transl4.getText().toString());
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
        updateQuestion();
    }
}
