package com.qburst.ormlitedemo;

import java.sql.SQLException;

import com.example.ormlitedemo.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddToDataBase extends OrmLiteBaseActivity<ORMHelper> implements
		OnClickListener {

	EditText editTextName;
	EditText editTextAge;
	EditText editTextNumber;
	int age = 0;
	long phNumber = 0;
	MyData myData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		editTextName = (EditText) findViewById(R.id.editText1);
		editTextAge = (EditText) findViewById(R.id.editText2);
		editTextNumber = (EditText) findViewById(R.id.editText3);

		Button add = (Button) findViewById(R.id.add);
		add.setOnClickListener(this);
		Button done = (Button) findViewById(R.id.done);
		done.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub		
		
		if (view.getId() == R.id.add) {
			addValues();
			Intent callParentActivity = new Intent(this, AddToDataBase.class);
			startActivity(callParentActivity);
		}

		if (view.getId() == R.id.done) {
			Intent goBack = new Intent(this, MainActivity.class);
			startActivity(goBack);
		}

	}

	void addValues() {

		String name = editTextName.getText().toString();
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

		if ((name.equals("")) || (age == 0) || (phNumber == 0)) {
			Toast.makeText(this, "Incomplete", Toast.LENGTH_SHORT).show();
			finish();
		} else {
			// add to database
			myData = new MyData(name, age, phNumber);
		}
		// get our Dao
		Dao<MyData, Integer> simpleDao = null;
		try {
			simpleDao = getHelper().getDao();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			simpleDao.create(myData);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
