package com.qburst.newsreader.app;

import android.app.Application;

/**
 * Designed application class for global access of application context.
 */
public class NRApp extends Application {
	
	private static Application _globalInstance; 
	private Boolean isFirstUser = false;
	
	/**
	 * Static application context access method
	 * 
	 * @return Singleton Application Instance
	 */
	public static Application getSharedApplication() {
		return _globalInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		_globalInstance = this;
	}

	public Boolean getIsFirstUser() {
		return isFirstUser;
	}

	public void setIsFirstUser(Boolean isFirstUser) {
		this.isFirstUser = isFirstUser;
	}
	
	

}
