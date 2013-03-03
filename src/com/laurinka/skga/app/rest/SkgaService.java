package com.laurinka.skga.app.rest;



/**
 * Webservice for querying SKGA handicap, name of player and club.
 */
public class SkgaService extends RestClient {
    public SkgaService() {
		super( "http://skga-radim.rhcloud.com/rest");
		//super( "http://192.168.56.1/rest/"); /* server for local test */
    }

	public void queryHcp(String member,
			final OnSKGAHcpResponse onRestResponseponse) {
		if (null == member || "".equals(member))
			return;
		super.execute("members/" + member, new OnHcpRestResponse(onRestResponseponse));
	}

	public void searchLike(String what,
			final OnSKGASearchResponse onRestResponseponse) {
		if (null == what || "".equals(what))
			return;
		super.execute("members/search?q=" + what,
				new OnSearchResponse(onRestResponseponse));
	}
}
