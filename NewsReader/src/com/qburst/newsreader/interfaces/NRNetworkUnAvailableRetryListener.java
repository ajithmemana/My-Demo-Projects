package com.qburst.newsreader.interfaces;

/**
 * @author user Designed BENetworkUnAvailableRetryListener interface methods
 *         retry and setEditButtonEnableStatus.
 */

public interface NRNetworkUnAvailableRetryListener extends NRApiListener {
	public void retry(String baseUrl);
	public void setEditButtonEnableStatus(boolean status);
}
