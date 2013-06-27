package com.qburst.newsreader.api;

import java.util.Map;
import android.util.Log;
import com.qburst.newsreader.api.beans.response.News_ParseResponseBean;
import com.qburst.newsreader.api.beans.request.News_ParseRequestBean;
import com.qburst.newsreader.interfaces.NRApiListener;
import com.qburst.newsreader.interfaces.NRExceptionApiListener;
import com.qburst.newsreader.interfaces.NewsListener;

public class GetNewsApi extends NRBaseApi implements NRApiListener {

	private NewsListener _listener;

	public GetNewsApi(NewsListener listener,
			NRExceptionApiListener exceptionListener) {
		this._listener = listener;
		this._excptnListener = exceptionListener;
	}

	public void getMenuDetails(String sessionId) {

		News_ParseRequestBean requestBean = new News_ParseRequestBean();
		if (_listener != null) {
			_listener.requestDidStart();
		}
		NRBaseWebService service = new NRBaseWebService(this,
				requestBean.toJsonString(), News_ParseResponseBean.class,
				baseUrl());
		service.setAuthenticationParams("qburst", "qburst");
		service.execute(baseUrl());
	}

	@Override
	public String baseUrl() {

		return super.baseUrl();
	}

	@Override
	public void onResponseReceived(Map<String, Object> response) {

		Log.i("RESPONSE", response.toString());
		if (_listener != null) {
			_listener.requestDidFinish();
		}
		if (response.containsKey(NRApiConstants.kSuccessMsgKey)) {

			News_ParseResponseBean respBean = (News_ParseResponseBean) response
					.get(NRApiConstants.kSuccessMsgKey);

			if (respBean != null) {
				Log.v("respbean", "" + respBean);
				_listener.receivednewsapi(respBean);
			}
		} else if (response.containsKey(NRApiConstants.kApiFailedMsgKey)) {

			if (response.containsKey("HttpStatus")) {

				if (_listener != null) {
					_listener.errorInResponse();
				}
			}
		}

	}

	@Override
	public void onFailedToGetResponse(Map<String, Object> errorResponse) {
		// TODO Auto-generated method stub

	}

}
