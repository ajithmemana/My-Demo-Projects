package com.qburst.newsreader.activities;

import java.io.Serializable;

public class NewsData implements Serializable {

	private static final long serialVersionUID = 1L;
	private int Id;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getShortUrl() {
		return ShortUrl;
	}

	public void setShortUrl(String shortUrl) {
		ShortUrl = shortUrl;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		this.Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}

	public String getFeedName() {
		return FeedName;
	}

	public void setFeedName(String feedName) {
		FeedName = feedName;
	}

	public String getFeedHashTag() {
		return FeedHashTag;
	}

	public void setFeedHashTag(String feedHashTag) {
		FeedHashTag = feedHashTag;
	}

	public String getPublishDate() {
		return PublishDate;
	}

	public void setPublishDate(String publishDate) {
		PublishDate = publishDate;
	}

	public String getIcon() {
		return Icon;
	}

	public void setIcon(String icon) {
		Icon = icon;
	}

	public String getPage() {
		return Page;
	}

	public void setPage(String page) {
		Page = page;
	}

	public String getPageName() {
		return PageName;
	}

	public void setPageName(String pageName) {
		PageName = pageName;
	}

	private String Url;
	private String ShortUrl;
	private String Title;
	private String Description;
	private String Category;
	private String CategoryName;
	private String FeedName;
	private String FeedHashTag;
	private String PublishDate;
	private String Icon;
	private String Page;
	private String PageName;

}
