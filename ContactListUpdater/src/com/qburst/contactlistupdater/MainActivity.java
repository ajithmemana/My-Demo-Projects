package com.qburst.contactlistupdater;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener {

	ImageView imageView;
	Bitmap yourSelectedImage;
	Bitmap testBitmap;
	String filePath;
	File direct;
	Uri selectedImage;

	final int ACTIVITY_SELECT_CAMERA_IMAGE = 1234;
	final int ACTIVITY_SELECT_GALLERY_IMAGE = 6789;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_display);

		ImageButton camera = (ImageButton) findViewById(R.id.button1);
		ImageButton gallery = (ImageButton) findViewById(R.id.button2);

		camera.setOnClickListener(this);
		gallery.setOnClickListener(this);

		saveTestFile();
		deleteSDCardContent();

	}

	private void deleteSDCardContent() {
		// TODO Auto-generated method stub
		// to delete full folder

		direct = Environment.getExternalStorageDirectory();
		File facesImages = new File(direct, "/faces");
		File cameraImages = new File(direct, "/Camera");

		//delete faces folder
		if (facesImages.exists()) {
			if (facesImages.isDirectory()) {
				String[] children = facesImages.list();
				for (int i = 0; i < children.length; i++) {
					new File(facesImages, children[i]).delete();
				}
				System.out.println("Faces folder deleted ");
			}
		}
		
		//delete camera folder
		if (cameraImages.exists()) {
			if (cameraImages.isDirectory()) {
				String[] children = cameraImages.list();
				for (int i = 0; i < children.length; i++) {
					new File(cameraImages, children[i]).delete();
				}
				System.out.println("Camera folder deleted ");
			}
		}
	}

	private void saveTestFile() {
		// TODO Auto-generated method stub
		testBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.many);

		final File direct = new File(Environment.getExternalStorageDirectory()
				+ "/faces");

		if (!direct.exists())
			direct.mkdir();
		String extStorageDirectory = Environment.getExternalStorageDirectory()
				.toString();
		OutputStream outStream = null;
		File file = new File(extStorageDirectory + "/test.JPEG");
		try {
			outStream = new FileOutputStream(file);
			testBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
			outStream.flush();
			outStream.close();
		} catch (Exception e) {
		}
		System.out.println("test file saved -" + file);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view.getId() == R.id.button1) {

			Intent intent = new Intent(this, ClickFromCamera.class);
			final int ACTIVITY_SELECT_CAMERA_IMAGE = 1234;
			startActivityForResult(intent, ACTIVITY_SELECT_CAMERA_IMAGE);

		}

		if (view.getId() == R.id.button2) {

			// to open gallery pictures
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(intent, ACTIVITY_SELECT_GALLERY_IMAGE);

		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {

		case ACTIVITY_SELECT_CAMERA_IMAGE:

			if (resultCode == RESULT_OK) {
				Log.d("MESSAGE", "Entered camera on finishActivity");

				filePath = data.getStringExtra("path");
				Intent startFaceDetection = new Intent(this,
						PassFaceToBeDetected.class);
				startFaceDetection.putExtra("pathToFile", filePath);
				startFaceDetection.putExtra("activityCode",
						ACTIVITY_SELECT_CAMERA_IMAGE);
				startActivity(startFaceDetection);

				break;

			}

		case ACTIVITY_SELECT_GALLERY_IMAGE:

			if (resultCode == RESULT_OK) {

				selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Log.d("MESSAGE", "Entered gallery on finishActivity");

				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				filePath = cursor.getString(columnIndex);
				cursor.close();

				Intent startFaceDetection = new Intent(this,
						PassFaceToBeDetected.class);
				startFaceDetection.putExtra("pathToFile", filePath);
				startFaceDetection.putExtra("activityCode",
						ACTIVITY_SELECT_GALLERY_IMAGE);
				startActivity(startFaceDetection);

				break;

			}

		}

	};

}
