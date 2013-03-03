package com.laurinka.skga.app.rest;




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
	public static void main(String[] args) {
		CgfService svc = new CgfService();
		svc.queryHcp("950806", new OnSKGAHcpResponse() {
			@Override
			public void onResponse(Hcp hcp) {
				System.out.println(hcp);
			}

			@Override
			public void onError(Integer errorCode, String errorMessage) {
				throw new IllegalStateException();
			}
		});
	}

}
