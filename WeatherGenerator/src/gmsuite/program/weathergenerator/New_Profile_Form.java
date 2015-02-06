package gmsuite.program.weathergenerator;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class New_Profile_Form extends Activity {
	
	final static String EXTRA = "gmsuite.program.weathergenerator.ID";
	EditText name;
	Button savProf, restart1, shop1;
	ListView biome;
	RadioButton mild1, poor1, very_poor1, dangerous1, deadly1;
	Spinner hour1, day1, month1, season1;
	DBAdapterFLV myDB = new DBAdapterFLV(this);
	String item = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new__profile__form);
		 savProf = (Button)findViewById(R.id.createProfile);
		 restart1 = (Button)findViewById(R.id.restart);
		 shop1 = (Button)findViewById(R.id.shop);
		 biome = (ListView)findViewById(R.id.biomeSelect);
		 mild1 = (RadioButton)findViewById(R.id.mild);
		 poor1 = (RadioButton)findViewById(R.id.poor);
		 very_poor1 = (RadioButton)findViewById(R.id.very_poor);
		 dangerous1 = (RadioButton)findViewById(R.id.dangerous);
		 deadly1 = (RadioButton)findViewById(R.id.deadly);
		 hour1 = (Spinner)findViewById(R.id.spinner_hour);
		 day1 = (Spinner)findViewById(R.id.spinner_day);
		 month1 = (Spinner)findViewById(R.id.spinner_month);
		 season1 = (Spinner)findViewById(R.id.spinner_season);
		 name = (EditText)findViewById(R.id.editText1);
		 
		 //checks if the database for save files is created yet and creates one if it isn't
		 CreateSelect b = new CreateSelect(this);
		
		 //populate spinners
		popSpinners();
		//open database
		myDB.open();
		//populate list view
		popListView();
		
		//create listeners for buttons  
		savProf.setOnClickListener(new OnClickListener(){
			
			 public void onClick(View view) 
			 {
				 savePress();
			 }

		 	});
		
		restart1.setOnClickListener(new OnClickListener(){
			
			 public void onClick(View view) 
			 {
				 restartPress();
			 }
		});
			
			biome.setOnItemClickListener(
					
			new android.widget.AdapterView.OnItemClickListener()
			{
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
			{
				item += ((TextView)(view.findViewById(R.id.listviewtext))).getText().toString();}
			
			});
			
		}
	
	// creates a new profile for user and saves it entering next intent
	private void savePress() {
		int h, d, m, s, r = 1; 
		String n, b;
		Long id;
		
		h = hour1.getLastVisiblePosition();
		d = day1.getLastVisiblePosition();
		m = month1.getLastVisiblePosition();
		s = season1.getLastVisiblePosition();
		
		Log.w("tag", String.valueOf(h));
		
		Random rand = new Random();
		
		if(mild1.isChecked() == true)
			r = rand.nextInt(2) + 1;
		if(poor1.isChecked() == true)
			r = rand.nextInt(3) + 2;
		if(very_poor1.isChecked() == true)
			r = rand.nextInt(3) + 3;
		if(dangerous1.isChecked() == true)
			r = rand.nextInt(3) + 5;
		if (deadly1.isChecked() == true)	
			r = rand.nextInt(3) + 7;
		
		n = name.getText().toString();
		
		b = item;
		
		CreateSaves x = new CreateSaves(this);
		x.open();
		id = x.insertRow(n, h, d, m, s, b, r); 
		x.close();
		
		Intent bb = new Intent(this, Generator.class);
		startActivity(bb);
		bb.putExtra(EXTRA, id);
		this.finish();
		
	}
	
	//resets all fields in the form
	private void restartPress() 
	{
		Intent intent = new Intent(this, New_Profile_Form.class);
		startActivity(intent);
		this.finish();
	}
 	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		myDB.close();
	}

	//creates array adapters to populate spinners
	public void popSpinners()
	{
		ArrayAdapter<CharSequence> hour_adapter = ArrayAdapter.createFromResource(
		this, R.array.Hour_Array, android.R.layout.simple_spinner_item );
		hour_adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
				
		ArrayAdapter<CharSequence> day_adapter = ArrayAdapter.createFromResource(
		this, R.array.Day_Array, android.R.layout.simple_spinner_item );
		day_adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
								
		ArrayAdapter<CharSequence> month_adapter = ArrayAdapter.createFromResource(
		this, R.array.Month_Array, android.R.layout.simple_spinner_item );
		month_adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
										
		ArrayAdapter<CharSequence> season_adapter = ArrayAdapter.createFromResource(
		this, R.array.Season_Array, android.R.layout.simple_spinner_item );
		season_adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
					
				hour1.setAdapter(hour_adapter);
				day1.setAdapter(day_adapter);
				month1.setAdapter(month_adapter);
				season1.setAdapter(season_adapter);
	}
	
	      
	
	
	//populates list view
	@SuppressWarnings("deprecation")// useful for small queries
	private void popListView() {
		Cursor cursor = myDB.getAllRows();
		startManagingCursor(cursor);
		//set up mapping
		String[] from = new String[]{DBAdapterFLV.KEY_VALUE};
		int[] to = new int[]{R.id.listviewtext};
		
		//create adapter for db ripping
		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.listview_view, cursor, from, to);
		// set adapter for list view
		biome.setAdapter(cursorAdapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new__profile__form, menu);
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
