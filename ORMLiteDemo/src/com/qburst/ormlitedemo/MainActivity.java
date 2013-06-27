package com.qburst.ormlitedemo;

import java.util.List;

import com.example.ormlitedemo.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends OrmLiteBaseActivity<ORMHelper> implements
		android.view.View.OnClickListener {
	MyData myData;
	int flag = 0;
	int savedFlag = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		// if extending Activity use dis
		/*
		 * ORMHelper helper = new ORMHelper(this); RuntimeExceptionDao<MyData,
		 * Integer> simpleDao = helper .getMyDataDao();
		 */

		RuntimeExceptionDao<MyData, Integer> simpleDao = getHelper()
				.getMyDataDao();
		List<MyData> list = simpleDao.queryForAll();
		Log.d(getClass().getSimpleName(), "inside MainActivity");

		for (MyData myData : list) {
			// sb.append(myData.name);
			Log.d("sb", myData.name);
		}

		Button viewDatabase = (Button) findViewById(R.id.button1);
		Button addValue = (Button) findViewById(R.id.button2);
		Button deleteValue = (Button) findViewById(R.id.button3);
		Button searchDataBase = (Button) findViewById(R.id.button4);

		viewDatabase.setOnClickListener(this);
		addValue.setOnClickListener(this);
		deleteValue.setOnClickListener(this);
		searchDataBase.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			Intent viewDatabase = new Intent(this, DataBaseViewer.class);
			viewDatabase.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(viewDatabase);
			break;
		case R.id.button2:
			Intent searchDataBase = new Intent(this, SearchDatabase.class);
			startActivity(searchDataBase);
			break;
		case R.id.button3:
			Intent addValue = new Intent(this, AddToDataBase.class);
			startActivity(addValue);
			break;
		case R.id.button4:
			Intent deleteValue = new Intent(this, DeleteFromDatabase.class);
			startActivity(deleteValue);
			break;
		}
	}
}
