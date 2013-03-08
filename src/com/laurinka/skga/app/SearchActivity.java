package com.laurinka.skga.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
/**
 * Backs search by name screen.
 * @author radimpavlicek
 *
 */
public class SearchActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

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
