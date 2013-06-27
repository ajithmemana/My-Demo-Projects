package com.qburst.newsreader.activities;

import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.qburst.newsreader.app.NRConstants;
import com.qburst.newsreader.interfaces.NRBaseAPIListener;
import com.qburst.newsreader.interfaces.NRExceptionApiListener;
import com.qburst.newsreader.interfaces.NRMenuApiListener;
import com.qburst.newsreader.models.BEMenuNavigation;
import com.qburst.newsreader.utilities.NRUtility;
//import com.qburst.newsreader.api.BEMenuApi;

/**
 * @author user Base Activity Class to handle base events and to enable base
 *         error and content loading logic.
 */

public class NRBaseActivity extends Activity implements NRBaseAPIListener,
NRExceptionApiListener, NRMenuApiListener {

	ProgressDialog _progressDialogue;

	@Override
	public void didFailToFetchMenu() {
		// TODO Auto-generated method stub

	}

	@Override
	public void didFetchMenuNavigations(BEMenuNavigation navigations) {
		// TODO Auto-generated method stub

	}

	public int getMenuId() {
		// Subclass should Override this method to provide menu id
		return 0;
	}

	public void groupOnBackPressed() {

	}

	@Override
	public void onAuthenticationErrorResponse(Map<String, Object> errorResponse) {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		NRConstants.SCREEN_HIGHT = dm.heightPixels;
		NRConstants.SCREEN_WIDTH = dm.widthPixels;
		super.onCreate(savedInstanceState);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		int menuId = getMenuId();
		if (menuId != 0) {

			MenuInflater inflater = getMenuInflater();
			inflater.inflate(menuId, menu);

		}
		return menuId != 0;

	}

	@Override
	public void onInternalServerErrorResponse(Map<String, Object> errorResponse) {

	}

	@Override
	public void onNetWorkUnavailableResponse(Map<String, Object> errorResponse) {
		Log.i("Activity", "no network");

	}

	@Override
	public void onRequestTimedoutResponse(Map<String, Object> errorResponse) {

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
	public void requestDidStart() {
		if (_progressDialogue != null) {
			if (_progressDialogue.isShowing()) {
				_progressDialogue.dismiss();
			}
		}
		_progressDialogue = NRUtility.showProgressDialog(this);

	}

	public void settingsClicked(View v) {

	}

	public void showLogoutDialogue() {

	}

}
