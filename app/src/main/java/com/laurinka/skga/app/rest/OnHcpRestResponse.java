package com.laurinka.skga.app.rest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.laurinka.skga.app.DomXmlParser;

public final class OnHcpRestResponse implements OnRestResponse {
	private final OnSKGAHcpResponse onRestResponseponse;

	public OnHcpRestResponse(OnSKGAHcpResponse onRestResponseponse) {
		this.onRestResponseponse = onRestResponseponse;
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
		if (null == nameItem) {
			return;
		}
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
}