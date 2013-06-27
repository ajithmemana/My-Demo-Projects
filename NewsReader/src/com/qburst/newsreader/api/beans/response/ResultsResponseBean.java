package com.qburst.newsreader.api.beans.response;

import java.util.ArrayList;

public class ResultsResponseBean extends NRBaseResponseBean {
			
	// private static final long serialVersionUID = 1L;
	
	             private String id;
	             public String getId() {
					return id;
				}
				public void setId(String id) {
					this.id = id;
				}
				public String getSectionId() {
					return sectionId;
				}
				public void setSectionId(String sectionId) {
					this.sectionId = sectionId;
				}
				public String getSectionName() {
					return sectionName;
				}
				public void setSectionName(String sectionName) {
					this.sectionName = sectionName;
				}
				public String getWebPublicationDate() {
					return webPublicationDate;
				}
				public void setWebPublicationDate(String webPublicationDate) {
					this.webPublicationDate = webPublicationDate;
				}
				public String getWebTitle() {
					return webTitle;
				}
				public void setWebTitle(String webTitle) {
					this.webTitle = webTitle;
				}
				public String getWebUrl() {
					return webUrl;
				}
				public void setWebUrl(String webUrl) {
					this.webUrl = webUrl;
				}
				public String getApiUrl() {
					return apiUrl;
				}
				public void setApiUrl(String apiUrl) {
					this.apiUrl = apiUrl;
				}
				public FieldsResponseBean getFields() {
					return fields;
				}
				public void setFields(FieldsResponseBean fields) {
					this.fields = fields;
				}
				public ArrayList<FactboxesResponseBean> getFactboxes() {
					return factboxes;
				}
				public void setFactboxes(ArrayList<FactboxesResponseBean> factboxes) {
					this.factboxes = factboxes;
				}
				private String sectionId;
	             private String sectionName;
	             private String webPublicationDate;
	             private String webTitle;
	             private String webUrl;
	             private String apiUrl;
	             private FieldsResponseBean fields;
	             private ArrayList<FactboxesResponseBean> factboxes;
	             
	             

}
