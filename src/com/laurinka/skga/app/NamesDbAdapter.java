/*
 * Copyright (C) 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.laurinka.skga.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.laurinka.skga.app.util.CSVReader;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

/**
 * Simple names database access helper class. Defines the basic CRUD operations
 * , and gives the ability to list all names as well as retrieve or modify a
 * specific name.
 * 
 */
public class NamesDbAdapter {

	public static final String KEY_NUMBER = "number";
	public static final String KEY_NAME = "name";
	public static final String KEY_ROWID = "_id";

	private static final String TAG = "NotesDbAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	/**
	 * Database creation sql statement
	 */
	private static final String DATABASE_CREATE = "create table names (_id integer primary key autoincrement, "
			+ "number text not null, name text not null);";

	private static final String DATABASE_NAME = "data";
	private static final String DATABASE_TABLE = "names";
	private static final int DATABASE_VERSION = 2;

	private final Context mCtx;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS names");
			onCreate(db);
		}
	}

	/**
	 * Constructor - takes the context to allow the database to be
	 * opened/created
	 * 
	 * @param ctx
	 *            the Context within which to work
	 */
	public NamesDbAdapter(Context ctx) {
		this.mCtx = ctx;
	}

	/**
	 * Open the notes database. If it cannot be opened, try to create a new
	 * instance of the database. If it cannot be created, throw an exception to
	 * signal the failure
	 * 
	 * @return this (self reference, allowing this to be chained in an
	 *         initialization call)
	 * @throws SQLException
	 *             if the database could be neither opened or created
	 */
	public NamesDbAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	/**
	 * Create a new note using the title and body provided. If the note is
	 * successfully created return the new rowId for that note, otherwise return
	 * a -1 to indicate failure.
	 * 
	 * @param title
	 *            the title of the note
	 * @param body
	 *            the body of the note
	 * @return rowId or -1 if failed
	 */
	public long createName(String title, String body) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NUMBER, title);
		initialValues.put(KEY_NAME, body);

		return mDb.insert(DATABASE_TABLE, null, initialValues);
	}

	/**
	 * Return a Cursor over the list of all notes in the database
	 * 
	 * @return Cursor over all notes
	 */
	public Cursor fetchAllNotes() {

		return mDb.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_NUMBER,
				KEY_NAME }, null, null, null, null, null);
	}

	public boolean isFilled() {
		SQLiteStatement s = mDb.compileStatement("select count(*) from "
				+ DATABASE_TABLE + ";");
		long count = s.simpleQueryForLong();
		return 0 != count;
	}

	/**
	 * Return a Cursor positioned at the note that matches the given rowId
	 * 
	 * @param rowId
	 *            id of note to retrieve
	 * @return Cursor positioned to matching note, if found
	 * @throws SQLException
	 *             if note could not be found/retrieved
	 */
	public Cursor fetchNote(long rowId) throws SQLException {

		Cursor mCursor =

		mDb.query(true, DATABASE_TABLE, new String[] { KEY_ROWID, KEY_NUMBER,
				KEY_NAME }, KEY_ROWID + "=" + rowId, null, null, null, null,
				null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}

	public void importData(Activity anActivity) {
		InputStream inputStream = anActivity.getResources().openRawResource(
				R.raw.numbers);

		InputStreamReader in = new InputStreamReader(inputStream);
		CSVReader csvReader = new CSVReader(in);
		String[] arr;
		int i = 0;
		try {
			mDb.beginTransaction();
			try {
				while ((arr = csvReader.readNext()) != null) {
					createName(arr[1], arr[0]);
					i++;
					if (i% 100 == 0)
						Log.i("IMPORT", i + "");
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
					csvReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			mDb.setTransactionSuccessful();
		} finally {
			mDb.endTransaction();
		}

	}
}
