package com.codepath.apps.tweets.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.tweets.R;
import com.codepath.apps.tweets.fragment.ComposeDialogFragment;
import com.codepath.apps.tweets.fragment.ComposeDialogFragment.ComposeListener;
import com.codepath.apps.tweets.fragment.HomeTimelineFragment;
import com.codepath.apps.tweets.fragment.MentionsTimelineFragment;

import butterknife.BindView;

import static butterknife.ButterKnife.bind;

public class TweetsTimelineActivity extends AppCompatActivity implements ComposeListener {

//    private TwitterClient client;
//    private List<Tweet> tweets;
//    private TweetsAdapter tweetsAdapter;
//
//    @BindView(R.id.listViewTweets) ListView listViewTweets;

    @BindView(R.id.tabsStripTimeline) PagerSlidingTabStrip tabStripTimeline;
    @BindView(R.id.viewPagerTimeline) ViewPager viewPagerTimeline;

    private Long sinceId = 1L;

    private HomeTimelineFragment homeTimelineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_timeline);
        bind(this);
        if(savedInstanceState == null) {
            homeTimelineFragment = new HomeTimelineFragment();
        }
        viewPagerTimeline.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        tabStripTimeline.setViewPager(viewPagerTimeline);

//        client = TwitterApplication.getRestClient();
//        tweets = new ArrayList<>();
//        tweetsAdapter = new TweetsAdapter(this, tweets);
//
//        listViewTweets.setAdapter(tweetsAdapter);
//        listViewTweets.setOnScrollListener(new EndlessScrollListener() {
//            @Override
//            public boolean onLoadMore(int page, int totalItemsCount) {
//                populateTimeline(page);
//                return true;
//            }
//        });
//        populateHomeTimeline(0);
//        populateMentionsTimeline();
    }

//    // calling api, deserializing and updating the view.
//    private void populateHomeTimeline(int page) {
//        Log.d("fetching timeline ... " , String.valueOf(sinceId));
//        client.getHomeTimeline(page, new JsonHttpResponseHandler() {
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                tweetsAdapter.addAll(Tweet.fromJsonArray(response));
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//            }
//        });
//    }
//
//    private void populateMentionsTimeline() {
//        Log.d("flow", "fetching mentions timeline ... ");
//        client.getMentionsTimeline(new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                tweetsAdapter.addAll(Tweet.fromJsonArray(response));
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//            }
//        });
//
//    }


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


    @Override
    public void onSave(String tweetBody) {
        homeTimelineFragment.addNewTweet(tweetBody);
//        client.postTweet(tweetBody, new JsonHttpResponseHandler(){
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                try {
//                    Tweet tweet = Tweet.fromJson(response);
//                    tweets.add(0, tweet);
//                    tweetsAdapter.notifyDataSetChanged();
//
////                    tweetsAdapter.add(tweet);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//            }
//        });
    }


    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        private String[] tabsTitle = { "Home", "Mentions" };

        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return homeTimelineFragment;
            } else if (position == 1) {
                return new MentionsTimelineFragment();
            } else {
                return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabsTitle[position];
        }

        @Override
        public int getCount() {
            return tabsTitle.length;
        }
    }
}
