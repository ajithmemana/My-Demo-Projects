package com.qburst.newsreader.cache;

import com.qburst.newsreader.models.BEMenuNavigation;

/**
 * Cache manager class to handle data cache mechanism. Shared cache mechanism is
 * provided through static access for caching objects.
 */
public class NRDataCache {

	private static NRDataCache _cache;
	
	private BEMenuNavigation _navigations;
	
	/**
	 * Access global cache object to retrieve and store object caches.
	 * 
	 * @return Shared Cache Manager Object
	 */
	public static NRDataCache getSharedCache() {

		if (_cache == null) {
			_cache = new NRDataCache();

		}
		return _cache;
	}


	

	public void setMenuNavigations(BEMenuNavigation navigations) {
		this._navigations = navigations;
	}

	public BEMenuNavigation getMenuNavigations() {
		return this._navigations;
	}

	
}
