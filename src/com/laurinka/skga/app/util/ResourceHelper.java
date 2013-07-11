package com.laurinka.skga.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;

import android.app.Activity;

public class ResourceHelper {

	public static String readRawTextFile(Activity anActivity, int id) {
		InputStream inputStream = anActivity.getResources().openRawResource(id);
		InputStreamReader in = new InputStreamReader(inputStream);
		BufferedReader buf = new BufferedReader(in);
		String line;
		StringBuilder text = new StringBuilder();
		try {
			while ((line = buf.readLine()) != null)
				text.append(line);
		} catch (IOException e) {
			return null;
		}
		return text.toString();
	}

	public static String stripAccents(String anStr) {
		if (null == anStr || "".equals(anStr))
			return "";
		String s = Normalizer.normalize(anStr, Normalizer.Form.NFD);
		s = s.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		return s;
	}

	public static String onlyNumber(String a) {
		return a.replaceAll("[^\\d]", "");
	}
}
