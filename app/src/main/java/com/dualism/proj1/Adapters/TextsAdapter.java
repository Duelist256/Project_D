package com.dualism.proj1.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dualism.proj1.Fragments.ShowTextFragment;
import com.dualism.proj1.MainActivity;
import com.dualism.proj1.R;

import java.util.List;

/**
 * Created by Duelist on 09.06.2017.
 */

public class TextsAdapter extends RecyclerView.Adapter<TextsAdapter.ViewHolder>{
    private List<String> mDataset;
    private static final String TAG = "TextsAdapter";

    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        //public CheckBox mCheckBox;

        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                    //mCheckBox.setChecked(!mCheckBox.isChecked());
                }
            });

            mTextView = (TextView) v.findViewById(R.id.text_item);
            //mCheckBox = (CheckBox) v.findViewById(R.id.text_check_box);
        }

        public TextView getTextView() {
            return mTextView;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TextsAdapter(List<String> myDataset, Context context) {
        mContext = context;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TextsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_item, parent, false);
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

        final String item = mDataset.get(position);

        if (mDataset.get(position).length() > 10) {
            String arr[] = mDataset.get(position).split(" ", 3);
            if (arr[0].compareTo(arr[1]) == 1){
                holder.getTextView().setText(arr[0].replace("\n", " ") + "...");
            } else {
                holder.getTextView().setText(arr[0].replace("\n", " ") + " " + arr[1].replace("\n", " ") + "...");
            }
        } else {
            holder.getTextView().setText(mDataset.get(position));
        }

        holder.getTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentJump(item);
            }
        });
    }

    private void fragmentJump(String mItemSelected) {
        Fragment mFragment = null;
        mFragment = new ShowTextFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("full text", mItemSelected);
        mFragment.setArguments(mBundle);
        switchContent(R.id.content_main, mFragment);
    }

    public void switchContent(int id, Fragment fragment) {
        if (mContext == null)
            return;
        if (mContext instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) mContext;
            Fragment frag = fragment;
            mainActivity.switchContent(id, frag);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
