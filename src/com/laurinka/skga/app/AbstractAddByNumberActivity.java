package com.laurinka.skga.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Back screen where you add number and this will be saved.
 * @author radimpavlicek
 *
 */

public abstract class AbstractAddByNumberActivity extends Activity {
	SharedPreferences sharedPreferences;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		sharedPreferences = getSharedPreferences(Constants.DATA_PREFERENCES,
				MODE_PRIVATE);
	}

	public void close(View view) {
		finish();
	}
	
	abstract void saveNumber(View view);

	protected String findNumber() {
		EditText editText = findEditText();
		return editText.getText().toString();
	}

	protected EditText findEditText() {
		return (EditText) findViewById(R.id.edit_message);
	}
}
