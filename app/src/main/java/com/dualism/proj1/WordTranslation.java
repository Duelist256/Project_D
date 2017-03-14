package com.dualism.proj1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WordTranslation extends AppCompatActivity {

    private final String[] words1 = {"dog", "cat", "spoon", "fork", "bed",
    "key", "word", "car", "wheel", "morning",
    "evening", "Sun", "Moon", "star", "boss"};
    private final String[] transl1 = {"собака", "кошка", "ложка", "вилка", "кровать",
            "ключ", "слово", "автомобиль", "колесо", "утро",
            "вечер", "солнце", "луна", "звезда", "начальник"};

    TextView textView;
    Button answ1, answ2, answ3, answ4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_translation);

        textView = (TextView) findViewById(R.id.word);
        textView.setText(words1[1]);

        answ1 = (Button) findViewById(R.id.answ1);
        answ2 = (Button) findViewById(R.id.answ2);
        answ3 = (Button) findViewById(R.id.answ3);
        answ4 = (Button) findViewById(R.id.answ4);

        answ1.setText(transl1[1]);
        answ2.setText(transl1[2]);
        answ3.setText(transl1[3]);
        answ4.setText(transl1[4]);
    }

    public void answ1Btn(View view){
        if(answ1.getText().toString().equals("кошка")){
            Toast.makeText(WordTranslation.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(WordTranslation.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }

    public void answ2Btn(View view){
        if(answ2.getText().toString().equals("кошка")){
            Toast.makeText(WordTranslation.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(WordTranslation.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }

    public void answ3Btn(View view){
        if(answ3.getText().toString().equals("кошка")){
            Toast.makeText(WordTranslation.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(WordTranslation.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }

    public void answ4Btn(View view){
        if(answ4.getText().toString().equals("кошка")){
            Toast.makeText(WordTranslation.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(WordTranslation.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }
}