package com.codepath.apps.tweets.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.tweets.R;
import com.codepath.apps.tweets.models.Tweet;
import com.codepath.apps.tweets.util.CircularTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;

import static butterknife.ButterKnife.bind;

/**
 * Created by ajasuja on 3/22/17.
 */

public class TweetsAdapter extends ArrayAdapter<Tweet> {

    static class ViewHolder {
        @BindView(R.id.imageViewUserProfile) ImageView imageViewUserProfile;
        @BindView(R.id.textViewName) TextView textViewName;
        @BindView(R.id.textViewScreenName) TextView textViewScreenName;
        @BindView(R.id.textViewCreatedAt) TextView textViewCreatedAt;
        @BindView(R.id.textViewTweetText) TextView textViewTweetText;

        public ViewHolder(View view) {
            bind(this, view);
        }
    }

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //data
        Tweet tweet = getItem(position);
        //view
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //update view with data
        updateView(tweet, viewHolder);
        //return updated view
        return convertView;
    }

    private void updateView(Tweet tweet, ViewHolder viewHolder) {
        Picasso.with(getContext())
                .load(tweet.getUser().getProfileImageUrl())
                .transform(new CircularTransformation())
                .into(viewHolder.imageViewUserProfile);
        viewHolder.textViewName.setText(tweet.getUser().getName());
        viewHolder.textViewScreenName.setText(tweet.getUser().getScreenName());
        viewHolder.textViewCreatedAt.setText(tweet.getCreatedAt());
        viewHolder.textViewTweetText.setText(tweet.getText());
    }
}
