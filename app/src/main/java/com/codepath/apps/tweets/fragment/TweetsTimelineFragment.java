package com.codepath.apps.tweets.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.tweets.R;
import com.codepath.apps.tweets.TwitterApplication;
import com.codepath.apps.tweets.TwitterClient;
import com.codepath.apps.tweets.adapter.TweetsAdapter;
import com.codepath.apps.tweets.listener.EndlessScrollListener;
import com.codepath.apps.tweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static butterknife.ButterKnife.bind;

/**
 * Created by ajasuja on 4/1/17.
 */
public abstract class TweetsTimelineFragment extends Fragment {

    private List<Tweet> tweets;
    private TweetsAdapter tweetsAdapter;

    protected TwitterClient client;

    @BindView(R.id.listViewTweets) ListView listViewTweets;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<>();
        tweetsAdapter = new TweetsAdapter(getActivity(), tweets);
        client = TwitterApplication.getRestClient();
        populateTimeline(0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweets_timeline, container, false);
        bind(this, view);
        listViewTweets.setAdapter(tweetsAdapter);
        listViewTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                populateTimeline(page);
                return true;
            }
        });
        return view;
    }

    protected void addTweet(Tweet tweet) {
        tweets.add(0, tweet);
        tweetsAdapter.notifyDataSetChanged();
    }

    protected void addAllTweets(List<Tweet> tweets) {
        tweetsAdapter.addAll(tweets);
    }


    protected abstract void populateTimeline(int page);
}
