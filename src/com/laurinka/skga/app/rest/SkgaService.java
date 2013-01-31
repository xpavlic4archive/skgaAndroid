package com.laurinka.skga.app.rest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.laurinka.skga.app.DomXmlParser;

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
		super.execute("members/" + member, new OnRestResponse() {
			@Override
			public void onResponse(String response) {
				Document document = DomXmlParser.XMLfromString(response);
				if (null == document)
					return;
				NodeList handicap = document.getElementsByTagName("handicap");
				Node item = handicap.item(0);
				String txtHcp = item.getChildNodes().item(0).getNodeValue();

				NodeList name = document.getElementsByTagName("name");
				Node nameItem = name.item(0);
				String txtName = nameItem.getChildNodes().item(0)
						.getNodeValue();

				NodeList club = document.getElementsByTagName("club");
				Node clubItem = club.item(0);
				String txtClub = clubItem.getChildNodes().item(0)
						.getNodeValue();

				Hcp hcp = new Hcp();
				hcp.setHcp(txtHcp);
				hcp.setName(txtName);
				hcp.setClub(txtClub);

				onRestResponseponse.onResponse(hcp);
			}

			@Override
			public void onError(Integer errorCode, String errorMessage) {
				onRestResponseponse.onError(errorCode, errorMessage);
			}
		});
	}

	public void searchLike(String what,
			final OnSKGASearchResponse onRestResponseponse) {
		if (null == what || "".equals(what))
			return;
		super.execute("members/search?q=" + what,
				new OnRestResponseImplementation(onRestResponseponse));
	}
}
