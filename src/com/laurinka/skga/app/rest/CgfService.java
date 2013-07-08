package com.laurinka.skga.app.rest;


import android.text.TextUtils;

/**
 * Webservice for querying SKGA handicap, name of player and club.
 */
public class CgfService extends RestClient {
    public CgfService() {
		super( "http://skga-radim.rhcloud.com/rest");
		//super( "http://192.168.56.1/rest/"); /* server for local test */
    }

	public void queryHcp(String member,
			final OnSKGAHcpResponse onRestResponseponse) {
		if (null == member || "".equals(member))
			return;
		super.execute("cgf/" + member, new OnHcpRestResponse(onRestResponseponse));
	}
	public void searchLike(String what,
			final OnSKGASearchResponse onRestResponseponse) {
		if (null == what || "".equals(what))
			return;
		super.execute("cgfs/search?q=" + TextUtils.htmlEncode(what),
				new OnSearchResponse(onRestResponseponse));
	}
}
