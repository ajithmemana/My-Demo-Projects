package com.qburst.contactlistupdater;

import java.io.File;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class PicGridDisplay extends Activity implements OnClickListener,
		OnItemClickListener {

	public class ImageAdapter extends BaseAdapter {

		private Context mContext;
		ArrayList<String> itemList = new ArrayList<String>();

		public ImageAdapter(Context c) {
			mContext = c;
		}

		void add(String path) {
			itemList.add(path);
		}

		@Override
		public int getCount() {
			return itemList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) { // if it's not recycled, initialize some
										// attributes
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(220, 220));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(8, 8, 8, 8);
			} else {
				imageView = (ImageView) convertView;
			}

			Bitmap bm = decodeSampledBitmapFromUri(itemList.get(position), 220,
					220);

			imageView.setImageBitmap(bm);
			return imageView;
		}

		public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth,
				int reqHeight) {

			Bitmap bm = null;
			// First decode with inJustDecodeBounds=true to check dimensions
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, options);

			// Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqHeight);

			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			bm = BitmapFactory.decodeFile(path, options);

			return bm;
		}

		public int calculateInSampleSize(

		BitmapFactory.Options options, int reqWidth, int reqHeight) {
			// Raw height and width of image
			final int height = options.outHeight;
			final int width = options.outWidth;
			int inSampleSize = 1;

			if (height > reqHeight || width > reqWidth) {
				if (width > height) {
					inSampleSize = Math.round((float) height
							/ (float) reqHeight);
				} else {
					inSampleSize = Math.round((float) width / (float) reqWidth);
				}
			}

			return inSampleSize;
		}

	}

	ImageAdapter myImageAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pic_grid_display);

		GridView gridView = (GridView) findViewById(R.id.gridView);
		myImageAdapter = new ImageAdapter(this);
		gridView.setAdapter(myImageAdapter);

		gridView.setOnItemClickListener(this);

		String ExternalStorageDirectoryPath = Environment
				.getExternalStorageDirectory().getAbsolutePath();

		String targetPath = ExternalStorageDirectoryPath + "/faces";

		Toast.makeText(getApplicationContext(), targetPath, Toast.LENGTH_LONG)
				.show();
		File targetDirector = new File(targetPath);

		File[] files = targetDirector.listFiles();
		for (File file : files) {
			myImageAdapter.add(file.getAbsolutePath());

		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		// TODO Auto-generated method stub
		Intent processGridViewImage = new Intent(this, AddToContact.class);
		processGridViewImage.putExtra("position", position);
		startActivity(processGridViewImage);

	}

}
