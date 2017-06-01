package com.dualism.proj1.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dualism.proj1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by glad on 29.05.17.
 */

public class MyDictionaryFragment extends Fragment {
    private Button mButton;
    RequestQueue queue;
    String base64Credentials;

    public MyDictionaryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_dictionary, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("My Dictionary");

        Intent intent = getActivity().getIntent();
        base64Credentials = intent.getStringExtra("credentials");

        queue = Volley.newRequestQueue(getActivity());

        mButton = (Button) view.findViewById(R.id.get_words);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String TAG = "Lol";

                String url = "http://54.218.48.30:8080/words";


                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "Eeee");

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonWord = (JSONObject) response.get(i);
                                String word = jsonWord.getString("word");
                                String translation = jsonWord.getString("value");
                                Log.d(TAG, word + " - " + translation);
                            }
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
                        Toast.makeText(getActivity().getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String>  headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                /*Intent intent = getIntent();
                base64Credentials = intent.getStringExtra("credentials");*/
                        headers.put("Authorization", "Basic "+base64Credentials);
                        return headers;
                    }
                };;
                queue.add(jsonArrayRequest);
            }
        });
    }
}
