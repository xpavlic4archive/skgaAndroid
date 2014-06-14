package com.laurinka.skga.app.util;

import java.util.List;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.laurinka.skga.app.Constants;

import com.laurinka.skga.app.rest.CgfService;
import com.laurinka.skga.app.rest.Hcp;
import com.laurinka.skga.app.rest.OnSKGAHcpResponse;
import com.laurinka.skga.app.rest.SkgaService;
import com.laurinka.skga.app.storage.StorageHelper;

public class Updater {
	private static class UpdateHandicapsTask extends AsyncTask<SharedPreferences, Integer, Long> {
	    @Override
	    protected Long doInBackground(SharedPreferences... s) {
	    	SharedPreferences SharedPreferences = s[0];
	    	
			List<String> skgaNmbrs = StorageHelper.getSkgaNumbers(SharedPreferences);
			for (String message : skgaNmbrs) {
				skga(SharedPreferences, message);
			}
			List<String> cgfs = StorageHelper.getCgfNumbers(SharedPreferences);
			for (String cgf : cgfs) {
				cgf(SharedPreferences, cgf);
			}
	
	    	return Long.MAX_VALUE;
	    }
		private static void cgf(final SharedPreferences s, final String message) {
			new CgfService().queryHcp(message, new OnSKGAHcpResponse() {

				public void onResponse(Hcp response) {
					Log.i(this.getClass().toString(), response.toString());
					s.edit()
							//
							.putString(Constants.CGF_HCP_PREFIX + message,
									response.getHcp()) //
							.putString(Constants.CGF_CLUB_PREFIX + message,
									response.getClub()) //
							.putString(Constants.CGF_NAME_PREFIX + message,
									response.getName()) //
							.commit();

				}

				public void onError(Integer errorCode, String errorMessage) {
					Log.w(this.getClass().toString(), errorCode + " "
							+ errorMessage);
				}

			});
		}

		private static void skga(final SharedPreferences s, final String message) {
			new SkgaService().queryHcp(message, new OnSKGAHcpResponse() {
				public void onResponse(Hcp response) {
					Log.i(this.getClass().toString(), response.toString());
					s.edit()
							//
							.putString(Constants.SKGA_HCP_PREFIX + message,
									response.getHcp()) //
							.putString(Constants.SKGA_CLUB_PREFIX + message,
									response.getClub()) //
							.putString(Constants.SKGA_NAME_PREFIX + message,
									response.getName()) //
							.commit();
				}

				public void onError(Integer errorCode, String errorMessage) {
					Log.w(this.getClass().toString(), errorCode + " "
							+ errorMessage);
				}

			});
		}

	}
	public static void invoke(final SharedPreferences s) {
		new Updater.UpdateHandicapsTask().execute(s);	
	}

}
