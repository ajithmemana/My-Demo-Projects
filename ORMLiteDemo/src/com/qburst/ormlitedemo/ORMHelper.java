package com.qburst.ormlitedemo;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class ORMHelper extends OrmLiteSqliteOpenHelper {
	private static final String DATABASE_NAME = "myORMSample.db";
	private static final int DATABASE_VERSION = 1;

	// create the required DAO objects
	private Dao<MyData, Integer> simpleDao = null;
	private RuntimeExceptionDao<MyData, Integer> simpleRuntimeDao = null;

	public ORMHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		// TODO Auto-generated method stub
		Log.d(getDatabaseName(), "in Oncreate of " + getClass().getSimpleName());
		try {
			TableUtils.createTable(connectionSource, MyData.class);
		} catch (SQLException e) {
			Log.d(getDatabaseName(), "can't create database", e);
			throw new RuntimeException(e);
		}

		// adding values to database at runtime
		RuntimeExceptionDao<MyData, Integer> dao = getMyDataDao();
		MyData myData = new MyData("Aravind", 23, 8089880337L);
		dao.create(myData);
		myData = new MyData("Vineet", 23, 8089880337L);
		dao.create(myData);
		myData = new MyData("Rakesh", 22, 9447715028L);
		dao.create(myData);
		Log.i(ORMHelper.class.getName(), "created new entries in onCreate: ");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.d(getClass().getSimpleName(), "inside onUpgrade");
		try {
			TableUtils.dropTable(connectionSource, MyData.class, true);
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.e(MyData.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}

	}

	public Dao<MyData, Integer> getDao() throws SQLException {
		if (simpleDao == null) {
			simpleDao = getDao(MyData.class);
		}
		return simpleDao;
	}

	public RuntimeExceptionDao<MyData, Integer> getMyDataDao() {
		if (simpleRuntimeDao == null) {
			simpleRuntimeDao = getRuntimeExceptionDao(MyData.class);
		}
		return simpleRuntimeDao;

	}

	@Override
	public void close() {
		super.close();
		simpleRuntimeDao = null;
	}

}
