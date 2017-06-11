package com.dualism.proj1.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import com.dualism.proj1.AuditionActivity;
import com.dualism.proj1.R;
import com.dualism.proj1.SpeakingActivity;
import com.dualism.proj1.TranslationWordActivity;
import com.dualism.proj1.WordTranslationActivity;

/**
 * Created by glad on 29.05.17.
 */

public class LearnWordsFragment extends Fragment {

    Button wordTranslationButton, translationWordButton, auditonButton, speakingButton;

    public LearnWordsFragment () {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Learn Words");
        //TextView textView = (TextView) getView().findViewById(R.id.word_translation_button);
        //textView.setText("ekea"); // mowno delat tak, mowet bb|t dawe nuwno!!11

        wordTranslationButton = (Button) getView().findViewById(R.id.word_translation_button);
        wordTranslationButton.setBackgroundResource(android.R.drawable.btn_default);
        wordTranslationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WordTranslationActivity.class);
                intent.putExtra("credentials", getActivity().getIntent().getStringExtra("credentials"));
                startActivity(intent);
            }
        });

        translationWordButton = (Button) getView().findViewById(R.id.translation_word_button);
        translationWordButton.setBackgroundResource(android.R.drawable.btn_default);
        translationWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TranslationWordActivity.class);
                intent.putExtra("credentials", getActivity().getIntent().getStringExtra("credentials"));
                startActivity(intent);
            }
        });

        final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        animation.setDuration(500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in


        auditonButton = (Button) getView().findViewById(R.id.audio_word_button);
        auditonButton.startAnimation(animation);
        auditonButton.setBackgroundResource(android.R.drawable.btn_default);
        auditonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.clearAnimation();
                Intent intent = new Intent(getActivity(), AuditionActivity.class);
                intent.putExtra("credentials", getActivity().getIntent().getStringExtra("credentials"));
                startActivity(intent);
            }
        });

        speakingButton = (Button) getView().findViewById(R.id.speaking_button);
        speakingButton.setBackgroundResource(android.R.drawable.btn_default);
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
