package com.qburst.contactlistupdater;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class TouchCrop extends Activity implements OnTouchListener,
		OnClickListener {

	ImageView imageView;
	String pathToCrop;
	File file;
	OutputStream outStream;
	String extStorageDirectory;
	float oldDist = 1f;
	float x1, y1, x2, y2;
	Bitmap bmp, bitmap;
	int i = 1;
	Canvas canvas, c;
	Paint myPaint, paint;
	Boolean drawRectangle = false;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		pathToCrop = (String) b.get("path");

		setContentView(R.layout.activity_touch_crop);
		imageView = (ImageView) findViewById(R.id.imageView1);
		
		Toast.makeText(this, "Drag to create a rectangle", Toast.LENGTH_LONG).show();

		bmp = BitmapFactory.decodeFile(pathToCrop);
		System.out
				.println("original " + bmp.getWidth() + "x" + bmp.getHeight());

		Display currentDisplay = getWindowManager().getDefaultDisplay();
		int dw = currentDisplay.getWidth();
		int dh = currentDisplay.getHeight();
		System.out.println("display size " + dw + "x" + dh);

		bitmap = Bitmap.createScaledBitmap(bmp, dw, dh, true);
		canvas = new Canvas(bitmap);

		paint = new Paint();

		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2);

		imageView.setImageBitmap(bitmap);

		imageView.setOnTouchListener(this);

		Button clear = (Button) findViewById(R.id.clear);
		Button done = (Button) findViewById(R.id.done);

		clear.setOnClickListener(this);
		done.setOnClickListener(this);

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			x1 = event.getX();
			y1 = event.getY();
			break;

		case MotionEvent.ACTION_MOVE:
			break;

		case MotionEvent.ACTION_UP:
			x2 = event.getX();
			y2 = event.getY();
			canvas.drawRect(x1, y1, x2, y2, paint);
			imageView.invalidate();
			System.out.println("x1 =" + x1 + "y1 =" + y1 + "x2 =" + x2 + "y2 ="
					+ y2);
			break;

		case MotionEvent.ACTION_CANCEL:
			break;
		default:
			break;
		}
		return true;

	}

	void clearCanvas() {

		Log.d("Entered", "Entered on clear canvas");

		canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
		canvas.drawBitmap(bitmap, 0, 0, null);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.d("Entered onClick", "Entered onClick");

		if (v.getId() == R.id.clear) {

			Intent callCurrentActivity = new Intent(this, TouchCrop.class);
			callCurrentActivity.putExtra("path", pathToCrop);
			startActivity(callCurrentActivity);

		}
		if (v.getId() == R.id.done) {
			saveDrawnImage();
			Intent callNextActivity = new Intent(this, PicGridDisplay.class);
			startActivity(callNextActivity);
		}
	}

	private void saveDrawnImage() {
		// TODO Auto-generated method stub
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, (int) x1, (int) y1,
				(int) (x2 - x1), (int) (y2 - y1));
		final File direct = new File(Environment.getExternalStorageDirectory()
				+ "/faces");
		long lDateTime = new Date().getTime();
		// direct.delete();

		if (!direct.exists())
			direct.mkdir();

		extStorageDirectory = Environment.getExternalStorageDirectory()
				.toString();

		outStream = null;
		file = new File(direct, "pic" + lDateTime + ".JPEG");
		try {
			outStream = new FileOutputStream(file);
			newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
			outStream.flush();
			outStream.close();
		} catch (Exception e) {
		}

	}
}
