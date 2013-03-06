package com.laurinka.skga.app;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
/**
 * Shows screen with 2 choices, either search by name or search by number.
 * @author radimpavlicek
 *
 */
@SuppressLint("NewApi")
public class AddByActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_by);
		
		/** Added typeFace for Intro buttons */
		TextView myTitle=(TextView)findViewById(R.id.title);
		
		Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/helvneue63medextobl.ttf");
		myTitle.setTypeface(typeFace);
		
		/** Hide ActionBar in IntroActivity screen*/
		ActionBar actionBar = getActionBar();
		actionBar.hide();
	}


	public void byNumber(View view) {
		startActivity(new Intent(this, AddByNumberActivity.class));
		finish();
	}
	public void byCgfNumber(View view) {
		startActivity(new Intent(this, AddByCgfNumberActivity.class));
		finish();
	}

	public void search(View view) {
		startActivity(new Intent(this, SearchActivity.class));
		finish();
	}

	public void back(View view) {
		finish();
	}
}
