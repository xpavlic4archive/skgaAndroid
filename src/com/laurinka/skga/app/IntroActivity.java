package com.laurinka.skga.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IntroActivity extends Activity {
	
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
        IntroActivity.this.finish();
    }
}
