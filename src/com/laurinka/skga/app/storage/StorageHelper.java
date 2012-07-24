package com.laurinka.skga.app.storage;

import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import android.content.SharedPreferences;

import com.laurinka.skga.app.Constants;

public class StorageHelper {

	public static Set<String> getNumbers(SharedPreferences sharedPreferences) {
		String tmpCsv = sharedPreferences.getString(Constants.SKGA_NUMBERS, "");
		if (tmpCsv.equals(""))
			return Collections.emptySet();
		Scanner s = new Scanner(tmpCsv);
		Set<String> set = new HashSet<String>() ;
		while (s.hasNext()) {
			set.add(s.next());
		}
		return set;
//		return sharedPreferences.getStringSet(Constants.SKGA_NUMBERS, new HashSet<String>());;
	}

	public static void addMessage(SharedPreferences sharedPreferences,
			String message) {
		String numbers = sharedPreferences.getString(Constants.SKGA_NUMBERS, "");
        numbers += " " + message;
        sharedPreferences.edit().putString(Constants.SKGA_NUMBERS, numbers).commit();		
	}

}
