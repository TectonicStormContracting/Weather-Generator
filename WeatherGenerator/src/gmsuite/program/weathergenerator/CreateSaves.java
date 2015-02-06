package gmsuite.program.weathergenerator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CreateSaves {

	/////////////////////////////////////////////////////////////////////
	//	Constants & Data
	/////////////////////////////////////////////////////////////////////
	// For logging:
	private static final String TAG = "DBAdapter";
	
	// DB Fields
	public static final String KEY_ROWID = "_id";
	public static final int COL_ROWID = 0;
	public static final String KEY_NAME = "name";
	public static final String KEY_HOURS = "hour";
	public static final String KEY_DAYS = "day";
	public static final String KEY_MONTH = "month";
	public static final String KEY_SEASON = "season";
	public static final String KEY_BIOME = "biome";
	public static final String KEY_VALUE = "value";
	public static final int COL_NAME = 1;
	public static final int COL_HOURS = 2;
	public static final int COL_DAYS = 3;
	public static final int COL_MONTH = 4;
	public static final int COL_SEASON = 5;
	public static final int COL_BIOME = 6;
	public static final int COL_VALUE = 7;
	public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_NAME, KEY_HOURS, 
		KEY_DAYS, KEY_MONTH, KEY_SEASON, KEY_BIOME, KEY_VALUE};
	
	// DB info: it's name, and the table
	public static final String DATABASE_NAME = "save";
	public static final String DATABASE_TABLE = "oogabooga";
	//DB version
	public static final int DATABASE_VERSION = 1;	
	
	private static final String DATABASE_CREATE_SQL = 
			"create table " + DATABASE_TABLE 
			+ " (" + KEY_ROWID + " integer primary key autoincrement, "
			+ KEY_NAME + " text not null, "
			+ KEY_HOURS + " integer not null, "
			+ KEY_DAYS + " integer not null, "
			+ KEY_MONTH + " integer not null, "
			+ KEY_SEASON + " integer not null, "
			+ KEY_BIOME + " text not null, "
			+ KEY_VALUE + " integer not null"
			+ ");";
	
	private final Context context;
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	/////////////////////////////////////////////////////////////////////
	//	Public methods:
	/////////////////////////////////////////////////////////////////////
	
	public CreateSaves(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}
	
	// Open the database connection.
	public CreateSaves open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}
	
	// Close the database connection.
	public void close() {
		myDBHelper.close();
	}
	
	// Add a new set of values to the database.
	public long insertRow(String name, int hours, int days, int months, int seasons, 
			String biomes, int value) {
		
		// Create row's data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_HOURS, hours);
		initialValues.put(KEY_DAYS, days);
		initialValues.put(KEY_MONTH, months);
		initialValues.put(KEY_SEASON, seasons);
		initialValues.put(KEY_BIOME, biomes);
		initialValues.put(KEY_VALUE, value);
		
		// Insert it into the database.
		return db.insert(DATABASE_TABLE, null, initialValues);
	}
	
	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRow(Long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(DATABASE_TABLE, where, null) != 0;
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
	
	public String getName(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = 	db.query(true, DATABASE_TABLE, new String[]{KEY_NAME}, 
						where, null, null, null, null, null);
		c.moveToFirst();
		
		
		String h = c.getString(c.getColumnIndex(KEY_NAME));
		return h;
	}
	
	// Get a specific data item (by rowId)
	public int getHour(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = 	db.query(true, DATABASE_TABLE, new String[]{KEY_HOURS}, 
						where, null, null, null, null, null);
		c.moveToFirst();
		
		
		int h = c.getInt(c.getColumnIndex(KEY_HOURS));
		return h;
	}
	// Get a specific data item (by rowId)
	public int getDay(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = 	db.query(true, DATABASE_TABLE, new String[]{KEY_DAYS}, 
						where, null, null, null, null, null);
		c.moveToFirst();
		
		
		int h = c.getInt(c.getColumnIndex(KEY_DAYS));
		return h;
	}
	// Get a specific data item (by rowId)
	public int getMonth(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = 	db.query(true, DATABASE_TABLE, new String[]{KEY_MONTH}, 
						where, null, null, null, null, null);
		c.moveToFirst();
		
		
		int h = c.getInt(c.getColumnIndex(KEY_MONTH));
		return h;
	}
	// Get a specific data item (by rowId)
	public int getSeason(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = 	db.query(true, DATABASE_TABLE, new String[]{KEY_SEASON}, 
						where, null, null, null, null, null);
		c.moveToFirst();
		
		
		int h = c.getInt(c.getColumnIndex(KEY_SEASON));
		return h;
	}
	// Get a specific data item (by rowId)
	public long getValue(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = 	db.query(true, DATABASE_TABLE, new String[]{KEY_VALUE}, 
						where, null, null, null, null, null);
		c.moveToFirst();
		
		
		int h = c.getInt(c.getColumnIndex(KEY_VALUE));
		return h;
	}
	// Get a specific data item (by rowId)
	public String getBiome(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = 	db.query(true, DATABASE_TABLE, new String[]{KEY_BIOME}, 
						where, null, null, null, null, null);
		c.moveToFirst();
		
		
		String h = c.getString(c.getColumnIndex(KEY_BIOME));
		return h;
	}
	
	// updates the values of a row
	public boolean updateRow(Long rowId, String name, int hours, int days, int months, int seasons, 
			String biomes, int value) {
		String where = KEY_ROWID + "=" + rowId;

		// Create row's data:
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_NAME, name);
		newValues.put(KEY_HOURS, hours);
		newValues.put(KEY_DAYS, days);
		newValues.put(KEY_MONTH, months);
		newValues.put(KEY_SEASON, seasons);
		newValues.put(KEY_BIOME, biomes);
		newValues.put(KEY_VALUE, value);
		
		// Insert it into the database.
		return db.update(DATABASE_TABLE, newValues, where, null) != 0;
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
