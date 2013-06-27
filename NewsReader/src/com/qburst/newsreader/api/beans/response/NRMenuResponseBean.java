package com.qburst.newsreader.api.beans.response;

import com.qburst.newsreader.models.BEMenuNavigation;

public class NRMenuResponseBean extends NRBaseResponseBean {
	private BEMenuNavigation navigations;

	public BEMenuNavigation getNavigations() {
		return navigations;
	}

	public void setNavigations(BEMenuNavigation navigations) {
		this.navigations = navigations;
	}
}
