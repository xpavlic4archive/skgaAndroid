package com.laurinka.skga.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.laurinka.skga.app.rest.Hcp;
import com.laurinka.skga.app.rest.OnSKGAResponse;
import com.laurinka.skga.app.rest.SkgaService;
import com.laurinka.skga.app.storage.StorageHelper;

public class AddByNumberActivity extends Activity {
	private SharedPreferences sharedPreferences;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		sharedPreferences = getSharedPreferences(Constants.DATA_PREFERENCES,
				MODE_PRIVATE);
		setContentView(R.layout.add);
	}

	public void close(View view) {
		finish();
	}
	
	public void saveNumber(View view) {
		final String message = findNumber();

		new SkgaService().queryHcp(message, new OnSKGAResponse() {

			public void onResponse(Hcp response) {
				Log.d(this.getClass().toString(), response.toString());
				sharedPreferences
						.edit()
						//
						.putString(Constants.HCP_PREFIX + message,
								response.getHcp()) //
						.putString(Constants.CLUB_PREFIX + message,
								response.getClub()) //
						.putString(Constants.NAME_PREFIX + message,
								response.getName()) //
						.commit();
				StorageHelper.addMessage(sharedPreferences, message);
				sendBroadcast(new Intent(Constants.COM_LAURINKA_SKGA_APP_REFRESH));
			}

			public void onError(Integer errorCode, String errorMessage) {
				Log.w(this.getClass().toString(), errorCode + " "
						+ errorMessage);
			}

		});
		finish();
	}

	private String findNumber() {
		EditText editText = findEditText();
		return editText.getText().toString();
	}

	private EditText findEditText() {
		return (EditText) findViewById(R.id.edit_message);
	}
}
