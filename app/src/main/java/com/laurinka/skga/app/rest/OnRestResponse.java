package com.laurinka.skga.app.rest;

/**
 * Callback interface on webservice call.
 */
public interface OnRestResponse {
	void onResponse(String response);
	void onError(Integer errorCode, String errorMessage);
}