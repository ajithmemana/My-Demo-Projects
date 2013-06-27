package com.qburst.newsreader.models;

import java.util.ArrayList;

/**
 * @author user Model DAO for managing data with folders menu item.
 * 
 */
public class BEFolderMenuItem extends BEMenuItem{
	private int folderId;
	private String folderName;
	private ArrayList<BEFolderMenuItem> subFolders;
	private int subFolderId;
	private int parentFolderId;
	private String subFolderName;
	public int getFolderId() {
		return folderId;
	}
	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public ArrayList<BEFolderMenuItem> getSubFolders() {
		return subFolders;
	}
	public void setSubFolders(ArrayList<BEFolderMenuItem> subFolders) {
		this.subFolders = subFolders;
	}
	public int getSubFolderId() {
		return subFolderId;
	}
	public void setSubFolderId(int subFolderId) {
		this.subFolderId = subFolderId;
	}
	public int getParentFolderId() {
		return parentFolderId;
	}
	public void setParentFolderId(int parentFolderId) {
		this.parentFolderId = parentFolderId;
	}
	public String getSubFolderName() {
		return subFolderName;
	}
	public void setSubFolderName(String subFolderName) {
		this.subFolderName = subFolderName;
	}
	
	
}
