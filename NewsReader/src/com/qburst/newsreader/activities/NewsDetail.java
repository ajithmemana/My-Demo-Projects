package com.qburst.newsreader.activities;



import java.io.File;
import java.io.InputStream;
import com.qburst.newsreader.R;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class NewsDetail extends Activity {
	
	WebView webView;
	String link;
    final File direct = new File(Environment.getExternalStorageDirectory() + "/saved_pages");
    InputStream inputStream ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_detail);
		
		    link = getIntent().getStringExtra("query");
	        Log.d("link", link);
	     	        
	        getWindow().setFeatureInt( Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);

	        final File direct = new File(Environment.getExternalStorageDirectory() + "/saved_pages");

	        if(!direct.exists())
	        direct.mkdir();         //directory is created;
	        
	    	webView=new WebView(this);
		  	setContentView(webView);
	
	    	webView.setInitialScale(50);
	    	
	    	webView.setWebViewClient(new WebViewClient());
	    	final Activity activity = this;	  
	    	webView.setWebChromeClient(new WebChromeClient(){

	            @Override
				public void onProgressChanged(WebView view, int progress) {
	            	activity.setTitle(Long.toString(progress) + "% Loaded.....");
	        		activity.setProgress(progress * 100);
	                       if(progress == 100)
	                          activity.setTitle("News");
	                    }	                       
	    	});
  	   		    
	    	webView.loadUrl(link); 
	   // 	new DownloadWebpage().execute(link);
	    	
	    	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news_detail, menu);
		return true;
	}

}
