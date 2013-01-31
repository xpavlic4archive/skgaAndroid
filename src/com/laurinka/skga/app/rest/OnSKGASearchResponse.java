package com.laurinka.skga.app.rest;

import java.util.List;

/**
 * Callback interface for SKGA webservice call.
 */
public interface OnSKGASearchResponse {
	public abstract void onResponse(List<Hcp> hcps);
	public abstract void onError(Integer errorCode, String errorMessage);
}