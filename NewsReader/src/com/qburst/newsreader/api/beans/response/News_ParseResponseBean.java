package com.qburst.newsreader.api.beans.response;

import com.qburst.newsreader.activities.Response;

public class News_ParseResponseBean extends NRBaseResponseBean {
private Response response;

public Response getResponse() {
	return response;
}

public void setResponse(Response response) {
	this.response = response;
}
}
