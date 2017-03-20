package com.dualism.proj1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MyDB myDB;
    private String id;
    private Integer idInt = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new MyDB(this);
    }

    public void myDictionaries(View view) {
        Intent intent = new Intent(this, MyDictionariesActivity.class);
        startActivity(intent);
    }

    //
    public void learnWords(View view) {
        Intent intent = new Intent(this, LearnWordsActivity.class);
        startActivity(intent);
    }

    public void grammBook(View view) {
        id = idInt.toString();
        myDB.createRecords(id, "Kek");
        idInt++;
    }
}
