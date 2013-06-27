package com.qburst.newsreader.interfaces;

import com.qburst.newsreader.api.beans.response.News_ParseResponseBean;

public interface NewsListener extends NRBaseAPIListener {
	
	public void receivednewsapi(News_ParseResponseBean respBean);
	public void errorInResponse();
    
    }
