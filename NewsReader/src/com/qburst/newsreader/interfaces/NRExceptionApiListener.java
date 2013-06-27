package com.qburst.newsreader.interfaces;

import java.util.Map;


/**
 * @author user Designed BEExceptionApi interface methods
 *         onNetWorkUnavailableResponse, onRequestTimedoutResponse,
 *         onInternalServerErrorResponse and onAuthenticationErrorResponse.
 */

public interface NRExceptionApiListener{

	public void onNetWorkUnavailableResponse(Map<String, Object> errorResponse);

	public void onRequestTimedoutResponse(Map<String, Object> errorResponse);

	public void onInternalServerErrorResponse(Map<String, Object> errorResponse);

	public void onAuthenticationErrorResponse(Map<String, Object> errorResponse);

}
 