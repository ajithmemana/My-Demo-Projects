package com.qburst.contactlistupdater;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.BaseKeyListener;
import android.util.Log;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

@SuppressWarnings("deprecation")
public class FacebookActivity extends Activity {

	Facebook facebook = new Facebook("464704813617020");
	String userID = "571299732915087";
	String ALBUM_ID = "119392101600572";
	String link, imagePath;
	byte[] pic;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		facebook.authorizeCallback(requestCode, resultCode, data);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		imagePath = (String) b.get("imagePath");

		Bitmap bm = BitmapFactory.decodeFile(imagePath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		pic = baos.toByteArray();

		link = "https://www.google.co.in/";

		facebook.authorize(
				this,
				new String[] { "user_photos,publish_checkins,publish_actions,publish_stream,photo_upload" },
				new DialogListener() {

					@Override
					public void onCancel() {
						finish();
					}

					@Override
					public void onComplete(Bundle values) {
						String token = facebook.getAccessToken(); // get access
																	// token
						save(token);
						Log.d("TAG", "Entered Facebook onComplete");
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

	protected void postToWall() {
		// TODO Auto-generated method stub

		Bundle parameters = new Bundle();

		AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook);

		parameters.putString(Facebook.TOKEN, facebook.getAccessToken());
		parameters.putByteArray("picture", pic);

		mAsyncRunner.request("me/photos", parameters, "POST",
				new UploadListener(), null);
	}

	private void save(String token) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		prefs.edit().putString("Token", token).commit();
	}

	public class UploadListener extends BaseKeyListener implements
			RequestListener {

		@Override
		public void onComplete(String response, Object state) {
			// TODO Auto-generated method stub
			Toast.makeText(FacebookActivity.this, "Picture posted !!!",
					Toast.LENGTH_SHORT).show();
			finish();
		}

		public void onFacebookError(FacebookError e, Object state) {
			// TODO Auto-generated method stub

		}

		public Bitmap getInputType(Bitmap img) {
			// TODO Auto-generated method stub
			return img;
		}

		@Override
		public int getInputType() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void onIOException(IOException e, Object state) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFileNotFoundException(FileNotFoundException e,
				Object state) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onMalformedURLException(MalformedURLException e,
				Object state) {
			// TODO Auto-generated method stub

		}

	}
}