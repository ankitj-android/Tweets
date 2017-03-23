package com.codepath.apps.tweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ajasuja on 3/21/17.
 */

public class User {
    private long id;
    private String name;
    private String screenName;
    private String profileImageUrl;

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

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.id = jsonObject.getLong("id");
        user.name = jsonObject.getString("name");
        user.screenName = jsonObject.getString("screen_name");
        user.profileImageUrl = jsonObject.getString("profile_image_url");
        return user;
    }
}
