package com.qburst.ormlitedemo;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;

public class MyData {

	@DatabaseField(generatedId = true)
	int id;
	@DatabaseField
	// (id = true)
	String name;
	@DatabaseField
	int age;
	@DatabaseField
	long phNumber;

	MyData() {
		// empty constructor needed by Mydata
	}

	public MyData(String name, int age, long phNumber) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.age = age;
		this.phNumber = phNumber;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name=").append(name);
		sb.append(", ").append("age=").append(age);
		sb.append(", ").append("number=").append(phNumber);
		Log.d(getClass().getSimpleName() + " in StringBuilder", sb.toString());
		return sb.toString();
	}
}
