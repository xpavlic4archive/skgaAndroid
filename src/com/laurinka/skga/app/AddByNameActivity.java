package com.laurinka.skga.app;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import com.laurinka.skga.app.rest.Hcp;
import com.laurinka.skga.app.rest.OnSKGAResponse;
import com.laurinka.skga.app.rest.SkgaService;
import com.laurinka.skga.app.storage.StorageHelper;

/**
 * Holds list view and controller for adding new number and about activity.
 */
public class AddByNameActivity extends ListActivity {

	private SharedPreferences sharedPreferences;
	public SimpleAdapter adapter;

	private NamesDbAdapter mDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_by_name_list);
		sharedPreferences = getSharedPreferences(Constants.DATA_PREFERENCES,
				MODE_PRIVATE);
		mDbHelper = new NamesDbAdapter(this);
		mDbHelper.open();
		fillData();
	}

	public void close(View view) {
		finish();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// Create a progress bar to display while the list loads
		ProgressBar progressBar = new ProgressBar(this);
		progressBar.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		progressBar.setIndeterminate(true);
		getListView().setEmptyView(progressBar);

		// Must add the progress bar to the root of the layout
		ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
		root.addView(progressBar);

		Cursor c = (Cursor) getListAdapter().getItem(position);
		String s = c.getString(c.getColumnIndex("number"));
		final String message = s;
		
		new SkgaService().queryHcp(message, new OnSKGAResponse() {
			public void onResponse(Hcp response) {
				Log.i(this.getClass().toString(), response.toString());
				sharedPreferences
						.edit()
						//
						.putString(Constants.HCP_PREFIX + message,
								response.getHcp()) //
						.putString(Constants.CLUB_PREFIX + message,
								response.getClub()) //
						.putString(Constants.NAME_PREFIX + message,
								response.getName()) //
						.commit();
				StorageHelper.addMessage(sharedPreferences, message);
				sendBroadcast(new Intent(Constants.COM_LAURINKA_SKGA_APP_REFRESH));
			}

			public void onError(Integer errorCode, String errorMessage) {
				Log.w(this.getClass().toString(), errorCode + " "
						+ errorMessage);
			}

		});
		finish();
	}

	private void fillData() {
		if (!mDbHelper.isFilled()) {
			final ProgressDialog dialog = ProgressDialog.show(this, "", "",
					true);

			new LongOperation(this, dialog).execute("");
			// mDbHelper.importData(this);

		}
		updateList();
	}

	private void updateList() {
		// Get all of the notes from the database and create the item list
		Cursor c = mDbHelper.fetchAllNotes();
		startManagingCursor(c);

		String[] from = new String[] { Constants.NAME };
		int[] to = new int[] { R.id.text1 };

		// Now create an array adapter and set it to display using our row
		SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
				R.layout.names_row, c, from, to);
		setListAdapter(notes);
	}

	private class LongOperation extends AsyncTask<String, Void, String> {
		Activity activity;
		private ProgressDialog dialog;

		public LongOperation(Activity act, ProgressDialog adialog) {
			dialog = adialog;
			activity = act;
		}

		@Override
		protected String doInBackground(String... params) {
			mDbHelper.importData(activity);
			return "Executed";
		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();
			updateList();
		}
	}
}