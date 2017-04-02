package com.codepath.apps.tweets.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.tweets.R;
import com.codepath.apps.tweets.TwitterApplication;
import com.codepath.apps.tweets.TwitterClient;
import com.codepath.apps.tweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.BindView;
import cz.msebera.android.httpclient.Header;

import static butterknife.ButterKnife.bind;

/**
 * Created by ajasuja on 4/1/17.
 */

public class ProfileHeaderFragment extends Fragment {

    @BindView(R.id.imageViewUser) ImageView imageViewUserProfile;
    @BindView(R.id.textViewUserName) TextView textViewUserName;
    @BindView(R.id.textViewTagline) TextView textViewTagLine;
    @BindView(R.id.textViewFollowers) TextView textViewFollowers;
    @BindView(R.id.textViewFollowings) TextView textViewFollowings;

    public static ProfileHeaderFragment newInstance(User user) {
        ProfileHeaderFragment profileHeaderFragment = new ProfileHeaderFragment();
        Bundle args = new Bundle();
        args.putParcelable("user", Parcels.wrap(user));
        profileHeaderFragment.setArguments(args);
        return profileHeaderFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_header, container, false);
        bind(this, view);
        Parcelable parcelableUser = getArguments().getParcelable("user");
        if (parcelableUser != null) {
            User user = Parcels.unwrap(parcelableUser);
            populateProfileHeader(user);
        }
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parcelable parcelableUser = getArguments().getParcelable("user");
        if (parcelableUser == null) {
            TwitterClient client = TwitterApplication.getRestClient();
            client.getUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        User user = User.fromJson(response);
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
