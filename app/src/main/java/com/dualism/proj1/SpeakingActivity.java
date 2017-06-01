package com.dualism.proj1;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dualism.proj1.Services.PlayAudioService;

import java.util.ArrayList;
import java.util.Locale;

public class SpeakingActivity extends AppCompatActivity {

    private TextView wordTextView;
    private TextView rightTextView;
    private Button sayButton;
    private Button nextButton;
    private String [] words;

    private int currentIndex;

    private Intent intent;
    private Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaking);

        getSupportActionBar().setTitle("Speaking");

        intent = getIntent();

        words = new String[] {"cat", "dog", "help", "frog"};

        wordTextView = (TextView) findViewById(R.id.speaking_word_text_view);
        wordTextView.setText(words[currentIndex]);

        wordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1.putExtra("word", words[currentIndex]);
                intent1.putExtra("credentials", intent.getStringExtra("credentials"));
                startService(intent1);
            }
        });

        rightTextView = (TextView) findViewById(R.id.right_text_view);
        rightTextView.setVisibility(View.INVISIBLE);


        sayButton = (Button) findViewById(R.id.speaking_say_button);
        sayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);

                try {
                    startActivityForResult(intent, 200);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(), "Intent problem", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nextButton = (Button) findViewById(R.id.speaking_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex +1) % words.length;
                updateWord();
            }
        });

        updateWord();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                //textView.setText(result.get(0));
                if (wordTextView.getText().toString().equals(result.get(0))) {
                    rightTextView.setText("Right!");
                    rightTextView.setBackgroundResource(R.color.trueAnswer);
                    rightTextView.setVisibility(View.VISIBLE);
                    sayButton.setVisibility(View.INVISIBLE);
                } else {
                    rightTextView.setText("Try again!");
                    rightTextView.setBackgroundResource(R.color.falseAnswer);
                    rightTextView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void updateWord(){
        wordTextView.setText(words[currentIndex]);
        rightTextView.setVisibility(View.INVISIBLE);
        sayButton.setVisibility(View.VISIBLE);

        intent1 = new Intent(this, PlayAudioService.class);
        intent1.putExtra("word", words[currentIndex]);
        intent1.putExtra("credentials", intent.getStringExtra("credentials"));
        startService(intent1);
    }
}
