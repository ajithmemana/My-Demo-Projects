package com.qburst.newsreader.api.beans.response;

import java.util.ArrayList;

import com.qburst.newsreader.api.beans.NRBaseBean;

public class NRResponseMessagesBean extends NRBaseBean {
	ArrayList<NRErrorMessageResponseBean> errorMessages;
	ArrayList<String> informationMessages;
	String errorMessage = "";

	public ArrayList<NRErrorMessageResponseBean> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(
			ArrayList<NRErrorMessageResponseBean> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public ArrayList<String> getInformationMessages() {
		return informationMessages;
	}

	public void setInformationMessages(ArrayList<String> informationMessages) {
		this.informationMessages = informationMessages;
	}

	public String errorMessagesToString() {
		for (NRErrorMessageResponseBean errorMsg : errorMessages) {
			errorMessage += errorMsg.getContent();
		}
		return errorMessage;
	}

	public String informationMessagesToString() {
		return informationMessages.toString();
	}

}
