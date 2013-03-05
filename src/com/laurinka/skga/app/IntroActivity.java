package com.laurinka.skga.app;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
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
		String s = "buttonIntro";
		RelativeLayout layout = new RelativeLayout(this);
		Button button = (Button)layout.findViewWithTag(s);
		
		Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/helvneue63medextobl.ttf");
		button.setTypeface(typeFace);
		
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
