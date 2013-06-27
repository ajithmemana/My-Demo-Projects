package com.qburst.newsreader.api.beans.response;

public class NRErrorMessageResponseBean {
	private String code;
	private String content;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContent() {
		return content.toString();
	}

	public void setContent(String content) {
		this.content = content;
	}

}
