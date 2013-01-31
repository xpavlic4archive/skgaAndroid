package com.laurinka.skga.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.laurinka.skga.app.rest.Hcp;
import com.laurinka.skga.app.rest.OnSKGAHcpResponse;
import com.laurinka.skga.app.rest.SkgaService;
import com.laurinka.skga.app.storage.StorageHelper;

/**
 * Holds list view and controller for adding new number and about activity.
 */
public class MainActivity extends ListActivity {

    private SharedPreferences sharedPreferences;
    private ArrayList<HashMap<String, String>> data;
    public SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        sharedPreferences = getSharedPreferences(Constants.DATA_PREFERENCES, MODE_PRIVATE);
        List<String> numbers = StorageHelper.getNumbers(sharedPreferences);
        if (numbers.isEmpty()) {
            Intent intent = new Intent(this, AddByActivity.class);
            startActivity(intent);
        }
   
        showList();
        updateAll();
        showList();
    }
 
    public void add(View view) {
        startActivity(new Intent(this, AddByActivity.class));
    }

    public void about(View view) {
        startActivity(new Intent(this, AboutActivity.class));
    }
    
    public void edit(View view) {
        startActivity(new Intent(this, EditActivity.class));
    }

    private void showList() {
        data = findData();

        adapter = new SimpleAdapter(this, data,
                R.layout.rowlayout, new String[]{Constants.NAME, Constants.HCP}, new int[]{R.id.name, R.id.hcp});

        // Assign adapter to ListView
        setListAdapter(adapter);
    }

    private ArrayList<HashMap<String, String>> findData() {
        List<String> numbers = StorageHelper.getNumbers(sharedPreferences);

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String[] numbersA = numbers.toArray(new String[0]);
        for (int i = 0; i < numbers.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            String value = numbersA[i];
            map.put(Constants.SKGA_NR, value);
            String hcp = sharedPreferences.getString(Constants.HCP_PREFIX + value, "");
            map.put(Constants.HCP, hcp);
            String name = sharedPreferences.getString(Constants.NAME_PREFIX + value, "");
            map.put(Constants.NAME, name);
            data.add(map);
        }
        return data;
    }
    
    public class UpdaterBroadcastReceiver extends BroadcastReceiver {       
		@Override
		public void onReceive(Context context, Intent intent) {
			showList();
		}
}

    @Override
    protected void onResume() {
    	//handle changes from edit activity
    	showList();
    	
    	//handles changes from async tasks
        IntentFilter filter = new IntentFilter(Constants.COM_LAURINKA_SKGA_APP_REFRESH);
        UpdaterBroadcastReceiver r = new UpdaterBroadcastReceiver();
        registerReceiver(r,filter);
        super.onResume();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // Create a progress bar to display while the list loads
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        progressBar.setIndeterminate(true);
        getListView().setEmptyView(progressBar);

        // Must add the progress bar to the root of the layout
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        root.addView(progressBar);

        Map<String, String> map = (Map<String, String>) getListAdapter().getItem(position);
        final String message = map.get(Constants.SKGA_NR);
        new SkgaService().queryHcp(message, new OnSKGAHcpResponse() {
            public void onResponse(Hcp response) {
                Log.i(this.getClass().toString(), response.toString());
                sharedPreferences.edit()             //
                        .putString(Constants.HCP_PREFIX + message, response.getHcp())       //
                        .putString(Constants.CLUB_PREFIX + message, response.getClub())       //
                        .putString(Constants.NAME_PREFIX + message, response.getName())       //
                        .commit();
                callBack(message, response);
            }

            public void onError(Integer errorCode, String errorMessage) {
                Log.w(this.getClass().toString(), errorCode + " " + errorMessage);
            }

        });
    }
    public void callBack(String message, Hcp response) {
        showList();
        Toast.makeText(this, response.getClub(), Toast.LENGTH_LONG).show();
    }

    public void updateAll() {
        try {
    	int count = getListAdapter().getCount();
    	for (int i= 0; i<count; i++) {
    		updateItemOnIndex(i);
    	}
        } catch (IndexOutOfBoundsException ignored) {
            //TODO radim
        }
    }
    private void updateItemOnIndex(int position) {
    	   Map<String, String> map = (Map<String, String>) getListAdapter().getItem(position);
           final String message = map.get(Constants.SKGA_NR);
    	new SkgaService().queryHcp(message, new OnSKGAHcpResponse() {
            public void onResponse(Hcp response) {
                Log.i(this.getClass().toString(), response.toString());
                sharedPreferences.edit()             //
                        .putString(Constants.HCP_PREFIX + message, response.getHcp())       //
                        .putString(Constants.CLUB_PREFIX + message, response.getClub())       //
                        .putString(Constants.NAME_PREFIX + message, response.getName())       //
                        .commit();
                //callBack(message, response);
            }

            public void onError(Integer errorCode, String errorMessage) {
                Log.w(this.getClass().toString(), errorCode + " " + errorMessage);
            }

        });
    }
}