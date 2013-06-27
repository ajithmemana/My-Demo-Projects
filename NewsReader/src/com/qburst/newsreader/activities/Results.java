package com.qburst.newsreader.activities;

import java.io.Serializable;
import java.util.ArrayList;

public class Results implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String sectionId;

	private String sectionName;

	private String webPublicationDate;

	private String webTitle;

	private String webUrl;

	private String apiUrl;

	private Fields fields;

	private ArrayList<Factboxes> factboxes;

	public String getApiUrl() {
		return apiUrl;
	}

	public ArrayList<Factboxes> getFactboxes() {
		return factboxes;
	}

	public Fields getFields() {
		return fields;
	}

	public String getId() {
		return id;
	}

	public String getSectionId() {
		return sectionId;
	}

	public String getSectionName() {
		return sectionName;
	}

	public String getWebPublicationDate() {
		return webPublicationDate;
	}

	public String getWebTitle() {
		return webTitle;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public void setFactboxes(ArrayList<Factboxes> factboxes) {
		this.factboxes = factboxes;
	}
	public void setFields(Fields fields) {
		this.fields = fields;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public void setWebPublicationDate(String webPublicationDate) {
		this.webPublicationDate = webPublicationDate;
	}
	public void setWebTitle(String webTitle) {
		this.webTitle = webTitle;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

}
