package gmsuite.program.weathergenerator;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
//This class no longer is viable

public class DBAdapterFLV2 {

	/////////////////////////////////////////////////////////////////////
	//	Constants & Data
	/////////////////////////////////////////////////////////////////////
	private static final String TAG = "DBAdapterFLV";
	
	// DB Fields
	public static final String KEY_ROWID = "_id";
	public static final int COL_ROWID = 0;	
	public static final String KEY_NAME = "name";
	public static final int COL_NAME = 1;
	public static final String KEY_HOUR = "hour";
	public static final int COL_HOUR = 2;
	public static final String KEY_DAY = "day";
	public static final int COL_DAY = 3;
	public static final String KEY_MONTH = "month";
	public static final int COL_MONTH = 4;
	public static final String KEY_SEASON = "season";
	public static final int COL_SEASON = 5;
	public static final String KEY_BIOME = "biome";
	public static final int COL_BIOME = 6;
	public static final String KEY_VALUE = "value";
	public static final int COL_VALUE = 7;
	public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_NAME,
		KEY_HOUR, KEY_DAY, KEY_MONTH, KEY_SEASON, KEY_BIOME, KEY_VALUE};
	public static final String[] NAME = new String[] {KEY_NAME};
	
	public static final String DATABASE_NAME = "save";
	public static final String DATABASE_TABLE = "oogabooga";
	public static final int DATABASE_VERSION = 1;	
	
	private static final String DATABASE_CREATE_SQL = 
			"create table " + DATABASE_TABLE 
			+ " (" + KEY_ROWID + " integer primary key autoincrement, "
			+ KEY_NAME + " text not null,"
			+ KEY_HOUR + " integer not null,"
			+ KEY_DAY + " integer not null,"
			+ KEY_MONTH + " integer not null,"
			+ KEY_SEASON + " integer not null,"
			+ KEY_BIOME + " text not null,"
			+ KEY_VALUE + " integer not null"
			+ ");";
	
	private final Context context;
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	/////////////////////////////////////////////////////////////////////
	//	Public methods:
	/////////////////////////////////////////////////////////////////////
	
	public DBAdapterFLV2(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}
	
	// Open the database connection.
	public DBAdapterFLV2 open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}
	
	// Close the database connection.
	public void close() {
		myDBHelper.close();
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

	// Get a specific row (by rowId)
	public Cursor getRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
						where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
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
			try{
			_db.execSQL(DATABASE_CREATE_SQL);
			}
			catch(Exception e)
			{
				Log.w("damn","this works"+ e.toString());
			}
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
