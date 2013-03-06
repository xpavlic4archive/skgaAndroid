package com.laurinka.skga.app;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
/**
 * Back intro screen.
 * @author radimpavlicek
 *
 */
@SuppressLint("NewApi")
public class IntroActivity extends Activity {


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/** Setting a custom layout for the list activity */
		setContentView(R.layout.intro);
		
		/** Added typeFace for Intro buttons */
		Button myButton=(Button)findViewById(R.id.records_button);
		
		Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/helvneue63medextobl.ttf");
		myButton.setTypeface(typeFace);
		
		/** Hide ActionBar in IntroActivity screen*/
		ActionBar actionBar = getActionBar();
		actionBar.hide();
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
