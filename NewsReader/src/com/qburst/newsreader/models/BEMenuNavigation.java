package com.qburst.newsreader.models;

import java.util.ArrayList;
/**
 * @author user Model DAO for managing data with menu navigation.
 * 
 */
public class BEMenuNavigation {
	private ArrayList<BEMenuItem> BILLERS;
	private ArrayList<BEMenuItem> MYBILLS;
	private ArrayList<BEFolderMenuItem> FOLDERS;
	private ArrayList<BEMenuItem> settings;
	//private ArrayList<BEMenuItem> filters;
	
	public ArrayList<BEMenuItem> getBILLERS() {
		return BILLERS;
	}
	public void setBILLERS(ArrayList<BEMenuItem> billers) {
		BILLERS = billers;
	}
	public ArrayList<BEMenuItem> getMYBILLS() {
		return MYBILLS;
	}
	public void setMYBILLS(ArrayList<BEMenuItem> myBilld) {
		MYBILLS = myBilld;
	}
	public ArrayList<BEFolderMenuItem> getFOLDERS() {
		return FOLDERS;
	}
	
	public ArrayList<BEFolderMenuItem> getAllFOLDERS() {
		ArrayList<BEFolderMenuItem> allFolders = new ArrayList<BEFolderMenuItem>();
		
		ArrayList<BEFolderMenuItem> mainFolders = getFOLDERS();
		
		if(mainFolders!=null){
			int count = mainFolders.size();
			for(int i=0;i<count;i++){
				
				BEFolderMenuItem folder = mainFolders.get(i);
				allFolders.add(folder);
				
				if(folder.getSubFolders()!=null){
					
					ArrayList<BEFolderMenuItem> subFolders = folder.getSubFolders();

					
					int subCount = subFolders.size();
										
					for(int j=0;j<subCount;j++){
						BEFolderMenuItem subFolder = subFolders.get(j);
						allFolders.add(subFolder);

					}
				}
				
			}
			
		}
		
		
		return allFolders;
		
	}
	
	public void setFOLDERS(ArrayList<BEFolderMenuItem> folders) {
		FOLDERS = folders;
	}
	public ArrayList<BEMenuItem> getSettings() {
		// TODO Auto-generated method stub
		return settings;
	}
	
	public void setSettings(ArrayList<BEMenuItem> settings){
		this.settings = settings;
	}
//	public ArrayList<BEMenuItem> getFilters() {
//		return filters;
//	}
//	public void setFilters(ArrayList<BEMenuItem> filters) {
//		this.filters = filters;
//	}
	
}
