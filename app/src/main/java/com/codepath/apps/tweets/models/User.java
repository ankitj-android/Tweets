package com.codepath.apps.tweets.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by ajasuja on 3/21/17.
 */
@Parcel
public class User {
    private long id;
    private String name;
    private String screenName;
    private String profileImageUrl;
    private String description;
    private int followersCount;
    private int followingsCount;

    // empty constructor needed by the Parceler library
    public User() {
    }
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return "@"+screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingsCount() {
        return followingsCount;
    }

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.id = jsonObject.getLong("id");
        user.name = jsonObject.getString("name");
        user.screenName = jsonObject.getString("screen_name");
        user.profileImageUrl = jsonObject.getString("profile_image_url");
        user.description = jsonObject.getString("description");
        user.followersCount = jsonObject.getInt("followers_count");
        user.followingsCount = jsonObject.getInt("friends_count");
        return user;
    }

}
