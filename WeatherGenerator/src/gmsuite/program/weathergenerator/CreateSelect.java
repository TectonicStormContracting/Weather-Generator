package gmsuite.program.weathergenerator;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CreateSelect {

	/////////////////////////////////////////////////////////////////////
	//	Constants & Data
	/////////////////////////////////////////////////////////////////////
	// For logging:
	private static final String TAG = "DBAdapter";
	
	// DB Fields
	public static final String KEY_ROWID = "_id";
	public static final int COL_ROWID = 0;
	public static final String KEY_BIOMES = "Biomes";
	public static final int COL_BIOMES = 1;
	public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_BIOMES};
	
	// DB info: it's name, and the tables.
	public static final String DATABASE_NAME = "select";
	public static final String DATABASE_TABLE = "stuff";
	//DB version.
	public static final int DATABASE_VERSION = 1;	
	
	private static final String DATABASE_CREATE_SQL = 
			"create table " + DATABASE_TABLE 
			+ " (" + KEY_ROWID + " integer primary key autoincrement, "
			+ KEY_BIOMES + " text not null"
			+ ");";
	
	private final Context context;
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	/////////////////////////////////////////////////////////////////////
	//	Public methods:
	/////////////////////////////////////////////////////////////////////
	
	public CreateSelect(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
		open();
		deleteAll();
		insertRow("Temperate_Plains");
		insertRow("Temperate_Hills");
		insertRow("Tundra");
		close();
	}
	
	// Open the database connection.
	public CreateSelect open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}
	
	// Close the database connection.
	public void close() {
		myDBHelper.close();
	}
	
	// Add a new set of values to the database.
	public long insertRow(String name) {

		// Create row's data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_BIOMES, name);
		
		// Insert it into the database.
		return db.insert(DATABASE_TABLE, null, initialValues);
	}
	
	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(DATABASE_TABLE, where, null) != 0;
	}
	 //deletes all rows
	public void deleteAll() {
		Cursor c = getAllRows();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRow(c.getLong((int) rowId));				
			} while (c.moveToNext());
		}
		c.close();
	}
	
	// Return all data in the database.
	public Cursor getAllRows() {
		String where = null;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
							where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			
		}
		return c;
	}
	
	//querys the names and returns them for a spinner list
	public List<String> getAllNames() {
		List<String> names = new ArrayList<String>();
		
		String selectQuery = "SELECT  * FROM " + DATABASE_TABLE;
		Cursor c = db.rawQuery(selectQuery, null);
        
		if (c != null)
		{		
		
			if (c.moveToFirst())
			{	
				do 
				{
					names.add(c.getString(1));
				}
				while (c.moveToNext());
			}
		}
		return names;
	}
	
	/////////////////////////////////////////////////////////////////////
	//	Private Helper Classes:
	/////////////////////////////////////////////////////////////////////
	
	/**
	 * Private class which handles database creation and upgrading.
	 * Used to handle low-level database access.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE_SQL);			
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data!");
			
			// Destroy old database:
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			
			// Recreate new database:
			onCreate(_db);
		}
	}
}
