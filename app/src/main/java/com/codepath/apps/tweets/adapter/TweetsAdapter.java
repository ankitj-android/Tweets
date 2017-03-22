package com.codepath.apps.tweets.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.codepath.apps.tweets.models.Tweet;

import java.util.List;

/**
 * Created by ajasuja on 3/22/17.
 */

public class TweetsAdapter extends ArrayAdapter<Tweet> {

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }
}
