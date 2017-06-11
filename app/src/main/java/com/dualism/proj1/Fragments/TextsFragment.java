package com.dualism.proj1.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dualism.proj1.Adapters.TextsAdapter;
import com.dualism.proj1.Adapters.WordsAdapter;
import com.dualism.proj1.DB.Word;
import com.dualism.proj1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class TextsFragment extends Fragment {

    private String base64Credentials;
    private RequestQueue queue;
    private Button mButton;



    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String[] myDataSet = {"elem1", "elem2", "elem3", "elem4", "elem5", "elem6",
            "elem7", "elem8", "elem9", "elem10", "elem11", "elem12"};

    private List<String> myDSet;

    public TextsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Reading");

        queue = Volley.newRequestQueue(getActivity());

        Intent intent = getActivity().getIntent();
        base64Credentials = intent.getStringExtra("credentials");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.texts_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        myDSet = new ArrayList<>(Arrays.asList(myDataSet));

        //mAdapter = new TextsAdapter(myDSet, getContext());
        //mRecyclerView.setAdapter(mAdapter);

        //mButton = (Button) getActivity().findViewById(R.id.showTexts);

        getTexts();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_texts, container, false);
    }

    private void getTexts() {
        final String TAG = "Texts";
        final List<String> texts = new ArrayList<>();
        //myDataSet1.clear();
        //myDataSet2.clear();
        Log.d(TAG, "Starting the request");
        String url = "http://54.218.48.30:8080/texts";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "Response!");

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonWord = (JSONObject) response.get(i);
                        String text = jsonWord.getString("text");

                        texts.add(text);
                        Log.d(TAG, text);


                        if (i == 7) {
                            Log.d(TAG, "all words added to ArrayList");
                        }
                    }
                    mAdapter = new TextsAdapter(texts, getContext());
                    mRecyclerView.setAdapter(mAdapter);
                    //databaseHelper.addWords(words);
                    //Log.d("DB", "words were added");
                    //mAdapter = new WordsAdapter(databaseHelper.getAllWords());
                    //mRecyclerView.setAdapter(mAdapter);
                    //progress.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //progress.dismiss();
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "Basic "+base64Credentials);
                return headers;
            }
        };;
        queue.add(jsonArrayRequest);
    }

}
