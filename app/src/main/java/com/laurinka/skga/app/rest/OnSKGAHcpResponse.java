package com.laurinka.skga.app.rest;

/**
 * Callback interface for SKGA webservice call.
 */
public interface OnSKGAHcpResponse {
	void onResponse(Hcp hcp);
	void onError(Integer errorCode, String errorMessage);
}