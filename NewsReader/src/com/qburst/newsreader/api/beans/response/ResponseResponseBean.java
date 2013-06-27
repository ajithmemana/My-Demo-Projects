package com.qburst.newsreader.api.beans.response;

import java.util.ArrayList;

public class ResponseResponseBean extends NRBaseResponseBean  {
	
	/*"status":"ok",
	"userTier":"free",
	"total":882088,
	"startIndex":1,
	"pageSize":10,
	"currentPage":1,
	"pages":88209,
	"orderBy":"newest",
	"results":[]
*/			
 private String status;
 public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getUserTie() {
	return userTier;
}
public void setUserTie(String userTie) {
	this.userTier = userTie;
}
public String getTotal() {
	return total;
}
public void setTotal(String total) {
	this.total = total;
}
public int getStartIndex() {
	return startIndex;
}
public void setStartIndex(int startIndex) {
	this.startIndex = startIndex;
}
public int getPageSize() {
	return pageSize;
}
public void setPageSize(int pageSize) {
	this.pageSize = pageSize;
}
public int getCurrentPage() {
	return currentPage;
}
public void setCurrentPage(int currentPage) {
	this.currentPage = currentPage;
}
public String getPages() {
	return pages;
}
public void setPages(String pages) {
	this.pages = pages;
}
public String getOrderBy() {
	return orderBy;
}
public void setOrderBy(String orderBy) {
	this.orderBy = orderBy;
}
public ArrayList<ResultsResponseBean> getResults() {
	return results;
}
public void setResults(ArrayList<ResultsResponseBean> results) {
	this.results = results;
}

private String userTier;
 private String total;
 private int startIndex;
 private int pageSize;
 private int currentPage;
 private String pages;
 private String orderBy;
 private ArrayList<ResultsResponseBean> results;
 

 
 
}
