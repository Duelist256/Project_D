package com.dualism.proj1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dualism.proj1.Services.PlayAudioService;

public class AuditionActivity extends AppCompatActivity {

    private Button repeatButton, nextButton, checkButton;
    private TextView rightTextView;
    private EditText auditionEditText;

    private String [] words;
    private int currentIndex;

    private Intent intent;
    private Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audition);

        getSupportActionBar().setTitle("Audition");

        words = new String[] {"cat", "dog", "help", "frog"};

        auditionEditText = (EditText) findViewById(R.id.audition_edit_text);

        rightTextView = (TextView) findViewById(R.id.audition_right_text_view);
        rightTextView.setVisibility(View.INVISIBLE);

        intent = getIntent();

        repeatButton = (Button) findViewById(R.id.audition_repeat_button);
        repeatButton.setBackgroundResource(android.R.drawable.btn_default);
        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1.putExtra("word", words[currentIndex]);
                intent1.putExtra("credentials", intent.getStringExtra("credentials"));
                startService(intent1);
            }
        });

        checkButton = (Button) findViewById(R.id.audition_check_button);
        checkButton.setBackgroundResource(android.R.drawable.btn_default);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auditionEditText.getText().toString().equalsIgnoreCase(words[currentIndex])) {
                    rightTextView.setVisibility(View.VISIBLE);
                    rightTextView.setText("Right!");
                    rightTextView.setBackgroundResource(R.color.trueAnswer);
                    nextButton.setVisibility(View.VISIBLE);
                    auditionEditText.setFocusable(false);
                    checkButton.setEnabled(false);
                } else {
                    rightTextView.setVisibility(View.VISIBLE);
                    rightTextView.setText("Try now!");
                    rightTextView.setBackgroundResource(R.color.falseAnswer);
                }
            }
        });

        nextButton = (Button) findViewById(R.id.audition_next_button);
        nextButton.setBackgroundResource(android.R.drawable.btn_default);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex +1) % words.length;
                updateWord();
            }
        });

        updateWord();
    }

    private void updateWord() {
        intent1 = new Intent(this, PlayAudioService.class);
        intent1.putExtra("word", words[currentIndex]);
        intent1.putExtra("credentials", intent.getStringExtra("credentials"));
        startService(intent1);
        nextButton.setVisibility(View.INVISIBLE);
        rightTextView.setVisibility(View.INVISIBLE);
        auditionEditText.setText("");
        auditionEditText.setFocusableInTouchMode(true);
        checkButton.setEnabled(true);
    }
}
