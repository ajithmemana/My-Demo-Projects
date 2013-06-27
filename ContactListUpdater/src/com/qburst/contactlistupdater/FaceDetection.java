package com.qburst.contactlistupdater;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

@SuppressLint("DrawAllocation")
public class FaceDetection extends Activity {

	public String path;

	private int imageWidth;
	private int imageHeight;
	private int counter = 0;
	private int numberOfFaceDetected;
	private int numberOfFace = 50;
	private int displayWidth;
	private int displayHeight;
	private FaceDetector myFaceDetect;
	private FaceDetector.Face[] myFace;
	float myEyesDistance = 0.0f;
	Bitmap myBitmap, scaledBitmap, resizedBitmap;
	String pathToCrop;
	String extStorageDirectory;
	File file;
	OutputStream outStream;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		pathToCrop = (String) b.get("path");

		Log.d("pathToCrop", pathToCrop);

		setContentView(new myView(this));

		// Broadcast the Media Scanner Intent to trigger it
		sendBroadcast(new Intent(
				Intent.ACTION_MEDIA_MOUNTED,
				Uri.parse("file://" + Environment.getExternalStorageDirectory())));
	}

	@SuppressLint("DrawAllocation")
	class myView extends View {

		@SuppressWarnings("deprecation")
		public myView(Context context) {
			super(context);
			// setDrawingCacheEnabled(true);

			BitmapFactory.Options BitmapFactoryOptionsbfo = new BitmapFactory.Options();
			BitmapFactoryOptionsbfo.inPreferredConfig = Bitmap.Config.RGB_565;
			myBitmap = BitmapFactory.decodeFile(pathToCrop,
					BitmapFactoryOptionsbfo);

			imageHeight = myBitmap.getHeight();
			imageWidth = myBitmap.getWidth();
			System.out.println("original " + imageWidth + "x" + imageHeight);

			ScrollView scrollView = new ScrollView(getContext());
			scrollView.setVerticalScrollBarEnabled(true);
			scrollView.setHorizontalScrollBarEnabled(true);
			scrollView.setWillNotDraw(false);

			Display currentDisplay = getWindowManager().getDefaultDisplay();
			displayWidth = currentDisplay.getWidth();
			displayHeight = currentDisplay.getHeight();

			myBitmap = Bitmap.createScaledBitmap(myBitmap, displayWidth,
					displayHeight, true);

			System.out.println("scaled " + myBitmap.getWidth() + "x"
					+ myBitmap.getHeight());

			myFace = new FaceDetector.Face[numberOfFace];
			myFaceDetect = new FaceDetector(displayWidth, displayHeight,
					numberOfFace);
			numberOfFaceDetected = myFaceDetect.findFaces(myBitmap, myFace);
			System.out.println("Number of faces = " + numberOfFaceDetected);
			Toast.makeText(FaceDetection.this,
					"Number of faces detected = " + numberOfFaceDetected,
					Toast.LENGTH_LONG).show();

			this.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent callPicGridClass = new Intent(FaceDetection.this,
							PicGridDisplay.class);
					startActivity(callPicGridClass);

				}
			});

		}

		@SuppressLint("DrawAllocation")
		@Override
		protected void onDraw(Canvas canvas) {
			System.out.println("ON DRAW IS CALLED");

			if (myBitmap != null) {

				canvas.drawBitmap(myBitmap, 0, 0, null);

				Paint myPaint = new Paint();
				myPaint.setColor(Color.GREEN);
				myPaint.setStyle(Paint.Style.STROKE);
				myPaint.setStrokeWidth(3);

				// numberOfFaceDetected
				for (int i = 0; i < numberOfFaceDetected; i++) {
					Face face = myFace[i];
					PointF myMidPoint = new PointF();
					face.getMidPoint(myMidPoint);
					myEyesDistance = face.eyesDistance();

					canvas.drawRect((int) (myMidPoint.x - myEyesDistance - 20),
							(int) (myMidPoint.y - myEyesDistance - 20),
							(int) (myMidPoint.x + myEyesDistance + 20),
							(int) (myMidPoint.y + myEyesDistance + 20), myPaint);

					System.out.println("Eyes Distance = " + myEyesDistance);
					System.out.println("myMidPoint.x =" + myMidPoint.x);
					System.out.println("myMidPoint.y =" + myMidPoint.y);

					int left = ((int) (myMidPoint.x - myEyesDistance - 15));
					if (left < 0)
						left = 0;

					int right = (int) (myMidPoint.y - myEyesDistance - 40);
					if (right < 0)
						right = 0;

					int width = (int) ((2 * myEyesDistance + 40));

					int height = (int) ((2 * myEyesDistance + 80));

					if ((left + width) > myBitmap.getWidth()) {
						System.out.println("Exceeded Width");
						width = (int) (width + (myBitmap.getWidth() - (left + width)));

					}

					resizedBitmap = Bitmap.createBitmap(myBitmap, left, right,
							width, height);

					// saving the cropped bitmap to sd card
					saveBitmapImage();

				}

				Toast.makeText(FaceDetection.this,
						"Touch Image to continue !!!", Toast.LENGTH_LONG)
						.show();
			}

		}

		void saveBitmapImage() {

			final File direct = new File(
					Environment.getExternalStorageDirectory() + "/faces");
			long lDateTime = new Date().getTime();

			if (!direct.exists())
				direct.mkdir();

			extStorageDirectory = Environment.getExternalStorageDirectory()
					.toString();

			outStream = null;
			file = new File(direct, "pic" + ++counter + lDateTime + ".JPEG");
			try {
				outStream = new FileOutputStream(file);
				resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
						outStream);
				outStream.flush();
				outStream.close();
			} catch (Exception e) {
			}
		}
	}

}
