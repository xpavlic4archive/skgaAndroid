package com.laurinka.skga.app.rest;

/**
 * Callback interface on webservice call.
 */
public interface OnRestResponse {
	public abstract void onResponse(String response);
	public abstract void onError(Integer errorCode, String errorMessage);
}