package com.dualism.proj1.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.dualism.proj1.DB.DBHelper;
import com.dualism.proj1.DB.Word;
import com.dualism.proj1.MainActivity;
import com.dualism.proj1.R;
import com.dualism.proj1.SimpleDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by glad on 29.05.17.
 */

public class MyDictionaryFragment extends Fragment {
    private Button mButton;
    RequestQueue queue;
    String base64Credentials;
    Word word1;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private EditText searchEditText;
    
    private String[] myDSet1 = {"word1", "word2", "word3", "word4", "word5", "word6",
            "word7", "word8", "word9", "word10", "word11", "word12"};
    private String[] myDSet2 = {"translation1", "translation2", "translation3", "translation4", "translation5", "translation6",
            "translation7", "translation8", "translation9", "translation10", "translation11", "translation12"};

    private List<Word> myDatasetWords;
    private List<String> myDataSet1;
    private List<String> myDataSet2;
    private List<Word> mWords;

    private ProgressDialog progress;

    public MyDictionaryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_dictionary, container, false);
        // Inflate the layout for this fragment
        return view;
    }
    private DBHelper databaseHelper;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("My Dictionary");

        myDataSet1 = new ArrayList<>(Arrays.asList(myDSet1));
        myDataSet2 = new ArrayList<>(Arrays.asList(myDSet2));

        myDatasetWords = new ArrayList<>();
        for (int i = 0; i < myDSet1.length; i++) {
            Word newWord = new Word();
            newWord.setWord(myDataSet1.get(i));
            newWord.setWord(myDataSet2.get(i));
        }

        searchEditText = (EditText) view.findViewById(R.id.my_dict_edit_text);
        databaseHelper = DBHelper.getInstance(getActivity());

        int count = databaseHelper.countWords();


        Intent intent = getActivity().getIntent();
        base64Credentials = intent.getStringExtra("credentials");

        queue = Volley.newRequestQueue(getActivity());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.wordss_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new WordsAdapter(myDatasetWords);
        mRecyclerView.setAdapter(mAdapter);

        if (count == 0) {
            progress = ProgressDialog.show(getActivity(), "", "Loading words...", true);
            getWords();
        } else {
            progress = ProgressDialog.show(getActivity(), "", "Loading words...", true);
            mWords = databaseHelper.getAllWords();
            mAdapter = new WordsAdapter(mWords);
            mRecyclerView.setAdapter(mAdapter);
            progress.dismiss();
        }

        addTextListener();
    }

    public void addTextListener(){

        searchEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                final List<Word> filteredWordList = new ArrayList<>();

                for (int i = 0; i < mWords.size(); i++) {

                    final String text = mWords.get(i).getWord().toLowerCase();
                    if (text.contains(query)) {
                        Word newWord = new Word();
                        newWord.setWord(mWords.get(i).getWord());
                        newWord.setTranslation(mWords.get(i).getTranslation());
                        filteredWordList.add(newWord);

                    }
                }

                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mAdapter = new WordsAdapter(filteredWordList);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();  // data set changed
            }
        });
    }


    private void getWords() {

        final String TAG = "Lol";
        final List<Word> words = new ArrayList<Word>();
        myDataSet1.clear();
        myDataSet2.clear();
        Log.d(TAG, "Starting the request");
        String url = "http://54.218.48.30:8080/words";

        databaseHelper.deleteAllWords();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "Response!");

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonWord = (JSONObject) response.get(i);
                        String word = jsonWord.getString("word");
                        String translation = jsonWord.getString("value");

                        myDataSet1.add(word);
                        myDataSet2.add(translation);

                        word1 = new Word();
                        word1.setWord(word);
                        word1.setTranslation(translation);
                        words.add(word1);

                        if (i == 51380) {
                            Log.d(TAG, "all words added to ArrayList");
                        }
                    }
                    databaseHelper.addWords(words);
                    Log.d("DB", "words were added");
                    mAdapter = new WordsAdapter(databaseHelper.getAllWords());
                    mRecyclerView.setAdapter(mAdapter);
                    progress.dismiss();
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
                progress.dismiss();
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
