package com.qburst.newsreader.interfaces;

import com.qburst.newsreader.models.BEMenuNavigation;

/**
 * @author user Designed BEMenuApi interface methods
 *         didFetchMenuNavigations and didFailToFetchMenu.
 */
public interface NRMenuApiListener extends NRBaseAPIListener{
	public void didFetchMenuNavigations(BEMenuNavigation navigations);
	public void didFailToFetchMenu();
}
