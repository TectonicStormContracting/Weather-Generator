package gmsuite.program.weathergenerator;

import java.util.ArrayList;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class ListPopulator extends ListActivity {
	
	public  ListAdapter coocoo;
	private ArrayList<String> results = new ArrayList<String>();
	private String tableName = "stuff";
	private SQLiteDatabase newDB;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openAndQueryDatabase();
        
        
		ArrayAdapter<String> crazy = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, results);
		
		coocoo = crazy;
        
    }
	
	private void openAndQueryDatabase() {
		try {
			DBhelper dbHelper = new DBhelper(this);
			newDB = dbHelper.getWritableDatabase();
			Cursor c = newDB.rawQuery("SELECT Biome FROM " +
	    			tableName +
	    			null, null, null);

	    	if (c != null ) {
	    		if  (c.moveToFirst()) {
	    			do {
	    				String Biome = c.getString(c.getColumnIndex("Biome"));
	    				results.add(Biome);
	    			}while (c.moveToNext());
	    		} 
	    	}			
		} catch (SQLiteException se ) {
        	Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        } finally {
        	if (newDB != null) 
        		newDB.execSQL("DELETE FROM " + tableName);
        		newDB.close();
        }

	}
    
}