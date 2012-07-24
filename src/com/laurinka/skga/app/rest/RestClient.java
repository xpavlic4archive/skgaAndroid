package com.laurinka.skga.app.rest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class RestClient {

    private String server;

    public RestClient(String server) {
		 this.server = server;
	}

	public String getServer() {
        return server;
    }

    public void execute(String param, OnRestResponse onReponse) {

        HttpUriRequest request = new HttpGet(getServer() + "/" + param);

        request.setHeader("Content-type", "application/json");
        request.setHeader("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3");

        RestRequest restRequest = new RestRequest();
        restRequest.setOnResponse(onReponse);
        restRequest.setRequest(request);
        restRequest.execute();

    }

    private class RestRequest extends AsyncTask<String, Integer, String> {

        private HttpUriRequest request;
        private OnRestResponse onResponse;
        private Integer errorCode;
        private String errorMessage;

        public HttpUriRequest getRequest() {
            return request;
        }

        public void setRequest(HttpUriRequest request) {
            this.request = request;
        }

        public OnRestResponse getOnResponse() {
            return onResponse;
        }

        public void setOnResponse(OnRestResponse onReponse) {
            this.onResponse = onReponse;
        }

        public Integer getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(Integer errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        @Override
        protected String doInBackground(String... params) {

            HttpClient client = new DefaultHttpClient();
            HttpResponse httpResponse;
            String response = null;

            try {

                httpResponse = client.execute(getRequest());

                setErrorCode(httpResponse.getStatusLine().getStatusCode());
                setErrorMessage(httpResponse.getStatusLine().getReasonPhrase());

                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    response = EntityUtils.toString(entity, "UTF-8");
                }

            } catch (Exception e) {

                setErrorCode(e.hashCode());
                setErrorMessage(e.getLocalizedMessage());

            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            if (getOnResponse() != null) {
                if (result != null) {
                    getOnResponse().onResponse(result);
                } else {
                    getOnResponse().onError(getErrorCode(), getErrorMessage());
                }
            }
        }
    }

}