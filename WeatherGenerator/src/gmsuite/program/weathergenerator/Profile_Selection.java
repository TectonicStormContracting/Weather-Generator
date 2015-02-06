package gmsuite.program.weathergenerator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class Profile_Selection extends Activity {
	
	final static String EXTRA = "gmsuite.program.weathergenerator.ID";
	Button wen, load, exit, back;
	ListView ps;
	public static final String DBprefs ="DBprefs";
	public static final int done = 0;
	DBAdapterFLV2 myDB = new DBAdapterFLV2(this);
	String item = "";
	Context c = this;
	long id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile__selection);
		 wen = (Button)findViewById(R.id.newProfile);
		 load = (Button)findViewById(R.id.loadProfile);
		 exit = (Button)findViewById(R.id.exit1);
		 back = (Button)findViewById(R.id.back);
		 ps = (ListView)findViewById(R.id.profileSelect);
		
		 CreateSaves x = new CreateSaves(this);
		 myDB.open(); 
		 popListView();
		 
		 // listner takes the id of the selected profil and ships it to the third activity
		 ps.setOnItemClickListener(
					
					new android.widget.AdapterView.OnItemClickListener()
					{
					
					@Override
					public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
					{
						item += ((TextView)(view.findViewById(R.id.listviewtext3))).getText().toString();
						id = Long.valueOf(item);
						Intent bb = new Intent(c, Generator.class);
						startActivity(bb);
						bb.putExtra(EXTRA, id);
						exitPress(exit);
						
					}
					
					});
	}
	
	//takes to the new profile screen
	public void newPress(View v)
	{
		Intent intent = new Intent(this,New_Profile_Form.class);
		startActivity(intent);
		this.finish();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		myDB.close();
	}
	//populates the list view
	@SuppressWarnings("deprecation")// useful for small queries
	private void popListView() {
		Cursor cursor = myDB.getAllRows();
		startManagingCursor(cursor);
		//set up mapping
		String[] from = new String[]{DBAdapterFLV2.KEY_NAME, DBAdapterFLV2.KEY_ROWID};
		int[] to = new int[]{R.id.listviewtext2, R.id.listviewtext3};
		
		//create adapter for db ripping
		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.listview_view2, cursor, from, to);
		// set adapter for list view
		ps.setAdapter(cursorAdapter);
	}

	//hides the buttons and brings forth the list view
	public void loadPress(View v)
	{
		
		wen.setVisibility(View.GONE);
		load.setVisibility(View.GONE);
		exit.setVisibility(View.GONE);
		back.setVisibility(View.VISIBLE);
		ps.setVisibility(View.VISIBLE);
	}
	//kills app
	public void exitPress(View v)
	{
		this.finish();
	}
	//hides the list view and itself brings back other buttons
	public void backPress(View v)
	{
		ps.setVisibility(View.GONE);
		back.setVisibility(View.GONE);
		wen.setVisibility(View.VISIBLE);
		load.setVisibility(View.VISIBLE);
		exit.setVisibility(View.VISIBLE);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile__selection, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
