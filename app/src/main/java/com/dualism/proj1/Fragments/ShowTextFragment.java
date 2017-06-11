package com.dualism.proj1.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dualism.proj1.R;



/**
 * Created by Duelist on 11.06.2017.
 */

public class ShowTextFragment extends Fragment{
    private TextView fullText;

    public ShowTextFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fullText = (TextView) view.findViewById(R.id.fullText);
        fullText.setText(getArguments().getString("full text"));
        fullText.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_text, container, false);
    }
}
