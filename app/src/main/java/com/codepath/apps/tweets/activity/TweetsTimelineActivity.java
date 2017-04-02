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

    @BindView(R.id.tabsStripTimeline) PagerSlidingTabStrip tabStripTimeline;
    @BindView(R.id.viewPagerTimeline) ViewPager viewPagerTimeline;

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


    @Override
    public void onSave(String tweetBody) {
        homeTimelineFragment.addNewTweet(tweetBody);
    }


    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        private String[] tabsTitle = { "Home", "Mentions" };

        public TweetsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
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
