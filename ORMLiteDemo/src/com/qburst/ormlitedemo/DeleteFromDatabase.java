package com.qburst.ormlitedemo;

import java.sql.SQLException;
import java.util.List;

import com.example.ormlitedemo.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DeleteFromDatabase extends OrmLiteBaseActivity<ORMHelper>
		implements OnItemClickListener {

	List<MyData> content = null;
	Dao<MyData, Integer> viewDao = null;
	int counter = 0;
	int listSize = 0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_from_database);
		TextView tv = new TextView(this);	
		ListView lv = (ListView) findViewById(R.id.listView);
		String data;

		try {
			viewDao = getHelper().getDao();
			content = viewDao.queryForAll();
			listSize = content.size();
			Log.d("list size", "" + listSize);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if(listSize == 0) {
			setContentView(tv);
			Log.d("listSize", "is 0");
			tv.setText("DataBase is empty");
		//	finish();
		}
		
		String[] contents = new String[listSize];

		for (MyData simple : content) {
			data = "name : " + simple.name + " | " + "age : " + simple.age
					+ " | " + "number : " + simple.phNumber;
			contents[counter++] = data;

		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, contents);

		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, final int pos,
			long id) {
		// TODO Auto-generated method stub
		Log.d("Clicked position", "" + pos);	

		// display dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Delete record ??");
		// Add the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// User clicked OK button
				int check = 0;
				for (MyData simple : content) {
					if (check++ == pos) {
						try {
							viewDao.delete(simple);
							Log.d("Deleted value", simple.name);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
				Intent callAgain = new Intent(DeleteFromDatabase.this,
						DeleteFromDatabase.class);
				startActivity(callAgain);
			}
		});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// User cancelled the dialog
					}
				});

		// Create the AlertDialog
		AlertDialog dialog = builder.create();
		dialog.show();
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
