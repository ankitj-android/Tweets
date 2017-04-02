package com.codepath.apps.tweets.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.tweets.R;
import com.codepath.apps.tweets.TwitterApplication;
import com.codepath.apps.tweets.TwitterClient;
import com.codepath.apps.tweets.fragment.UserTimelineFragment;
import com.codepath.apps.tweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import cz.msebera.android.httpclient.Header;

import static butterknife.ButterKnife.bind;
import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

public class ProfileActivity extends AppCompatActivity {

    private TwitterClient client;

    @BindView(R.id.imageViewUser) ImageView imageViewUserProfile;
    @BindView(R.id.textViewUserName) TextView textViewUserName;
    @BindView(R.id.textViewTagline) TextView textViewTagLine;
    @BindView(R.id.textViewFollowers) TextView textViewFollowers;
    @BindView(R.id.textViewFollowings) TextView textViewFollowings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bind(this);
        client = TwitterApplication.getRestClient();
        // only if it is the first time
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                User user = null;
                try {
                    user = User.fromJson(response);
                    getSupportActionBar().setTitle(user.getScreenName());
                    populateProfileHeader(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
        if (savedInstanceState == null) {
            String screenName = getIntent().getStringExtra("screenName");
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flContainer, userTimelineFragment);
            fragmentTransaction.commit();
        }
    }

    private void populateProfileHeader(User user) {
        Picasso.with(getContext())
                .load(user.getProfileImageUrl())
                .into(imageViewUserProfile);
        textViewUserName.setText(user.getName());
        textViewTagLine.setText(user.getDescription());
        textViewFollowers.setText(user.getFollowersCount() + " Followers");
        textViewFollowings.setText(user.getFollowingsCount() + " Followings");
    }
}
