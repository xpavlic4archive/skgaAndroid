package com.laurinka.skga.app;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;

import com.laurinka.skga.app.storage.StorageHelper;

public class EditActivity extends ListActivity {
	private ArrayList<String> data;
	
	private ArrayAdapter<String> adapter;
	private SharedPreferences sharedPreferences;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/** Setting a custom layout for the list activity */
		setContentView(R.layout.edit);
		sharedPreferences = getSharedPreferences(Constants.DATA_PREFERENCES,
				MODE_PRIVATE);
		showList();
	}

	public void close(View view) {
		sendBroadcast(new Intent(Constants.COM_LAURINKA_SKGA_APP_REFRESH));
		finish();
	}

	private void showList() {
		findData();
		/** Defining the ArrayAdapter to set items to ListView */
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice, data);
				// Assign a layout for styling it, this seems to be a generic layout #davidm

		// Assign adapter to ListView
		setListAdapter(adapter);
	}

	private void findData() {
		List<String> numbers = StorageHelper.getNumbers(sharedPreferences);

		ArrayList<String> data = new ArrayList<String>();
		String[] numbersA = numbers.toArray(new String[0]);
		for (int i = 0; i < numbers.size(); i++) {
			String value = numbersA[i];
			String name = sharedPreferences.getString(Constants.NAME_PREFIX
					+ value, "");
			data.add(name);

		}
		this.data =  data;
	}
	
	public void delete(View view) {
        SparseBooleanArray checkedItemPositions = getListView().getCheckedItemPositions();
        int itemCount = getListView().getCount();
        List<String> numbers = StorageHelper.getNumbers(sharedPreferences);
        for(int i=itemCount-1; i >= 0; i--){
            if(checkedItemPositions.get(i)){
                adapter.remove(data.get(i));
                getListView().setItemChecked(i, false);
            	String toBeDeleted = numbers.get(i);
            	StorageHelper.removeNumber(sharedPreferences, toBeDeleted);
            }
        }
        adapter.notifyDataSetChanged();
	}
}
