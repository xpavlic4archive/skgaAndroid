package com.laurinka.skga.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;

public class ResourceHelper {

	public static String readRawTextFile(Activity anActivity, int id) {
	    InputStream inputStream = anActivity.getResources().openRawResource(id);
	    InputStreamReader in = new InputStreamReader(inputStream);
	    BufferedReader buf = new BufferedReader(in);
	    String line;
	    StringBuilder text = new StringBuilder();
	    try {
	        while ((line = buf.readLine()) != null) text.append(line);
	    } catch (IOException e) {
	        return null;
	    }
	    return text.toString();
	}

}
