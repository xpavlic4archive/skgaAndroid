package com.laurinka.skga.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ubikod.capptain.android.sdk.activity.CapptainActivity;
/**
 * Back intro screen.
 * @author radimpavlicek
 *
 */

public class IntroActivity extends CapptainActivity {


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/** Setting a custom layout for the list activity */
		setContentView(R.layout.intro);

	}
	
    public void about(View view) {
        startActivity(new Intent(this, AboutActivity.class));
    }
    
    public void view(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
    
    public void close(View view) {
        finish();
    }
}
