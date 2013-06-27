package com.qburst.newsreader.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

//161902483969691

public class FacebookActivity extends Activity {

	@SuppressWarnings("deprecation")
	Facebook facebook = new Facebook("161902483969691");
	String link;

	@SuppressWarnings("deprecation")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		facebook.authorizeCallback(requestCode, resultCode, data);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		link = getIntent().getStringExtra("query");
		Log.d("link", link);

		facebook.authorize(this, new DialogListener() {

			@Override
			public void onCancel() {
			}

			@Override
			public void onComplete(Bundle values) {
				String token = facebook.getAccessToken(); // get access token
				save(token);
				postToWall();

			}

			@Override
			public void onError(DialogError e) {
			}

			@Override
			public void onFacebookError(FacebookError error) {
			}
		});
	}

	@SuppressWarnings("deprecation")
	protected void postToWall() {
		// TODO Auto-generated method stub
		Bundle parameters = new Bundle();

		parameters.putString("link", link);
		facebook.dialog(FacebookActivity.this, "feed", parameters,
				new DialogListener() {

			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				finish();
			}

			@Override
			public void onComplete(Bundle values) {
				// TODO Auto-generated method stub

				finish();
			}

			@Override
			public void onError(DialogError e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFacebookError(FacebookError e) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void save(String token) {
		Toast.makeText(this, "token" + token, Toast.LENGTH_LONG).show();
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		prefs.edit().putString("Token", token).commit();
	}
}