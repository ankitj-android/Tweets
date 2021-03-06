package com.codepath.apps.tweets;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
	public static final String REST_URL = "https://api.twitter.com/1.1/";
	public static final String REST_CONSUMER_KEY = "GhJ7ZlWn94NhBq1kNhgHsyoPj";
	public static final String REST_CONSUMER_SECRET = "yvexVJPA8PHPudSWCq3FOUvlZcXt0R25jOG74jwj4ESLMItkw8";
	public static final String REST_CALLBACK_URL = "oauth://cptweets";

	public static final int COUNT = 25;

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

    // ------------- home timeline ----------
	public void getHomeTimeline(int page, AsyncHttpResponseHandler responseHandler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", COUNT);
		params.put("page", page);
        getClient().get(apiUrl, params, responseHandler);
    }

	// ------------- mentions timeline ----------
	public void getMentionsTimeline(int page, AsyncHttpResponseHandler responseHandler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", COUNT);
		params.put("page", page);
		getClient().get(apiUrl, params, responseHandler);
	}

	// ------------- user timeline ----------
	public void getUserTimeline(int page, String screenName, AsyncHttpResponseHandler responseHandler) {
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", COUNT);
		params.put("page", page);
		params.put("screen_name", screenName);
		getClient().get(apiUrl, params, responseHandler);
	}

	// ------------- user info ----------
	public void getUserInfo(AsyncHttpResponseHandler responseHandler) {
		String apiUrl = getApiUrl("account/verify_credentials.json");
		getClient().get(apiUrl, null, responseHandler);
	}

	// ------------- post tweet -----------
	public void postTweet(String tweetBody, AsyncHttpResponseHandler responseHandler) {
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", tweetBody);

		getClient().post(apiUrl, params, responseHandler);
	}

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}
