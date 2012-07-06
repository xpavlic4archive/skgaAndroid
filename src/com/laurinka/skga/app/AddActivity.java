package com.laurinka.skga.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.laurinka.skga.app.rest.Hcp;
import com.laurinka.skga.app.rest.OnSKGAResponse;
import com.laurinka.skga.app.rest.SkgaService;

import java.util.HashSet;
import java.util.Set;

public class AddActivity extends Activity {
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(Constants.DATA_PREFERENCES, MODE_PRIVATE);
        setContentView(R.layout.add);
    }

    public void saveNumber(View view) {
        final String message = findNumber();
        Set<String> numbers = sharedPreferences.getStringSet(Constants.SKGA_NUMBERS, new HashSet<String>());
        numbers.add(message);
        sharedPreferences.edit().putStringSet(Constants.SKGA_NUMBERS, numbers).commit();

        new SkgaService().queryHcp(message, new OnSKGAResponse() {
            public void onResponse(Hcp response) {
                Log.d(this.getClass().toString(), response.toString());
                sharedPreferences.edit()             //
                        .putString(Constants.HCP_PREFIX + message, response.getHcp())       //
                        .putString(Constants.CLUB_PREFIX + message, response.getClub())       //
                        .putString(Constants.NAME_PREFIX + message, response.getName())       //
                        .commit();
            }

            public void onError(Integer errorCode, String errorMessage) {
                Log.w(this.getClass().toString(), errorCode + " " + errorMessage);
            }

        });

        finish();
    }

    private String findNumber() {
        EditText editText = findEditText();
        return editText.getText().toString();
    }

    private EditText findEditText() {
        return (EditText) findViewById(R.id.edit_message);
    }
}
