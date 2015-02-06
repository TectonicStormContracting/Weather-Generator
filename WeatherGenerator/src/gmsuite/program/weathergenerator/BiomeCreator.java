package gmsuite.program.weathergenerator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class BiomeCreator {

	/////////////////////////////////////////////////////////////////////
	//	Constants & Data
	/////////////////////////////////////////////////////////////////////
	// For logging:
	private static final String TAG = "DBAdapter";
	
	// DB Fields
	public static final String KEY_ROWID = "_id";
	public static final int COL_ROWID = 0;
	public static final String KEY_DESCRIPTION = "Discription";
	public static final String KEY_EFFECTS = "Effects";
	public static final int COL_DESCRIPTION = 1;
	public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_DESCRIPTION, 
		KEY_EFFECTS};
	
	// DB info: it's name, and the tables.
	public static final String DATABASE_NAME = "BIOME";
	public static final String DATABASE_TABLE1 = "SPRING";
	public static final String DATABASE_TABLE2 = "SUMMER";
	public static final String DATABASE_TABLE3 = "FALL";
	public static final String DATABASE_TABLE4 = "WINTER";
	//  DB version.
	public static final int DATABASE_VERSION = 1;	
	//create statements
	private static final String DATABASE_CREATE_SQL1 = 
			"create table " + DATABASE_TABLE1 
			+ " (" + KEY_ROWID + " integer primary key, "
			+ KEY_DESCRIPTION + " text not null, "
			+ KEY_EFFECTS + " text not null"
			+ ");";
	
	private static final String DATABASE_CREATE_SQL2 = 
			"create table " + DATABASE_TABLE2 
			+ " (" + KEY_ROWID + " integer primary key, "
			+ KEY_DESCRIPTION + " text not null, "
			+ KEY_EFFECTS + " text not null"
			+ ");";
	
	private static final String DATABASE_CREATE_SQL3 = 
			"create table " + DATABASE_TABLE3 
			+ " (" + KEY_ROWID + " integer primary key, "
			+ KEY_DESCRIPTION + " text not null, "
			+ KEY_EFFECTS + " text not null"
			+ ");";
	
	private static final String DATABASE_CREATE_SQL4 = 
			"create table " + DATABASE_TABLE4 
			+ " (" + KEY_ROWID + " integer primary key, "
			+ KEY_DESCRIPTION + " text not null, "
			+ KEY_EFFECTS + " text not null"
			+ ");";
	
	private final Context context;
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	/////////////////////////////////////////////////////////////////////
	//	Public methods:
	/////////////////////////////////////////////////////////////////////
	
	//construtor for accessing
	public BiomeCreator(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}
	
	//constructor for switching
	public BiomeCreator(Context ctx, String biome) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
		open();
		deleteAll(DATABASE_TABLE1);
		deleteAll(DATABASE_TABLE2); //deletes all previous entries
		deleteAll(DATABASE_TABLE3);
		deleteAll(DATABASE_TABLE4);
		switch(biome)				//checks biome then creates specific data for each
		{
			case "Temperate_Plains":
				{
					popTPlains();
					break;
				}
			case "Temperate_Hills":
				{
					popTHills();
					break;
				}
			case "Tundra":
				{
					popTundra();
					break;
				}
		}
	}
	
	//creates entries for temperate plains biome
	public void popTPlains()
	{
		//create spring
		insertRow(1, DATABASE_TABLE1, 
				"It is sunny. Temp 14 degrees celsius. Clear blue skys.", 
				"No Effects");
		insertRow(2, DATABASE_TABLE1, 
				"It is sunny. Temp 16 degrees celsius. Clear blue skys "
				+ "with a light breeze from the south east.", 
				"No Effects");
		insertRow(3, DATABASE_TABLE1, 
				"It is sunny. Temp 14 degrees celsius. Clouds are coming in from the east"
				+ "there is a moderate eastern wind", 
				"Projectile attacks receive a -1 penalty. Thrown weapons unaffected.");
		insertRow(4, DATABASE_TABLE1, 
				"It is cloudy. Temp 11 degrees. It is lightly raining with strong winds from"
				+ "the east", 
				"Projectile attacks receive a -2 penalty. Thrown weapons recieve a -1 penalty.");
		insertRow(5, DATABASE_TABLE1, 
				"It is cloudy. Temp 11 degrees. It is raining moderately with strong "
				+ "winds from the east", 
				"Projectile attacks receive a -2 penalty. Thrown weapons recieve a -1 penalty."
				+" Character movement is reduced to 3/4 after the first hour.");
		insertRow(6, DATABASE_TABLE1, 
				"It is cloudy. Temp 9 degrees. It is raining heavily with strong "
				+ "winds from the east", 
				"Projectile attacks receive a -3 penalty. Thrown weapons recieve a -1 penalty."
				+" Character movement is reduced to 1/2 after the first hour.");
		insertRow(7, DATABASE_TABLE1, 
				"It is cloudy. Temp 9 degrees. It is raining heavily with strong "
				+ "winds from the east and west for varying durations.", 
				"Projectile attacks receive a -4 penalty. Thrown weapons recieve a -2 penalty."
				+" Character movement is reduced to 1/2 after the first hour.");
		insertRow(8, DATABASE_TABLE1, 
				"It is cloudy. Temp 9 degrees. It is raining very heavily with very strong "
				+ "winds from the east and west for variying durations."
				+ " Lightning and thunder is sporradic.", 
				"Projectile attacks receive a -6 penalty. Thrown weapons recieve a -4 penalty."
				+" Character movement is reduced to 1/4 after the first hour."
				+ "There is a 5% chance every hour that a character will be struck by lightning."
				+ "The chance increases to 10% if the character is wearing metal armor.");
		insertRow(9, DATABASE_TABLE1, 
				"It is cloudy. Temp 7 degrees. It is raining extreamly heavily with very strong "
				+ "winds from the east and west for variying durations."
				+ " Lightning and thunder is constant.", 
				"Projectile attacks receive a -8 penalty. Thrown weapons recieve a -6 penalty."
				+" Character movement is reduced to 1/8 after the first hour."
				+ "There is a 10% chance every hour that a character will be struck by lightning."
				+ "The chance increases to 25% if the character is wearing metal armor.");
		insertRow(10, DATABASE_TABLE1, 
				"It is cloudy. Temp 9 degrees. It is raining extreamly heavily with extremly strong "
				+ "winds from the east and west for variying durations."
				+ " Lightning and thunder is constant. There is a 10% chance every hour"
				+ "that a tornado will touch down within 1km of the character", 
				"Projectile attacks receive a -10 penalty. Thrown weapons recieve a -8 penalty."
				+" Character movement is reduced to 1/10 after the first hour."
				+ "There is a 10% chance every hour that a character will be struck by lightning."
				+ "The chance increases to 25% if the character is wearing metal armor.");
		
		// create summer
		
		insertRow(1, DATABASE_TABLE2, 
				"It is sunny. Temp 22 degrees. The sky is clear.", 
				"No effects.");
		insertRow(2, DATABASE_TABLE2, 
				"It is sunny. Temp 25 degrees. The sky is clear with wisps of clouds "
				+ "drifting through it", 
				"No effects.");
		insertRow(3, DATABASE_TABLE2, 
				"It is sunny. Temp 30 degrees. The sky is completly clear.", 
				"Characters in heavy armor will fatigue 50% faster.");
		insertRow(4, DATABASE_TABLE2, 
				"It is sunny. Temp 25 degrees. The sky is clear with wisps of clouds."
				+ "There is a moderate wind blowing from the south.", 
				"Projectile attacks receive a -1 penalty. Thrown weapons unaffected.");
		insertRow(5, DATABASE_TABLE2, 
				"It is Sunny. Temp 32 degrees. The sky is clear with wisps of clouds."
				+ "There is a moderate wind blowing from the south.", 
				"Projectile attacks receive a -1 penalty. Thrown weapons unaffected."
				+ "Characters in heavy armor will fatigue 50% faster.");
		insertRow(6, DATABASE_TABLE2, 
				"It is Sunny. Temp 35 degrees. The sky is clear with wisps of clouds."
				+ "There is a moderate wind blowing from the south.", 
				"Projectile attacks receive a -1 penalty. Thrown weapons unaffected."
				+ "Characters in medium or heavy armor will fatigue 50% faster.");
		insertRow(7, DATABASE_TABLE2, 
				"It is Sunny. Temp 38 degrees. The sky is clear with wisps of clouds."
				+ "There is a strong wind blowing from the south.", 
				"Projectile attacks receive a -2 penalty. Thrown weapons receive a -1 penalty."
				+ "Characters in medium or heavy armor will fatigue 50% faster.");
		insertRow(8, DATABASE_TABLE2, 
				"It is Sunny. Temp 43 degrees. The sky is has large clouds moving over head."
				+ "There is a strong wind blowing from the south.", 
				"Projectile attacks receive a -1 penalty. Thrown weapons receive a -1 penalty."
				+ "Characters in light or medium armor will fatigue 50% faster."
				+ "Characters in heavy armor will develop hyperthermia within an hour.");
		insertRow(9, DATABASE_TABLE2, 
				"It is Overcast. Temp 38 degrees. The sky is has large clouds moving over head."
				+ "There is a strong wind blowing from the south."
				+ "A dry lightning storm has started", 
				"Projectile attacks receive a -2 penalty. Thrown weapons receive a -1 penalty."
				+ "Characters in medium or heavy armor will fatigue 50% faster."
				+ "There is a 5% chance every hour that a character will be struck by lightning."
				+ "This chance increases to 10% if in metal armor."
				+ "There is a 2% chance that the lightning sparks a brush fire within 1 km of the"
				+ "characters.");
		insertRow(10, DATABASE_TABLE2, 
				"It is Overcast. Temp 38 degrees. The sky is has large clouds moving over head."
				+ "There is a strong wind blowing from the south."
				+ "A dry lightning storm is raging.", 
				"Characters in medium or heavy armor will fatigue 50% faster."
				+ "There is a 10% chance every hour that a character will be struck by lightning."
				+ "This chance increases to 25% if in metal armor."
				+ "There is a 7% chance that the lightning sparks a brush fire within 1 km of the"
				+ "characters.");
		
		//create fall
				insertRow(1, DATABASE_TABLE3, 
						"value f1", 
						"value f1");
				insertRow(2, DATABASE_TABLE3, 
						"value f2", 
						"value f2");
				insertRow(3, DATABASE_TABLE3, 
						"value f3", 
						"value f3");
				insertRow(4, DATABASE_TABLE3, 
						"value f4", 
						"value f4");
				insertRow(5, DATABASE_TABLE3, 
						"value f5", 
						"value f5");
				insertRow(6, DATABASE_TABLE3, 
						"value f6", 
						"value f6");
				insertRow(7, DATABASE_TABLE3, 
						"value f7", 
						"value f7");
				insertRow(8, DATABASE_TABLE3, 
						"value f8", 
						"value f8");
				insertRow(9, DATABASE_TABLE3, 
						"value f9", 
						"value f9");
				insertRow(10, DATABASE_TABLE3, 
						"value f10", 
						"value f10");
				
				//create winter
				insertRow(1, DATABASE_TABLE4, 
						"value w1", 
						"value w1");
				insertRow(2, DATABASE_TABLE4, 
						"value w2", 
						"value w2");
				insertRow(3, DATABASE_TABLE4, 
						"value w3", 
						"value w3");
				insertRow(4, DATABASE_TABLE4, 
						"value w4", 
						"value w4");
				insertRow(5, DATABASE_TABLE4, 
						"value w5", 
						"value w5");
				insertRow(6, DATABASE_TABLE4, 
						"value w6", 
						"value w6");
				insertRow(7, DATABASE_TABLE4, 
						"value w7", 
						"value w7");
				insertRow(8, DATABASE_TABLE4, 
						"value w8", 
						"value w8");
				insertRow(9, DATABASE_TABLE4, 
						"value w9", 
						"value w9");
				insertRow(10, DATABASE_TABLE4, 
						"value w10", 
						"value w10");
	}
	
	//creates entries for temperate hill biome
	public void popTHills()
	{
		///create spring
		insertRow(1, DATABASE_TABLE1, 
				"value s1", 
				"value s1");
		insertRow(2, DATABASE_TABLE1, 
				"value s2", 
				"value s2");
		insertRow(3, DATABASE_TABLE1, 
				"value s3", 
				"value s3");
		insertRow(4, DATABASE_TABLE1, 
				"value s4", 
				"value s4");
		insertRow(5, DATABASE_TABLE1, 
				"value s5", 
				"value s5");
		insertRow(6, DATABASE_TABLE1, 
				"value s6", 
				"value s6");
		insertRow(7, DATABASE_TABLE1, 
				"value s7", 
				"value s7");
		insertRow(8, DATABASE_TABLE1, 
				"value s8", 
				"value s8");
		insertRow(9, DATABASE_TABLE1, 
				"value s9", 
				"value s9");
		insertRow(10, DATABASE_TABLE1, 
				"value s10", 
				"value s10");
				
				// create summer
		insertRow(1, DATABASE_TABLE2, 
				"value x1", 
				"value x1");
		insertRow(2, DATABASE_TABLE2, 
				"value x2", 
				"value x2");
		insertRow(3, DATABASE_TABLE2, 
				"value x3", 
				"value x3");
		insertRow(4, DATABASE_TABLE2, 
				"value x4", 
				"value x4");
		insertRow(5, DATABASE_TABLE2, 
				"value x5", 
				"value x5");
		insertRow(6, DATABASE_TABLE2, 
				"value x6", 
				"value x6");
		insertRow(7, DATABASE_TABLE2, 
				"value x7", 
				"value x7");
		insertRow(8, DATABASE_TABLE2, 
				"value x8", 
				"value x8");
		insertRow(9, DATABASE_TABLE2, 
				"value x9", 
				"value x9");
		insertRow(10, DATABASE_TABLE2, 
				"value x10", 
				"value x10");
				
		//create fall
		insertRow(1, DATABASE_TABLE3, 
				"value f1", 
				"value f1");
		insertRow(2, DATABASE_TABLE3, 
				"value f2", 
				"value f2");
		insertRow(3, DATABASE_TABLE3, 
				"value f3", 
				"value f3");
		insertRow(4, DATABASE_TABLE3, 
				"value f4", 
				"value f4");
		insertRow(5, DATABASE_TABLE3, 
				"value f5", 
				"value f5");
		insertRow(6, DATABASE_TABLE3, 
				"value f6", 
				"value f6");
		insertRow(7, DATABASE_TABLE3, 
				"value f7", 
				"value f7");
		insertRow(8, DATABASE_TABLE3, 
				"value f8", 
				"value f8");
		insertRow(9, DATABASE_TABLE3, 
				"value f9", 
				"value f9");
		insertRow(10, DATABASE_TABLE3, 
				"value f10", 
				"value f10");
		
		//create winter
		insertRow(1, DATABASE_TABLE4, 
				"value w1", 
				"value w1");
		insertRow(2, DATABASE_TABLE4, 
				"value w2", 
				"value w2");
		insertRow(3, DATABASE_TABLE4, 
				"value w3", 
				"value w3");
		insertRow(4, DATABASE_TABLE4, 
				"value w4", 
				"value w4");
		insertRow(5, DATABASE_TABLE4, 
				"value w5", 
				"value w5");
		insertRow(6, DATABASE_TABLE4, 
				"value w6", 
				"value w6");
		insertRow(7, DATABASE_TABLE4, 
				"value w7", 
				"value w7");
		insertRow(8, DATABASE_TABLE4, 
				"value w8", 
				"value w8");
		insertRow(9, DATABASE_TABLE4, 
				"value w9", 
				"value w9");
		insertRow(10, DATABASE_TABLE4, 
				"value w10", 
				"value w10");
	}
	
	// creates entries for tundra biome
	public void popTundra()
	{
		//create spring
		insertRow(1, DATABASE_TABLE1, 
				"value s1", 
				"value s1");
		insertRow(2, DATABASE_TABLE1, 
				"value s2", 
				"value s2");
		insertRow(3, DATABASE_TABLE1, 
				"value s3", 
				"value s3");
		insertRow(4, DATABASE_TABLE1, 
				"value s4", 
				"value s4");
		insertRow(5, DATABASE_TABLE1, 
				"value s5", 
				"value s5");
		insertRow(6, DATABASE_TABLE1, 
				"value s6", 
				"value s6");
		insertRow(7, DATABASE_TABLE1, 
				"value s7", 
				"value s7");
		insertRow(8, DATABASE_TABLE1, 
				"value s8", 
				"value s8");
		insertRow(9, DATABASE_TABLE1, 
				"value s9", 
				"value s9");
		insertRow(10, DATABASE_TABLE1, 
				"value s10", 
				"value s10");
				
				// create summer
		insertRow(1, DATABASE_TABLE2, 
				"value x1", 
				"value x1");
		insertRow(2, DATABASE_TABLE2, 
				"value x2", 
				"value x2");
		insertRow(3, DATABASE_TABLE2, 
				"value x3", 
				"value x3");
		insertRow(4, DATABASE_TABLE2, 
				"value x4", 
				"value x4");
		insertRow(5, DATABASE_TABLE2, 
				"value x5", 
				"value x5");
		insertRow(6, DATABASE_TABLE2, 
				"value x6", 
				"value x6");
		insertRow(7, DATABASE_TABLE2, 
				"value x7", 
				"value x7");
		insertRow(8, DATABASE_TABLE2, 
				"value x8", 
				"value x8");
		insertRow(9, DATABASE_TABLE2, 
				"value x9", 
				"value x9");
		insertRow(10, DATABASE_TABLE2, 
				"value x10", 
				"value x10");
				
		//create fall
		insertRow(1, DATABASE_TABLE3, 
				"value f1", 
				"value f1");
		insertRow(2, DATABASE_TABLE3, 
				"value f2", 
				"value f2");
		insertRow(3, DATABASE_TABLE3, 
				"value f3", 
				"value f3");
		insertRow(4, DATABASE_TABLE3, 
				"value f4", 
				"value f4");
		insertRow(5, DATABASE_TABLE3, 
				"value f5", 
				"value f5");
		insertRow(6, DATABASE_TABLE3, 
				"value f6", 
				"value f6");
		insertRow(7, DATABASE_TABLE3, 
				"value f7", 
				"value f7");
		insertRow(8, DATABASE_TABLE3, 
				"value f8", 
				"value f8");
		insertRow(9, DATABASE_TABLE3, 
				"value f9", 
				"value f9");
		insertRow(10, DATABASE_TABLE3, 
				"value f10", 
				"value f10");
		
		//create winter
		insertRow(1, DATABASE_TABLE4, 
				"value w1", 
				"value w1");
		insertRow(2, DATABASE_TABLE4, 
				"value w2", 
				"value w2");
		insertRow(3, DATABASE_TABLE4, 
				"value w3", 
				"value w3");
		insertRow(4, DATABASE_TABLE4, 
				"value w4", 
				"value w4");
		insertRow(5, DATABASE_TABLE4, 
				"value w5", 
				"value w5");
		insertRow(6, DATABASE_TABLE4, 
				"value w6", 
				"value w6");
		insertRow(7, DATABASE_TABLE4, 
				"value w7", 
				"value w7");
		insertRow(8, DATABASE_TABLE4, 
				"value w8", 
				"value w8");
		insertRow(9, DATABASE_TABLE4, 
				"value w9", 
				"value w9");
		insertRow(10, DATABASE_TABLE4, 
				"value w10", 
				"value w10");
	}
	
	// Open the database connection.
	public BiomeCreator open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}
	
	// Close the database connection.
	public void close() {
		myDBHelper.close();
	}
	
	// Add a new set of values to the database.
	public long insertRow(long id, String table, String des, String effect) {
		// Create row's data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ROWID, id);
		initialValues.put(KEY_DESCRIPTION, des);
		initialValues.put(KEY_EFFECTS, effect);
		// Insert it into the database.
		return db.insert(table, null, initialValues);
	}
	
	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRow(long rowId, String table) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(table, where, null) != 0;
	}
	
	// Delete all rows in a table
	public void deleteAll(String table) {
		Cursor c = getAllRows(table);
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRow(c.getLong((int) rowId), table);				
			} while (c.moveToNext());
		}
		c.close();
	}
	
	//Queries all rows in a table
	public Cursor getAllRows(String table) {
		String where = null;
		Cursor c = 	db.query(true, table, ALL_KEYS, 
							where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Get a specific row (by rowId) and season for the table
	public String getEffect(long rowId, int season) {
		String where = KEY_ROWID + "=" + rowId;
		String s = "";
		switch(season)
			{
				case 0:
					{ Cursor c = db.query(true, DATABASE_TABLE1, new String[] {KEY_EFFECTS}, 
									where, null, null, null, null, null);
					 c.moveToFirst();
					 s = c.getString(c.getColumnIndex(KEY_EFFECTS));
					break;
					}
				case 1:
					{ Cursor c = db.query(true, DATABASE_TABLE2, new String[] {KEY_EFFECTS}, 
									where, null, null, null, null, null);
					 c.moveToFirst();
					 s = c.getString(c.getColumnIndex(KEY_EFFECTS));
					break;
					}
				case 2:
					{ Cursor c = db.query(true, DATABASE_TABLE3, new String[] {KEY_EFFECTS}, 
									where, null, null, null, null, null);
					 c.moveToFirst();
					 s = c.getString(c.getColumnIndex(KEY_EFFECTS));
					break;
					}
				case 3:
					{ Cursor c = db.query(true, DATABASE_TABLE4, new String[] {KEY_EFFECTS}, 
									where, null, null, null, null, null);
					 c.moveToFirst();
					 s = c.getString(c.getColumnIndex(KEY_EFFECTS));
					break;
					}
			}
			return s;
		}
		
	//Quieries a specific value in this case the decription at row_id and season by its array value
	public String getDescription(long rowId, int season) {
		String where = KEY_ROWID + "=" + rowId;
		String s = "";
		switch(season)
			{
				case 0:
					{ Cursor c = db.query(true, DATABASE_TABLE1, new String[] {KEY_DESCRIPTION}, 
									where, null, null, null, null, null);
					 c.moveToFirst();
					 s = c.getString(c.getColumnIndex(KEY_DESCRIPTION));
					break;
					}
				case 1:
				{ Cursor c = db.query(true, DATABASE_TABLE2, new String[] {KEY_DESCRIPTION}, 
								where, null, null, null, null, null);
				 c.moveToFirst();
				 s = c.getString(c.getColumnIndex(KEY_DESCRIPTION));
				break;
				}
				case 2:
				{ Cursor c = db.query(true, DATABASE_TABLE3, new String[] {KEY_DESCRIPTION}, 
								where, null, null, null, null, null);
				 c.moveToFirst();
				 s = c.getString(c.getColumnIndex(KEY_DESCRIPTION));
				break;
				}
				case 3:
				{ Cursor c = db.query(true, DATABASE_TABLE4, new String[] {KEY_DESCRIPTION}, 
								where, null, null, null, null, null);
				 c.moveToFirst();
				 s = c.getString(c.getColumnIndex(KEY_DESCRIPTION));
				break;
				}
			}
			return s;
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
			_db.execSQL(DATABASE_CREATE_SQL1);
			_db.execSQL(DATABASE_CREATE_SQL2);	
			_db.execSQL(DATABASE_CREATE_SQL3);	
			_db.execSQL(DATABASE_CREATE_SQL4);	
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data!");
			
			// Destroy old database:
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE1);
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE2);
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE3);
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE4);
			
			// Recreate new database:
			onCreate(_db);
		}
	}
}

