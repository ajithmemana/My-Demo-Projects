package com.qburst.newsreader.api.beans;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NRBaseBean {

	public String toJsonString() {
		Gson gson = new GsonBuilder().serializeNulls().create();
		return gson.toJson(this);
	}
}
