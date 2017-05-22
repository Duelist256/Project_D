package com.dualism.proj1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dualism.proj1.DB.DatabaseHandler;

import java.util.List;

import com.dualism.proj1.DB.WordTransl;

public class MainActivity extends AppCompatActivity {
    DatabaseHandler db;
    TextView tvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);

        System.out.println("Inserting ..");
        db.addWordTransl(new WordTransl("key", "ключ"));
        db.addWordTransl(new WordTransl("whale", "кит"));
        db.addWordTransl(new WordTransl(44, "dog", "собака")); // but it outputs id = 3
        db.addWordTransl(new WordTransl("coat", "пальто"));

        tvUsername = (TextView) findViewById(R.id.tvUsername);
        System.out.println("Reading all contacts..");
        List<WordTransl> wordTranls = db.getAllWordTransls();
        for (WordTransl wr : wordTranls) {
            String log = "Id: " + wr.getID() + " ,Word: " + wr.getWord() + " ,Translation: " + wr.getTranslation_value();
            System.out.print("Name: ");
            System.out.println(log);
        }

        Intent intent = getIntent();
        String usrname = intent.getStringExtra("Ok");
        tvUsername.setText("Hello, "+ usrname + "!");
        //db.deleteAll();
    }

    public void myDictionaries(View view) {
        Intent intent = new Intent(this, MyDictionariesActivity.class);
        startActivity(intent);
    }

    //
    public void learnWords(View view) {
        Intent intent = new Intent(this, LearnWordsActivity.class);
        //intent.putExtra();
        startActivity(intent);
    }

    public void getAudio(View view) {
        Intent intent = new Intent(this, GetAudio.class);
        intent.putExtra("credentials", getIntent().getStringExtra("credentials"));
        startActivity(intent);
    }
}
