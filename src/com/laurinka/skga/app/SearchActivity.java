package com.laurinka.skga.app;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
/**
 * Backs search by name screen.
 * @author radimpavlicek
 *
 */
public class SearchActivity extends Activity {

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		
		/** Added typeFace for Intro buttons */
		TextView myTitle=(TextView)findViewById(R.id.title);
		
		Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/helvneue63medextobl.ttf");
		myTitle.setTypeface(typeFace);
		
		/** Hide ActionBar in IntroActivity screen*/
		ActionBar actionBar = getActionBar();
		actionBar.hide();
	}

	public void close(View view) {
		finish();
	}
	
	public void startSearch(View view) {
		final String message = findSearchPattern();
		Intent i  = new Intent(this, AddByNameActivity.class);
		i.putExtra("pattern", message);
		startActivity(i);
	}

	private String findSearchPattern() {
		EditText editText = findEditText();
		return editText.getText().toString();
	}

	private EditText findEditText() {
		return (EditText) findViewById(R.id.edit_search);
	}
}
