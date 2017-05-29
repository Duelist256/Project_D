package com.dualism.proj1.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dualism.proj1.R;
import com.dualism.proj1.Services.MyService;
import com.dualism.proj1.Services.PlayAudioService;


/**
 * A simple {@link Fragment} subclass.
 */
public class Menu3 extends Fragment {

    private EditText mEditText;
    private Button mButton;
    private String word;
    Intent intent;
    String strtext;

    public Menu3() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Menu 3");


        mEditText = (EditText) getActivity().findViewById(R.id.word_for_play);
        mButton = (Button) getActivity().findViewById(R.id.play);
        strtext = getArguments().getString("credentials");

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word = mEditText.getText().toString();
                //Intent intentOld = getActivity().getIntent();
                intent = new Intent(getActivity(), PlayAudioService.class);
                intent.putExtra("word", word);
                intent.putExtra("credentials", strtext);
                getActivity().startService(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_menu3, container, false);
    }

}
