package com.laurinka.skga.app.rest;

import java.util.Collections;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.laurinka.skga.app.DomXmlParser;

public final class OnRestResponseImplementation implements OnRestResponse {
	private final OnSKGASearchResponse onRestResponseponse;

	public OnRestResponseImplementation(OnSKGASearchResponse callBack) {
		this.onRestResponseponse = callBack;
	}

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
		String txtName = nameItem.getChildNodes().item(0).getNodeValue();

		NodeList club = document.getElementsByTagName("club");
		Node clubItem = club.item(0);
		String txtClub = clubItem.getChildNodes().item(0).getNodeValue();

		Hcp hcp = new Hcp();
		hcp.setHcp(txtHcp);
		hcp.setName(txtName);
		hcp.setClub(txtClub);

		onRestResponseponse.onResponse(Collections.<Hcp> emptyList());
	}

	@Override
	public void onError(Integer errorCode, String errorMessage) {
		onRestResponseponse.onError(errorCode, errorMessage);
	}
}