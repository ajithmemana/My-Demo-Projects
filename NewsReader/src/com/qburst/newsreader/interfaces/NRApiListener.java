package com.qburst.newsreader.interfaces;

import java.util.Map;

/**
 * @author user
 * 
 * Designed  methods onResponseReceived and onFailedToGetResponse.
 *
 */
public interface NRApiListener {
	
	public void onResponseReceived(Map<String, Object> response);
    public void onFailedToGetResponse(Map<String, Object> errorResponse);
   
    
}
