package com.qburst.ormlitedemo;

import java.sql.SQLException;
import java.util.List;

import com.example.ormlitedemo.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DataBaseViewer extends OrmLiteBaseActivity<ORMHelper> {
	int simpleC = 0;
	int listSize = 0;
	int counter = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_base_viewer);
		TextView tv = new TextView(this);		
		List<MyData> content = null;
		String viewData;
		ListView viewList = (ListView) findViewById(R.id.listView);

		Dao<MyData, Integer> viewDao = null;

		try {
			viewDao = getHelper().getDao();
			content = viewDao.queryForAll();
			listSize = content.size();
			Log.d(getClass().getSimpleName() + "list size", "" + listSize);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (listSize == 0) {
			setContentView(tv);
			Log.d("listSize", "is 0");
			tv.setText("DataBase is empty");
		}
		/*
		 * for (MyData simple : content) { sb.append(simple).append("\n");
		 * sb.append("------------------------------------------\n"); simpleC++;
		 * } tv.setText(sb);
		 */

		String[] contents = new String[listSize];

		for (MyData simple : content) {
			viewData = "name : " + simple.name + " | " + "age : " + simple.age
					+ " | " + "number : " + simple.phNumber;
			contents[counter++] = viewData;

		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, contents);

		viewList.setAdapter(adapter);
	}

}
