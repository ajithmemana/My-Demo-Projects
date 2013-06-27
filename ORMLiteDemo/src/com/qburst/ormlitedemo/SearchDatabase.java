package com.qburst.ormlitedemo;

import java.sql.SQLException;
import java.util.List;

import com.example.ormlitedemo.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SearchDatabase extends OrmLiteBaseActivity<ORMHelper> implements
		OnClickListener {
	EditText editTextName;
	EditText editTextAge;
	EditText editTextNumber;
	TextView tv;
	StringBuilder sb;
	String displayString;
	String name;
	int flag = 0;
	int age = 0;
	long phNumber = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_database);

		editTextName = (EditText) findViewById(R.id.editText1);
		editTextAge = (EditText) findViewById(R.id.editText2);
		editTextNumber = (EditText) findViewById(R.id.editText3);

		Button search = (Button) findViewById(R.id.search);
		search.setOnClickListener(this);

		sb = new StringBuilder();
		tv = new TextView(this);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		name = editTextName.getText().toString();
		Log.d("name", name);

		String ageString = editTextAge.getText().toString();
		Log.d("age", ageString);
		if (ageString.equals("") == false) {
			Log.d("AgeString", "ageString = " + ageString);
			age = Integer.parseInt(ageString);
		}

		String phNumberString = editTextNumber.getText().toString();
		if (phNumberString.equals("") == false) {
			Log.d("phNumberString", "phNumberString = " + phNumberString);
			phNumber = Long.parseLong(phNumberString);
		}
		Log.d("number", phNumberString);

		List<MyData> list = null;
		try {
			Dao<MyData, Integer> simpleDao = getHelper().getDao();
			list = simpleDao.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// if only name entered
		if ((phNumber == 0) && (age == 0)) {
			for (MyData myData : list) {
				if (name.equals(myData.name)) {
					flag = 1;
					Log.d(getClass().getSimpleName(), "Value found");
					sb.append(myData).append("\n");
					sb.append("------------------------------------------\n");
					setContentView(tv);
					tv.setText(sb.toString());

				}
			}
		}

		// if only name and age entered
		if ((phNumber == 0)) {
			for (MyData myData : list) {
				if (name.equals(myData.name) && (age == myData.age)) {
					flag = 1;
					Log.d(getClass().getSimpleName(), "Value found");
					sb.append(myData).append("\n");
					sb.append("------------------------------------------\n");
					setContentView(tv);
					tv.setText(sb.toString());
				}
			}
		}

		// if only age entered
		if ((phNumber == 0) && (name.equals(""))) {
			for (MyData myData : list) {
				if ((age == myData.age)) {
					flag = 1;
					Log.d(getClass().getSimpleName(), "Value found");
					sb.append(myData).append("\n");
					sb.append("------------------------------------------\n");
					setContentView(tv);
					tv.setText(sb.toString());
				}
			}
		}

		// if only number entered
		if ((age == 0) && (name.equals(""))) {
			for (MyData myData : list) {
				if ((phNumber == myData.phNumber)) {
					flag = 1;
					Log.d(getClass().getSimpleName(), "Value found");
					sb.append(myData).append("\n");
					sb.append("------------------------------------------\n");
					setContentView(tv);
					tv.setText(sb.toString());
				}
			}
		}

		// if only number and age entered
		if (name.equals("")) {
			for (MyData myData : list) {
				if ((phNumber == myData.phNumber) && (age == myData.age)) {
					flag = 1;
					Log.d(getClass().getSimpleName(), "Value found");
					sb.append(myData).append("\n");
					sb.append("------------------------------------------\n");
					setContentView(tv);
					tv.setText(sb.toString());
				}
			}
		}

		if (flag == 0) {
			setContentView(tv);
			tv.setText("No data found in database");
		}
	}
}
