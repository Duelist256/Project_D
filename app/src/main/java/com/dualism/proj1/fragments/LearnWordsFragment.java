package com.dualism.proj1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dualism.proj1.MainActivity;
import com.dualism.proj1.R;
import com.dualism.proj1.SpeakingActivity;
import com.dualism.proj1.TranslationWordActivity;
import com.dualism.proj1.WordTranslationActivity;

/**
 * Created by glad on 29.05.17.
 */

public class LearnWordsFragment extends Fragment {

    Button wordTranslationButton, translationWordButton, speakingButton;

    public LearnWordsFragment () {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Learn Words");
        //TextView textView = (TextView) getView().findViewById(R.id.word_translation_button);
        //textView.setText("ekea"); // mowno delat tak, mowet bb|t dawe nuwno!!11



        wordTranslationButton = (Button) getView().findViewById(R.id.word_translation_button);
        wordTranslationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WordTranslationActivity.class);
                intent.putExtra("credentials", getActivity().getIntent().getStringExtra("credentials"));
                startActivity(intent);
            }
        });

        translationWordButton = (Button) getView().findViewById(R.id.translation_word_button);
        translationWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TranslationWordActivity.class);
                intent.putExtra("credentials", getActivity().getIntent().getStringExtra("credentials"));
                startActivity(intent);
            }
        });

        speakingButton = (Button) getView().findViewById(R.id.speaking_button);
        speakingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SpeakingActivity.class);
                intent.putExtra("credentials", getActivity().getIntent().getStringExtra("credentials"));
                startActivity(intent);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learn_words, container, false);
    }
}
