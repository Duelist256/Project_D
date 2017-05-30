package com.dualism.proj1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AuditionActivity extends AppCompatActivity {

    private Button repeatButton, nextButton;
    private TextView rightTextView;
    private EditText auditionEditText;

    private String [] words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audition);

        words = new String[] {"cat", "dog", "help", "frog"};

        auditionEditText = (EditText) findViewById(R.id.audition_edit_text);

        rightTextView = (TextView) findViewById(R.id.audition_right_text_view);
        rightTextView.setVisibility(View.INVISIBLE);


        repeatButton = (Button) findViewById(R.id.audition_repeat_button);
        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        nextButton = (Button) findViewById(R.id.audition_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
