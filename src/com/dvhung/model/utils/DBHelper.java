package com.dvhung.model.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.util.Log;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static String DB_PATH;
	private static String DB_NAME = "aaa.png";
	private static final int DATABASE_VERSION = 1;

	private SQLiteDatabase myDatabase;
	private final Context myContext;

	private static DBHelper instance;

	public static DBHelper getInstance(Context context) {
		if (instance == null)
			instance = new DBHelper(context);

		return instance;
	}

	@SuppressLint("SdCardPath")
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION);
		myContext = context;
		if (!checkDataBase()) {
			Log.e("Khong ton tai DB", "khong ton tain");
			try {
				DB_PATH = "/data/data/" + myContext.getPackageName()
						+ "/databases/";
				File createOutFile = new File(DB_PATH);
				if (!createOutFile.exists()) {
					createOutFile.mkdir();
					CreateDatabase();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void CreateDatabase() throws IOException {
		// boolean dbExist = checkDataBase();
		//
		// if (dbExist) {

		this.getReadableDatabase();

		try {

			copyDataBase();
			Log.e("da copy DB", "db da ton tain");
		} catch (IOException e) {

			throw new Error("Error copying database");

		}
		// }
	}

	private boolean checkDataBase() {

		@SuppressWarnings("unused")
		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + DB_NAME;
			// checkDB = SQLiteDatabase.openDatabase(myPath, null,
			// SQLiteDatabase.OPEN_READONLY);
			File dbfile = new File(myPath);
			return dbfile.exists();

		} catch (SQLiteException e) {
			Log.e("Loi ", "DatabaseNotFound ");
		}

		return false;
	}

	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getResources().getAssets()
				.open(DB_NAME);

		// Path to the just created empty db
		String outFileName = myContext.getDatabasePath(DB_NAME).toString();// DB_PATH
																			// +
																			// DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
		Log.e("copy data base", "da copy");
	}

	// @SuppressLint("NewApi")
	public boolean openDataBase() {

		String myPath = DB_PATH + DB_NAME;

		myDatabase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);
		// myDatabase.enableWriteAheadLogging();
		if (myDatabase != null) {
			return true;
		}
		return false;
	}

	public Cursor Excutequery(String SQL) {
		Cursor c = null;
		myDatabase = this.getReadableDatabase();
		c = myDatabase.rawQuery(SQL, null);
		return c;
	}

	public int ExecuteNonQuery(String sql) {
		myDatabase = this.getWritableDatabase();
		myDatabase.rawQuery(sql, null);
		return 1;
	}

	public boolean ExcuteUpdate(String sql, String DATABASE_TABLE,
			ContentValues values, String where, String[] agrs) {
		try {
			myDatabase = this.getWritableDatabase();
			myDatabase.beginTransaction();
			// i = this.getWritableDatabase().update(DATABASE_TABLE, values,
			// where,
			// null);
			// myDatabase.execSQL(sql);
			myDatabase.update(DATABASE_TABLE, values, where, null);
			myDatabase.setTransactionSuccessful();
			myDatabase.endTransaction();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		// TODO Auto-generated method stub

		// return i;
	}

	public boolean ExcuteInsert(String sql, String DATABASE_TABLE,
			ContentValues values) {

		myDatabase = this.getWritableDatabase();
		myDatabase.beginTransaction();
		myDatabase.insert(DATABASE_TABLE, null, values);
		myDatabase.setTransactionSuccessful();
		myDatabase.endTransaction();

		// myDatabase = this.getWritableDatabase();
		// myDatabase.beginTransaction();
		// myDatabase.execSQL(sql);
		// myDatabase.setTransactionSuccessful();
		// myDatabase.endTransaction();
		return true;
	}

	public void deleteTable(String tableName, String sql) {

		try {

			openDataBase();

			// delete version Table
			myDatabase.delete(tableName, sql, null);

			Log.w("TABLE NAMES", tableName);

			close();

		} catch (Exception sqle) {
			sqle.printStackTrace();
			return;
		}

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized void close() {
		if (myDatabase != null)
			myDatabase.close();
		super.close();
	}
}
