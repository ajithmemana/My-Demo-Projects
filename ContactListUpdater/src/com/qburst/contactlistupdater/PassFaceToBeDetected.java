package com.qburst.contactlistupdater;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class PassFaceToBeDetected extends Activity implements OnClickListener {

	String pathToFile;
	Bitmap yourSelectedImage, newScaledImage;
	ImageView imageView;
	Button detectFace;
	Button cropManually;
	int activityCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detect_face);

		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		pathToFile = (String) b.get("pathToFile");
		activityCode = (Integer) b.get("activityCode");

		Log.d("pathToFile", pathToFile);
		Log.d("activityCode", "" + activityCode);

		imageView = (ImageView) findViewById(R.id.image_view);

		BitmapFactory.Options BitmapFactoryOptionsbfo = new BitmapFactory.Options();
		BitmapFactoryOptionsbfo.inPreferredConfig = Bitmap.Config.RGB_565;

		yourSelectedImage = BitmapFactory.decodeFile(pathToFile,
				BitmapFactoryOptionsbfo);

		// checking whether dimesions are perfect to be viewed in imageview
		if (yourSelectedImage.getHeight() <= 2048
				&& yourSelectedImage.getWidth() <= 2048) {
			imageView.setImageBitmap(yourSelectedImage);
		} else {
			yourSelectedImage = Bitmap.createScaledBitmap(yourSelectedImage,
					2048, 2048, true);
			imageView.setImageBitmap(yourSelectedImage);

		}

		Toast.makeText(this, "Select mode of face detection", Toast.LENGTH_SHORT).show();
		
		detectFace = (Button) findViewById(R.id.detect_face);
		detectFace.setOnClickListener(this);

		cropManually = (Button) findViewById(R.id.crop_manually);
		cropManually.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.detect_face) {
			Intent intent = new Intent(this, FaceDetection.class);
			intent.putExtra("path", pathToFile);
			startActivity(intent);
		}

		if (v.getId() == R.id.crop_manually) {
			Intent intent = new Intent(this, TouchCrop.class);
			intent.putExtra("path", pathToFile);
			startActivity(intent);
		}

	}

}
