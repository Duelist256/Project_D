package com.dualism.proj1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MyDictionariesActivity extends AppCompatActivity {

    //String kekStr = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dictionaries);
    }

    public void testButton(View view) {
        TextView textView = (TextView) findViewById(R.id.editText);

        /*if (textView.getText().toString().equals("Lol")){
            kekStr = "Kek";
            textView.setText(kekStr);
        } else if (textView.getText().toString().equals("Kek")){
            kekStr = "Chebureck";
            textView.setText(kekStr);
        } else {
            kekStr = "Lol";
            textView.setText(kekStr);
        }*/
    }
}
