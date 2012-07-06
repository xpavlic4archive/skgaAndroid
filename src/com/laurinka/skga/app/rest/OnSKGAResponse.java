package com.laurinka.skga.app.rest;

/**
 * Callback interface for SKGA webservice call.
 */
public interface OnSKGAResponse {
	public abstract void onResponse(Hcp hcp);
	public abstract void onError(Integer errorCode, String errorMessage);
}