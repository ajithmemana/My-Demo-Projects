package com.qburst.newsreader.api;

import java.util.HashMap;

import java.util.Map;

import android.util.Log;

import com.qburst.newsreader.interfaces.NRExceptionApiListener;


public class NRBaseApi {
	private Object _errorMsgObject;
	private Map<String, Object> _networkErrorResponse;
	private Map<String, Object> _networkTimeoutErrorResponse;
	private Map<String, Object> _internalServerErrorResponse;
	private Map<String, Object> _authenticationErrorResponse;
	NRExceptionApiListener _excptnListener;

	/**
	 * This function checks for specific network, internal server error  and network timeout related
	 * errors and handles it by redirecting it to related listeners.
	 * 
	 * @param ResponseObject
	 * @return void
	 * 
	 */
	
	public String baseUrl(){
		return NRApiConstants.baseUrl;
	}
	
	public void detectErrorMessage(Map<String, Object> response) {

		Log.i("ERROR FOUND", "ENTERED DETECT-ERROR-MESSAGE");

		if (response != null) {

			_networkErrorResponse = new HashMap<String, Object>();
			_networkTimeoutErrorResponse = new HashMap<String, Object>();
			_internalServerErrorResponse = new HashMap<String, Object>();
			_authenticationErrorResponse = new HashMap<String, Object>();

			_errorMsgObject = response.get(NRApiConstants.kErrorMsgKey);
			String networkErrorMessage = NRApiConstants.kNetworkErrorMessage;
			String networkTimeoutErrorMessage = NRApiConstants.kNetworkTimeoutErrorMessage;
			String internalServerErrorMessage = NRApiConstants.kInternalServerErrorMessage;
			String authenticationErrorMessage = NRApiConstants.kAuthenticationErrorMessage;

			
			_networkErrorResponse.put(NRApiConstants.kApiFailedMsgKey,
					networkErrorMessage);
			_networkTimeoutErrorResponse.put(NRApiConstants.kApiFailedMsgKey,
					networkTimeoutErrorMessage);
			_internalServerErrorResponse.put(NRApiConstants.kApiFailedMsgKey,
					internalServerErrorMessage);
			_authenticationErrorResponse.put(NRApiConstants.kApiFailedMsgKey,
					authenticationErrorMessage);

			if (_errorMsgObject
					.equals(NRApiConstants.kNetworkErrorExceptionKey)) {
				_excptnListener
						.onNetWorkUnavailableResponse(_networkErrorResponse);

			} else if (_errorMsgObject
					.equals(NRApiConstants.kTimeOutErrorExceptionKey)) {
				_excptnListener
						.onRequestTimedoutResponse(_networkTimeoutErrorResponse);

			}else if (_errorMsgObject
					.equals(NRApiConstants.kInvalidResponseError)) {
				_excptnListener.onInternalServerErrorResponse(_internalServerErrorResponse);

			}else if(_errorMsgObject
					.equals(NRApiConstants.kInternalServerErrorExceptionKey)){
				
				_excptnListener.onInternalServerErrorResponse(_internalServerErrorResponse);
			}else if(_errorMsgObject
					.equals(NRApiConstants.kAuthenticationErrorExceptionKey)){
				
				_excptnListener.onAuthenticationErrorResponse(_authenticationErrorResponse);
			} else if(_errorMsgObject.equals(NRApiConstants.kLargeContentErrorExceptionKey)){
				_internalServerErrorResponse = new HashMap<String, Object>();
				_internalServerErrorResponse.put(NRApiConstants.kApiFailedMsgKey,
						NRApiConstants.kLargeContentProcessingErrorMessage);
				_excptnListener.onInternalServerErrorResponse(_internalServerErrorResponse);
			}
		}
	}

	
}
