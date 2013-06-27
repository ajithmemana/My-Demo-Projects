package com.qburst.newsreader.models;

/**
 * @author user Model DAO for managing data with menu items.
 * 
 */
public class BEMenuItem {
	private String filterCategory;
	private String filterName;
	private int unreadCount;
	
	public String getFilterCategory() {
		return filterCategory;
	}
	public void setFilterCategory(String filterCategory) {
		this.filterCategory = filterCategory;
	}
	public String getFilterName() {
		return filterName;
	}
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	public int getUnreadCount() {
		return unreadCount;
	}
	public void setUnreadCount(int unreadCount) {
		this.unreadCount = unreadCount;
	}	
}
