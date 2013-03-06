package com.laurinka.skga.app;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.laurinka.skga.app.rest.Hcp;
import com.laurinka.skga.app.rest.OnSKGAHcpResponse;
import com.laurinka.skga.app.rest.SkgaService;
import com.laurinka.skga.app.storage.StorageHelper;
/**
 * Back screen where you add number and this will be saved.
 * @author radimpavlicek
 *
 */
public class AddByNumberActivity extends AbstractAddByNumberActivity {

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		
		/** Added typeFace for Intro buttons */
		TextView myTitle=(TextView)findViewById(R.id.title);
		
		Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/helvneue63medextobl.ttf");
		myTitle.setTypeface(typeFace);
		
		/** Hide ActionBar in IntroActivity screen*/
		ActionBar actionBar = getActionBar();
		actionBar.hide();
	}
	
	public void saveNumber(View view) {
		final String message = findNumber();

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
