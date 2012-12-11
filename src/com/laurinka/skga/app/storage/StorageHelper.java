package com.laurinka.skga.app.storage;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import android.content.SharedPreferences;

import com.laurinka.skga.app.Constants;

/**
 * Crud operation on storage with golf numbers and handicap values.
 * @author radimpavlicek
 *
 */
public class StorageHelper {

	public static List<String> getNumbers(SharedPreferences sharedPreferences) {
		String tmpCsv = sharedPreferences.getString(Constants.SKGA_NUMBERS, "");
		if (tmpCsv.equals(""))
			return Collections.emptyList();
		Scanner s = new Scanner(tmpCsv);
		LinkedList<String> set = new LinkedList<String>() ;
		while (s.hasNext()) {
			set.add(s.next());
		}
		return set;
	}

	public static void addNumber(SharedPreferences sharedPreferences,
			String message) {
		String numbers = sharedPreferences.getString(Constants.SKGA_NUMBERS, "");
        numbers += " " + message;
        sharedPreferences.edit().putString(Constants.SKGA_NUMBERS, numbers).commit();		
	}
	public static void removeNumber(SharedPreferences sharedPreferences, String number) {
		String tmpCsv = sharedPreferences.getString(Constants.SKGA_NUMBERS, "");
		if (tmpCsv.equals(""))
			return;
		String replaceFirst = tmpCsv.replaceFirst(number, "");
		String wipedDoubleSpaces = replaceFirst.replaceAll("  ", " ");
		sharedPreferences.edit().putString(Constants.SKGA_NUMBERS, wipedDoubleSpaces).commit();
	}
}
