package com.laurinka.skga.app.rest;

import java.util.List;

/**
 * Callback interface for SKGA webservice call.
 */
public interface OnSKGASearchResponse {
	void onResponse(List<NameNumber> nameNumbers);
	void onError(Integer errorCode, String errorMessage);
}