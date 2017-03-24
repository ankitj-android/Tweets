package com.codepath.apps.tweets.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.tweets.R;
import com.codepath.apps.tweets.TwitterApplication;
import com.codepath.apps.tweets.TwitterClient;
import com.codepath.apps.tweets.adapter.TweetsAdapter;
import com.codepath.apps.tweets.fragment.ComposeDialogFragment;
import com.codepath.apps.tweets.fragment.ComposeDialogFragment.ComposeListener;
import com.codepath.apps.tweets.listener.EndlessScrollListener;
import com.codepath.apps.tweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cz.msebera.android.httpclient.Header;

import static butterknife.ButterKnife.bind;

public class HomeTimelineActivity extends AppCompatActivity implements ComposeListener {

    private TwitterClient client;
    private List<Tweet> tweets;
    private TweetsAdapter tweetsAdapter;

    @BindView(R.id.listViewTweets) ListView listViewTweets;

    private Long sinceId = 1L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_timeline);
        bind(this);

        client = TwitterApplication.getRestClient();
        tweets = new ArrayList<>();
        tweetsAdapter = new TweetsAdapter(this, tweets);

        listViewTweets.setAdapter(tweetsAdapter);
        listViewTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                populateHomeTimeline(page);
                return true;
            }
        });
        populateHomeTimeline(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_timeline, menu);
        Drawable yourdrawable = menu.getItem(0).getIcon(); // change 0 with 1,2 ...
        yourdrawable.mutate();
        yourdrawable.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miCompose :
                Log.d("[DEBUG]", "composing called ...");
                showComposeDialog();
                return true;
            default:
                Log.d("[DEBUG]", "default item selected");
                return super.onOptionsItemSelected(item);
        }
    }

    private void showComposeDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ComposeDialogFragment settingsDialogFragment = new ComposeDialogFragment();
        settingsDialogFragment.show(fragmentManager, "Settings");
    }

    // calling api, deserializing and updating the view.
    private void populateHomeTimeline(int page) {
        Log.d("fetching timeline ... " , String.valueOf(sinceId));
        client.getHomeTimeline(page, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                tweetsAdapter.addAll(Tweet.fromJsonArray(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    @Override
    public void onSave(String tweetBody) {

        client.postTweet(tweetBody, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Tweet tweet = Tweet.fromJson(response);
                    tweets.add(0, tweet);
                    tweetsAdapter.notifyDataSetChanged();

//                    tweetsAdapter.add(tweet);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
