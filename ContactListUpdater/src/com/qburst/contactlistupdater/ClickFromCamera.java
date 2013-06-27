package com.qburst.contactlistupdater;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

@SuppressLint("SimpleDateFormat")
public class ClickFromCamera extends Activity {

	private static final String TAG = "CameraDemo";
	Camera mCamera;
	Preview preview;
	ImageButton buttonClick;
	ImageButton frontCamera;
	SurfaceView mySurfaceView;
	SurfaceHolder surfaceHolder;
	File pathToFolder;
	String path;
	FileOutputStream outStream;
	Bitmap bmp, bMapRotate;
	static int cameraFlag = 0;
	FrameLayout fl;
	int bitmapWidth = 0;
	int bitmapHeight = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_click_from_camera);

		mCamera = getCameraInstance();

		preview = new Preview(this, mCamera);
		fl = (FrameLayout) findViewById(R.id.camerapreview);
		fl.addView(preview);

		buttonClick = (ImageButton) findViewById(R.id.click);

		// take picture
		buttonClick.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				preview.mCamera.takePicture(shutterCallback, rawCallback,
						jpegCallback);
			}
		});

	}

	public static Camera getCameraInstance() {

		Log.d("track", "Entered getCameraInstance");
		Camera c = null;

		try {
			c = Camera.open();
			Log.d("back camera", "opened back camera"); // attempt to get a
														// Camera instance
		} catch (Exception e) { // Camera is not available (in use or does not
								// exist)
		}
		return c; // returns null if camera is unavailable

	}

	ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
			Log.d(TAG, "onShutter'd");
		}
	};

	/** Handles data for raw picture */
	PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.d(TAG, "onPictureTaken - raw");
		}
	};

	/** Handles data for jpeg picture */
	PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(final byte[] data, Camera camera) {
			outStream = null;
			int length = data.length;

			setContentView(R.layout.camera_image);
			ImageView imageView = (ImageView) findViewById(R.id.image_view);
			bmp = BitmapFactory.decodeByteArray(data, 0, length);

			Matrix mat = new Matrix();
			mat.postRotate(90);
			bMapRotate = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
					bmp.getHeight(), mat, true);
			bmp = bMapRotate;

			// if image larger than 2048x2048
			if (bmp.getHeight() > 2048 || bmp.getWidth() > 2048) {

				if (bmp.getWidth() < 2048) {
					bitmapWidth = 2048;
				}
				if (bmp.getHeight() > 2048) {
					bitmapHeight = 2048;
				}

				bmp = Bitmap.createScaledBitmap(bmp, bitmapWidth, bitmapHeight,
						true);
				imageView.setImageBitmap(bmp);

			}

			imageView.setImageBitmap(bmp);

			ImageButton save = (ImageButton) findViewById(R.id.save);
			ImageButton discard = (ImageButton) findViewById(R.id.discard);

			save.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					saveCameraImage();

				}
			});

			discard.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent cameraPreview = new Intent(ClickFromCamera.this,
							ClickFromCamera.class);
					startActivity(cameraPreview);

				}
			});

		}
	};

	void saveCameraImage() {
		try {

			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			long lDateTime = new Date().getTime();

			final File pathToFolder = new File(
					Environment.getExternalStorageDirectory() + "/Camera");
			if (!pathToFolder.exists())
				pathToFolder.mkdir();

			try {

				outStream = new FileOutputStream(String.format(pathToFolder
						+ "/%s.jpg", "Vineet_" + lDateTime));
				path = pathToFolder + "/Vineet_" + lDateTime + ".jpg";
				System.out.println(dateFormat.parse(dateFormat
						.format(new Date())));
				System.out.println("path = " + path);
				bmp.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
				outStream.flush();
				outStream.close();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			final Intent intent = new Intent();
			intent.putExtra("path", path);
			setResult(Activity.RESULT_OK, intent);
			finish();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ExifInterface ei = null;
		try {
			ei = new ExifInterface(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
				ExifInterface.ORIENTATION_NORMAL);
		Log.d("ORIENTATION", "" + orientation);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			// start new Activity here
			Intent goBack = new Intent(this, MainActivity.class);
			startActivity(goBack);
		}
		return super.onKeyDown(keyCode, event);
	}

}
