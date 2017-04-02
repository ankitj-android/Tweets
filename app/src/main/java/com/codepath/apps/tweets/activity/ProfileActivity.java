package com.codepath.apps.tweets.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.tweets.R;
import com.codepath.apps.tweets.fragment.ProfileHeaderFragment;
import com.codepath.apps.tweets.fragment.UserTimelineFragment;
import com.codepath.apps.tweets.models.User;

import org.parceler.Parcels;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if (savedInstanceState == null) {
            String screenName = getIntent().getStringExtra("screenName");
            User user = Parcels.unwrap(getIntent().getParcelableExtra("user"));
            getSupportActionBar().setTitle(user != null? user.getScreenName() : "");
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);
//            ProfileHeaderFragment profileHeaderFragment =
//                    (user != null) ? ProfileHeaderFragment.newInstance(user) : new ProfileHeaderFragment();
            ProfileHeaderFragment profileHeaderFragment = ProfileHeaderFragment.newInstance(user);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutProfileHeader, profileHeaderFragment);
            fragmentTransaction.replace(R.id.frameLayoutUserTimeline, userTimelineFragment);
            fragmentTransaction.commit();
        }
    }
}
