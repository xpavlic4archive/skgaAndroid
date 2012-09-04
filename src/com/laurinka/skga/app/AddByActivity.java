package com.laurinka.skga.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddByActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_by);
	}

	public void byName(View view) {
		startActivity(new Intent(this, AddByNameActivity.class));
		finish();
	}

	public void byNumber(View view) {
		startActivity(new Intent(this, AddByNumberActivity.class));
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
