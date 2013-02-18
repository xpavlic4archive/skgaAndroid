package com.laurinka.skga.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import com.laurinka.skga.app.rest.Hcp;
import com.laurinka.skga.app.rest.NameNumber;
import com.laurinka.skga.app.rest.OnSKGAHcpResponse;
import com.laurinka.skga.app.rest.OnSKGASearchResponse;
import com.laurinka.skga.app.rest.SkgaService;
import com.laurinka.skga.app.storage.StorageHelper;

/**
 * Holds list view and controller for adding new number and about activity.
 */
public class AddByNameActivity extends ListActivity {

	private SharedPreferences sharedPreferences;
	public SimpleAdapter adapter;
	// private NamesDbAdapter mDbHelper;
	private String pattern;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_by_name_list);
		sharedPreferences = getSharedPreferences(Constants.DATA_PREFERENCES,
				MODE_PRIVATE);
		Bundle extras = getIntent().getExtras();
		pattern = extras.getString(Constants.PATTERN);

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

		@SuppressWarnings("unchecked")
		HashMap<String, String> s = (HashMap<String, String>) getListAdapter().getItem(position);
		final String message = s.get(Constants.HCP);
		new SkgaService().queryHcp(message, new OnSKGAHcpResponse() {
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
				StorageHelper.addNumber(sharedPreferences, message);
				sendBroadcast(new Intent(
						Constants.COM_LAURINKA_SKGA_APP_REFRESH));
			}

			public void onError(Integer errorCode, String errorMessage) {
				Log.w(this.getClass().toString(), errorCode + " "
						+ errorMessage);
			}

		});
		// c.close();
		finish();
	}

	private void fillData() {
		SkgaService skgaService = new SkgaService();
		skgaService.searchLike(pattern, new OnSKGASearchResponse() {
			public void onResponse(List<NameNumber> response) {
				Log.i(this.getClass().toString(), response.toString());

				List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

				for (NameNumber o : response) {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(Constants.NAME, o.getName());
					map.put(Constants.HCP, o.getNumber());
					data.add(map);
				}

				// ArrayAdapter<NameNumber> adapter = new
				// ArrayAdapter<NameNumber>(AddByNameActivity.this,
				// R.layout.names_row, R.id.text1, response);
				SimpleAdapter adapter = new SimpleAdapter(
						AddByNameActivity.this, data, R.layout.rowlayout,
						new String[] { Constants.NAME , Constants.HCP},
						new int[] { R.id.name , R.id.hcp});
				setListAdapter(adapter);
				// sharedPreferences
				// .edit()
				// //
				// .putString(Constants.HCP_PREFIX + message,
				// response.getHcp()) //
				// .putString(Constants.CLUB_PREFIX + message,
				// response.getClub()) //
				// .putString(Constants.NAME_PREFIX + message,
				// response.getName()) //
				// .commit();
				// StorageHelper.addNumber(sharedPreferences, message);
				// sendBroadcast(new
				// Intent(Constants.COM_LAURINKA_SKGA_APP_REFRESH));
			}

			public void onError(Integer errorCode, String errorMessage) {
				Log.w(this.getClass().toString(), errorCode + " "
						+ errorMessage);
			}

		});
		// if (!mDbHelper.isFilled()) {
		// final ProgressDialog dialog = ProgressDialog.show(this, "", "",
		// true);
		//
		// new LongOperation(this, dialog).execute("");
		//
		// }
		updateList();
	}

	private void updateList() {
		// Get all of the notes from the database and create the item list
		if (null == pattern || "".equals(pattern)) {
			// c = mDbHelper.fetchAllNotes();
		} else {
			// c = mDbHelper.fetchNotesWhere(pattern);
		}

		String[] from = new String[] { Constants.NAME };
		int[] to = new int[] { R.id.text1 };

		// Now create an array adapter and set it to display using our row
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		// R.layout.names_row, R.id.text1, from);
		// setListAdapter(adapter);
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
			// mDbHelper.importData(activity);
			return "Executed";
		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();
		}
	}
}