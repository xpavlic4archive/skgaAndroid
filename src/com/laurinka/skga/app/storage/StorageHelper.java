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
		List<String> skgaList = getSkgaNumbers(sharedPreferences);
		List<String> cgfList = getCgfNumbers(sharedPreferences);
		List<String> l = new LinkedList<String>();
		l.addAll(skgaList);
		l.addAll(cgfList);
		return l;
	}
	public static List<String> getSkgaNumbers(SharedPreferences sharedPreferences) {
		String tmpCsv = sharedPreferences.getString(Constants.SKGA_NUMBERS, "");
		return extractNumbers(tmpCsv);
	}
	public static List<String> getCgfNumbers(SharedPreferences sharedPreferences) {
		String tmpCsv = sharedPreferences.getString(Constants.CGF_NUMBERS, "");
		return extractNumbers(tmpCsv);
	}
	
	private static List<String> extractNumbers(String tmpCsv) {
		if (tmpCsv.equals(""))
			return Collections.emptyList();
		Scanner s = new Scanner(tmpCsv);
		LinkedList<String> set = new LinkedList<String>() ;
		while (s.hasNext()) {
			set.add(s.next());
		}
		return set;
	}

	public static void addSkgaNumber(SharedPreferences sharedPreferences,
			String message) {
		String numbers = sharedPreferences.getString(Constants.SKGA_NUMBERS, "");
        numbers += " " + message;
        sharedPreferences.edit().putString(Constants.SKGA_NUMBERS, numbers).commit();		
	}
	
	public static void addCgfNumber(SharedPreferences sharedPreferences,
			String message) {
		String numbers = sharedPreferences.getString(Constants.CGF_NUMBERS, "");
        numbers += " " + message;
        sharedPreferences.edit().putString(Constants.CGF_NUMBERS, numbers).commit();		
	}
	
	public static void removeSkgaNumber(SharedPreferences sharedPreferences, String number) {
		String tmpCsv = sharedPreferences.getString(Constants.SKGA_NUMBERS, "");
		if (tmpCsv.equals(""))
			return;
		String replaceFirst = tmpCsv.replaceFirst(number, "");
		String wipedDoubleSpaces = replaceFirst.replaceAll("  ", " ");
		sharedPreferences.edit().putString(Constants.SKGA_NUMBERS, wipedDoubleSpaces).commit();
	}
}
