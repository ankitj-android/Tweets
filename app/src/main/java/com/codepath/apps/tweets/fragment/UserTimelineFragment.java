package com.codepath.apps.tweets.fragment;

import android.util.Log;

import com.codepath.apps.tweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ajasuja on 4/1/17.
 */
public class UserTimelineFragment extends TweetsTimelineFragment {

    @Override
    protected void populateTimeline(int page) {
        Log.d("flow", "fetching home timeline ... ");
        client.getUserTimeline(page, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                addAllTweets(Tweet.fromJsonArray(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

}
