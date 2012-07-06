package com.laurinka.skga.app.rest;

import com.laurinka.skga.app.DomXmlParser;
import com.laurinka.skga.app.rest.OnRestResponse;
import com.laurinka.skga.app.rest.RestClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Webservice for querying SKGA handicap, name of player and club.
 */
public class SkgaService extends RestClient {
    public SkgaService() {
        super("http://skga-radim.rhcloud.com/rest");
    }

    public void queryHcp(String member, final OnSKGAResponse onRestResponseponse) {
        super.execute("members/" + member, new OnRestResponse() {
            @Override
            public void onResponse(String response) {
                Document document = DomXmlParser.XMLfromString(response);
                if (null == document)
                    return;
                NodeList handicap = document.getElementsByTagName("handicap");
                Node item = handicap.item(0);
                String txtHcp = item.getTextContent();

                NodeList name = document.getElementsByTagName("name");
                Node nameItem = name.item(0);
                String txtName = nameItem.getTextContent();

                NodeList club = document.getElementsByTagName("club");
                Node clubItem = club.item(0);
                String txtClub = clubItem.getTextContent();

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
}
