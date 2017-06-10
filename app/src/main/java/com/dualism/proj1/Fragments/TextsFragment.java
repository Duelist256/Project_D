package com.dualism.proj1.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dualism.proj1.Adapters.TextsAdapter;
import com.dualism.proj1.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TextsFragment extends Fragment {

    private EditText mEditText;
    private Button mButton;
    private String word;
    Intent intent;
    String strtext;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String[] myDataSet = {"elem1", "elem2", "elem3", "elem4", "elem5", "elem6",
            "elem7", "elem8", "elem9", "elem10", "elem11", "elem12"};

    public TextsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Texts");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.texts_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new TextsAdapter(myDataSet);
        mRecyclerView.setAdapter(mAdapter);

        mButton = (Button) getActivity().findViewById(R.id.showTexts);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_texts, container, false);
    }

}
