package com.dualism.proj1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LearnWordsActivity extends AppCompatActivity {
    DatabaseHelper myDb; //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_words);
        myDb = new DatabaseHelper(this);//
    }

    public void wordTransl(View view) {
        Intent intent = new Intent(this, WordTranslation.class);
        startActivity(intent);
    }
}
