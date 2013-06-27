package com.qburst.newsreader.activities;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;


import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import com.qburst.newsreader.R;
import com.qburst.newsreader.api.GetNewsApi;
import com.qburst.newsreader.api.beans.response.News_ParseResponseBean;
import com.qburst.newsreader.interfaces.NRExceptionApiListener;
import com.qburst.newsreader.interfaces.NewsListener;
import com.qburst.newsreader.utilities.NRUtility;

import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

public class MainNewsFeed extends Activity implements OnItemClickListener, OnItemSelectedListener, NewsListener, NRExceptionApiListener {

	
    InputStream is = null;
    String result = "";
    ArrayList<Results> locationArray = null;
    ImageAdapter locationAdapter;
    NewsParse list;
    protected ImageLoader imageLoader;
    GridView gv;
    ProgressDialog _progressDialogue;
    ProgressDialog dialog;
    private PullToRefreshGridView mPullRefreshGridView;
       
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
				
	    	GetNewsApi gnapi = new GetNewsApi(this, this);
		    gnapi.getMenuDetails("");
					 
			mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.pull_refresh_grid);
			gv = mPullRefreshGridView.getAdapterView();
		
		    imageLoader = ImageLoader.getInstance();
		    imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		    
		    locationArray = new ArrayList<Results>();
	        locationAdapter = new ImageAdapter (MainNewsFeed.this, R.layout.grid_content, locationArray);
	 
	     // Set a listener to be invoked when the list should be refreshed.
			mPullRefreshGridView.setOnRefreshListener(new OnRefreshListener() {
				@Override
				public void onRefresh() {
					// Do work to refresh the list here.
										
					GetNewsApi gnapi1 = new GetNewsApi(MainNewsFeed.this, MainNewsFeed.this);
					gnapi1.getMenuDetails("");
					}
			});
		
	        
	        gv.setTextFilterEnabled(true);
	        gv.setAdapter(locationAdapter);
	        gv.setOnItemClickListener(this);
	        gv.setOnItemSelectedListener(this);
	        locationAdapter.notifyDataSetChanged();
	      	        
	    }
		
	@Override
	public void onItemSelected(AdapterView<?> parent,  View v, int position, long id) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void onItemClick(AdapterView<?> arg0,  View v, int position, long id) {
		// TODO Auto-generated method stub
		Toast.makeText(MainNewsFeed.this, "News item " + (position+1), Toast.LENGTH_SHORT).show();
		 Results link = (Results) arg0.getItemAtPosition(position);
		
	       Bundle bundle = new Bundle();  
	       bundle.putSerializable("value", link);
	       Intent intent=new Intent();
	       intent.putExtras(bundle);
	       intent.setClass(this,NewsIntro.class);
	       	   
	       startActivity(intent);
	}

	@Override
	public void requestDidStart() {
	if (_progressDialogue != null) {
	if (_progressDialogue.isShowing()) {
	_progressDialogue.dismiss();
	}
	}
	_progressDialogue = NRUtility.showProgressDialog(this);

	}

	@Override
	public void requestDidFinish() {

	if (_progressDialogue != null) {
	if (_progressDialogue.isShowing()) {
	_progressDialogue.dismiss();
	}
	}

	}
	@Override
	public void onNetWorkUnavailableResponse(Map<String, Object> errorResponse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRequestTimedoutResponse(Map<String, Object> errorResponse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInternalServerErrorResponse(Map<String, Object> errorResponse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAuthenticationErrorResponse(Map<String, Object> errorResponse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receivednewsapi(News_ParseResponseBean respBean) {
				// TODO Auto-generated method stub
		locationArray = respBean.getResponse().getResults();
		
		if(respBean != null ){
			mPullRefreshGridView.onRefreshComplete();
			//
					 		 
			if(null!= locationAdapter){
				locationAdapter.setObjects(locationArray);
				 locationAdapter.notifyDataSetChanged();
			}
			 
			Log.d("la",""+locationArray.size());
	    }
		
		
	}

	@Override
	public void errorInResponse() {
		// TODO Auto-generated method stub
		
	}
	
}
