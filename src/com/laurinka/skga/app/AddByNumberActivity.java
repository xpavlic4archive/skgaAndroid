package com.laurinka.skga.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.laurinka.skga.app.rest.Hcp;
import com.laurinka.skga.app.rest.OnSKGAHcpResponse;
import com.laurinka.skga.app.rest.SkgaService;
import com.laurinka.skga.app.storage.StorageHelper;
import com.laurinka.skga.app.util.ResourceHelper;
/**
 * Back screen where you add number and this will be saved.
 * @author radimpavlicek
 *
 */
public class AddByNumberActivity extends AbstractAddByNumberActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		
	}
	
	public void saveNumber(View view) {
		 String tmessage = findNumber();
		
		Bundle e = new Bundle();
		e.putString("number", tmessage);
		getCapptainAgent().sendSessionEvent("add by skga number", e);

		if (tmessage != null) {
			tmessage = ResourceHelper.onlyNumber(tmessage);
		}

		if (null == tmessage|| tmessage.length() < 1) {
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
			alertDialog.setMessage(getString(R.string.min_1_characters));
			alertDialog.setPositiveButton(R.string.back_to_search_button, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	                  dialog.dismiss();
	               }
	           });

			alertDialog.show();
			return;
		}
		final String message = tmessage;
		new SkgaService().queryHcp(message, new OnSKGAHcpResponse() {

			public void onResponse(Hcp response) {
				Log.d(this.getClass().toString(), response.toString());
				sharedPreferences
						.edit()
						//
						.putString(Constants.SKGA_HCP_PREFIX + message,
								response.getHcp()) //
						.putString(Constants.SKGA_CLUB_PREFIX + message,
								response.getClub()) //
						.putString(Constants.SKGA_NAME_PREFIX + message,
								response.getName()) //
						.commit();
				StorageHelper.addSkgaNumber(sharedPreferences, message);
				sendBroadcast(new Intent(Constants.COM_LAURINKA_SKGA_APP_REFRESH));
			}

			public void onError(Integer errorCode, String errorMessage) {
				Log.w(this.getClass().toString(), errorCode + " "
						+ errorMessage);
			}

		});
		finish();
	}
}
