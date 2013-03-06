package com.laurinka.skga.app;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.laurinka.skga.app.rest.CgfService;
import com.laurinka.skga.app.rest.Hcp;
import com.laurinka.skga.app.rest.OnSKGAHcpResponse;
import com.laurinka.skga.app.storage.StorageHelper;

/**
 * Back screen where you add cgf number and this will be saved.
 * @author radimpavlicek
 *
 */
@SuppressLint("NewApi")
public class AddByCgfNumberActivity extends AbstractAddByNumberActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    
        setContentView(R.layout.add_cgf);
        
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
        Log.i(this.getClass().toString(), message);

		new CgfService().queryHcp(message, new OnSKGAHcpResponse() {

			public void onResponse(Hcp response) {
				Log.d(this.getClass().toString(), response.toString());
				sharedPreferences
						.edit()
						//
						.putString(Constants.CGF_HCP_PREFIX + message,
								response.getHcp()) //
						.putString(Constants.CGF_CLUB_PREFIX + message,
								response.getClub()) //
						.putString(Constants.CGF_NAME_PREFIX + message,
								response.getName()) //
						.commit();
				StorageHelper.addCgfNumber(sharedPreferences, message);
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
