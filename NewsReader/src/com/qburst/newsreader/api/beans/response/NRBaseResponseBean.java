package com.qburst.newsreader.api.beans.response;

import java.io.InputStream;

import com.qburst.newsreader.api.beans.NRBaseBean;

public class NRBaseResponseBean extends NRBaseBean {
	private int responseCode;
	private NRResponseMessagesBean responseMessages;
	private String resultCode;
	private String resultString;
	private InputStream is;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public NRResponseMessagesBean getResponseMessages() {
		return responseMessages;
	}

	public void setResponseMessages(NRResponseMessagesBean responseMessages) {
		this.responseMessages = responseMessages;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}
}
