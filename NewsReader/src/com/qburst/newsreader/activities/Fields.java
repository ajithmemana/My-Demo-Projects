package com.qburst.newsreader.activities;

import java.io.Serializable;

public class Fields implements Serializable {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String trailText;
	public String getTrailText() {
		return trailText;
	}
	public void setTrailText(String trailText) {
		this.trailText = trailText;
	}
	public String getHeadLine() {
		return headline;
	}
	public void setHeadLine(String headLine) {
		this.headline = headLine;
	}
	public String getShowInrelatedContent() {
		return showInRelatedContent;
	}
	public void setShowInrelatedContent(String showInrelatedContent) {
		this.showInRelatedContent = showInrelatedContent;
	}
	public String getLastModified() {
		return lastModified;
	}
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}
	public String getHasStoryPackage() {
		return hasStoryPackage;
	}
	public void setHasStoryPackage(String hasStoryPackage) {
		this.hasStoryPackage = hasStoryPackage;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getStandFirst() {
		return standfirst;
	}
	public void setStandFirst(String standFirst) {
		this.standfirst = standFirst;
	}
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getWordcount() {
		return wordcount;
	}
	public void setWordcount(String wordcount) {
		this.wordcount = wordcount;
	}
	public String getCommentable() {
		return commentable;
	}
	public void setCommentable(String commentable) {
		this.commentable = commentable;
	}
	public String getAllowUgc() {
		return allowUgc;
	}
	public void setAllowUgc(String allowUgc) {
		this.allowUgc = allowUgc;
	}
	public String getIsPremoderated() {
		return isPremoderated;
	}
	public void setIsPremoderated(String isPremoderated) {
		this.isPremoderated = isPremoderated;
	}
	public String getByLine() {
		return byline;
	}
	public void setByLine(String byLine) {
		this.byline = byLine;
	}
	public String getPublication() {
		return publication;
	}
	public void setPublication(String publication) {
		this.publication = publication;
	}
	public String getShouldHideAdverts() {
		return shouldHideAdverts;
	}
	public void setShouldHideAdverts(String shouldHideAdverts) {
		this.shouldHideAdverts = shouldHideAdverts;
	}
	public String getLiveBloggingNow() {
		return liveBloggingNow;
	}
	public void setLiveBloggingNow(String liveBloggingNow) {
		this.liveBloggingNow = liveBloggingNow;
	}
	public String getCommentClosedate() {
		return commentCloseDate;
	}
	public void setCommentClosedate(String commentClosedate) {
		this.commentCloseDate = commentClosedate;
	}
	private String headline;
	private String showInRelatedContent;
	private String lastModified;
	private String hasStoryPackage;
	private String score;
	private String standfirst;
	private String shortUrl;
	private String thumbnail;
	private String wordcount;
	private String commentable;
	private String allowUgc;
	private String isPremoderated;
	private String byline;
	private String publication;
	private String shouldHideAdverts;
	private String liveBloggingNow;
	private String commentCloseDate;
	
	
	

}
