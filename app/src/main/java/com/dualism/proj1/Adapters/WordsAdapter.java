package com.dualism.proj1.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dualism.proj1.DB.Word;
import com.dualism.proj1.R;

import java.util.List;

/**
 * Created by Duelist on 09.06.2017.
 */

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.ViewHolder>{
    private List<String> mDatasetWords;
    private List<String> mDatasetTranslations;

    private List<Word> mDatasetWord;

    private static final String TAG = "WordsAdapter";

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewWord;
        public TextView mTextViewTranslation;

        public CheckBox mCheckBox;

        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                    mCheckBox.setChecked(!mCheckBox.isChecked());
                }
            });

            mTextViewWord = (TextView) v.findViewById(R.id.words_word_element);
            mTextViewTranslation = (TextView) v.findViewById(R.id.words_translation_element);
            mCheckBox = (CheckBox) v.findViewById(R.id.words_check_box);
        }

        public TextView getTextViewWord() {
            return mTextViewWord;
        }
        public TextView getTextViewTranslation() {
            return mTextViewTranslation;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public WordsAdapter(List<Word> myDatasetWord) {
        mDatasetWord = myDatasetWord;
        //mDatasetTranslations = myDatasetTranslations;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public WordsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.word_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)

    //@Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Log.d(TAG, "Element " + position + " set.");
        holder.getTextViewWord().setText(mDatasetWord.get(position).getWord());
        holder.getTextViewTranslation().setText(mDatasetWord.get(position).getTranslation());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDatasetWord.size();
    }
}
