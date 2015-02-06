package gmsuite.program.weathergenerator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBhelper extends SQLiteOpenHelper{
	
	public SQLiteDatabase DB;
	public String DBPath;
	public static String DBName = "select";
	public static final int version = '1';
	public static String tableName = "stuff";
	

	public DBhelper(Context context) {
		super(context, DBName, null, version);
		String packageName = context.getPackageName();
     	DBPath = context.getDatabasePath(DBName).toString();
		createDatabase();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

	private void createDatabase() {
		boolean dbExists = checkDbExists();
		
		if (dbExists) {
			// do nothing
		} else {
			Log.w("aaaa","doesn't exist");
		}
		
		
	}

	private boolean checkDbExists() {
		SQLiteDatabase checkDB = null;

		try {
			String myPath = DBPath + DBName;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {

			// database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}
}