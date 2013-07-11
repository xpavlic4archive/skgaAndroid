package com.laurinka.skga.app;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import com.laurinka.skga.app.rest.CgfService;
import com.laurinka.skga.app.rest.Hcp;
import com.laurinka.skga.app.rest.NameNumber;
import com.laurinka.skga.app.rest.OnSKGAHcpResponse;
import com.laurinka.skga.app.rest.OnSKGASearchResponse;
import com.laurinka.skga.app.rest.SkgaService;
import com.laurinka.skga.app.storage.StorageHelper;
import com.ubikod.capptain.android.sdk.activity.CapptainListActivity;

/**
 * Holds list view and controller for adding new number and about activity.
 */

public class AddByNameActivity extends CapptainListActivity {

	private SharedPreferences sharedPreferences;
	public SimpleAdapter adapter;
	private String pattern;
	private String type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_by_name_list);

		sharedPreferences = getSharedPreferences(Constants.DATA_PREFERENCES,
				MODE_PRIVATE);
		Bundle extras = getIntent().getExtras();
		pattern = extras.getString(Constants.PATTERN);
		type = extras.getString("type");
		Bundle e = new Bundle();
		e.putString("patern", pattern);
		e.putString("type", type);
		getCapptainAgent().sendSessionEvent("searchByName", e);

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
		HashMap<String, String> s = (HashMap<String, String>) getListAdapter()
				.getItem(position);
		final String message = s.get(Constants.HCP);
		
		Bundle e = new Bundle();
		e.putString("number", message);
		getCapptainAgent().sendSessionEvent("add by number from search list", e);

		if (type.equals("skga")) {
			new SkgaService().queryHcp(message, new OnSKGAHcpResponse() {
				public void onResponse(Hcp response) {
					Log.i(this.getClass().toString(), response.toString());
					sharedPreferences
							.edit()
							//
							.putString(Constants.SKGA_HCP_PREFIX + message,
									response.getHcp()) //
							.putString(Constants.SKGA_CLUB_PREFIX + message,
									response.getClub()) //
							.putString(Constants.SKGA_NAME_PREFIX + message,
									response.getName()) //
							.commit();
					StorageHelper.addSkgaNumber(sharedPreferences, message);
					sendBroadcast(new Intent(
							Constants.COM_LAURINKA_SKGA_APP_REFRESH));
				}

				public void onError(Integer errorCode, String errorMessage) {
					Log.w(this.getClass().toString(), errorCode + " "
							+ errorMessage);
				}

			});
		} else {
			new CgfService().queryHcp(message, new OnSKGAHcpResponse() {

				public void onResponse(Hcp response) {
					Log.i(this.getClass().toString(), response.toString());
					sharedPreferences
							.edit()
							//
							.putString(Constants.CGF_HCP_PREFIX + message,
									response.getHcp()) //
							.putString(Constants.CGF_CLUB_PREFIX + message,
									response.getClub()) //
							.putString(Constants.CGF_NAME_PREFIX + message,
									response.getName()) //
							.commit();
					StorageHelper.addCgfNumber(sharedPreferences, message);
					sendBroadcast(new Intent(
							Constants.COM_LAURINKA_SKGA_APP_REFRESH));
				}

				public void onError(Integer errorCode, String errorMessage) {
					Log.w(this.getClass().toString(), errorCode + " "
							+ errorMessage);
				}

			});
		}
		// go to the main screen clearing off the stack of already resumed
		// activities
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	private void fillData() {
		ProgressDialog prog;
		prog = new ProgressDialog(this);
		prog.setIndeterminate(false);
		prog.setMessage(getString(R.string.searching));
		prog.show();

		try {
			search(prog);
		} catch (UnsupportedEncodingException e) {
			Log.i(this.getClass().toString(), e.toString());
			prog.dismiss();
		}

	}

	private void search(final ProgressDialog d)
			throws UnsupportedEncodingException {
		List<String> skgaNmbrs = StorageHelper
				.getSkgaNumbers(sharedPreferences);
		if ("skga".equals(type)) {
			new SkgaService().searchLike(pattern, new OnSKGASearchResponse() {
				public void onResponse(List<NameNumber> response) {
					Log.i(this.getClass().toString(), response.toString());

					List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

					for (NameNumber o : response) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put(Constants.NAME, o.getName());
						map.put(Constants.HCP, o.getNumber());
						data.add(map);
					}

					d.dismiss();
					SimpleAdapter adapter = new SimpleAdapter(
							AddByNameActivity.this, data, R.layout.rowlayout,
							new String[] { Constants.NAME, Constants.HCP },
							new int[] { R.id.name, R.id.hcp });
					setListAdapter(adapter);
				}

				public void onError(Integer errorCode, String errorMessage) {
					d.dismiss();
					Log.w(this.getClass().toString(), errorCode + " "
							+ errorMessage);
				}

			});
		} else {
			new CgfService().searchLike(pattern, new OnSKGASearchResponse() {
				public void onResponse(List<NameNumber> response) {
					Log.i(this.getClass().toString(), response.toString());

					List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

					for (NameNumber o : response) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put(Constants.NAME, o.getName());
						map.put(Constants.HCP, o.getNumber());
						data.add(map);
					}

					d.dismiss();
					SimpleAdapter adapter = new SimpleAdapter(
							AddByNameActivity.this, data, R.layout.rowlayout,
							new String[] { Constants.NAME, Constants.HCP },
							new int[] { R.id.name, R.id.hcp });
					setListAdapter(adapter);
				}

				public void onError(Integer errorCode, String errorMessage) {
					d.dismiss();
					Log.w(this.getClass().toString(), errorCode + " "
							+ errorMessage);
				}

			});

		}
	}
}