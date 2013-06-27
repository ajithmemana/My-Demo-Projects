package com.qburst.newsreader.activities;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.qburst.newsreader.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsIntro extends Activity implements OnClickListener {

	protected ImageLoader imageLoader;
	Results rb;
	String query;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_intro);

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));

		Results rb = null;
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle != null)
			rb = (Results) bundle.getSerializable("value");
		Log.d("value", "" + rb);

		TextView tv1 = (TextView) findViewById(R.id.textView1);
		TextView tv2 = (TextView) findViewById(R.id.textView2);
		TextView tv3 = (TextView) findViewById(R.id.textView3);
		TextView tv4 = (TextView) findViewById(R.id.textView4);
		ImageView iv = (ImageView) findViewById(R.id.imageView1);

		View b = findViewById(R.id.button1);
		View fb = findViewById(R.id.fb);

		if (null != rb.getFields().getHeadLine()) {
			String header = rb.getFields().getHeadLine();
			tv1.setText(header);
		}

		String date = rb.getWebPublicationDate();
		for (int i = 0; i < date.length(); i++) {
			if (date.charAt(i) == 'T') {
				date = date.substring(0, i);
			}
		}
		tv2.setText("Date : " + date);

		String author = rb.getFields().getByLine();
		tv3.setText("Author : " + author);

		String publication = rb.getFields().getPublication();
		tv4.setText("Publication : " + publication);

		if (null != rb.getFields().getThumbnail()) {
			String thumb = rb.getFields().getThumbnail();
			imageLoader.displayImage(thumb, iv);
		} else {
			iv.setImageResource(R.drawable.image);
		}

		query = rb.getWebUrl();
		b.setOnClickListener(this);

		fb.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news_intro, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.button1) {
			Intent i = new Intent(this, NewsDetail.class);
			i.putExtra("query", query);
			startActivity(i);
		}

		if (v.getId() == R.id.fb) {
			Intent intent = new Intent();
			intent.putExtra("query", query);
			intent.setClass(this, FacebookActivity.class);
			startActivity(intent);

		}

	}

}
