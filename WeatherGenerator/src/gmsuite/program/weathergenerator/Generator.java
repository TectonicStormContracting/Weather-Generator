package gmsuite.program.weathergenerator;

import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class Generator extends Activity {
	
	final static String EXTRA = "gmsuite.program.weathergenerator.ID";
	String [] hours;
	String [] days;
	String [] months;
	String [] season;
	Long sid;
	CreateSaves x = new CreateSaves(this);
	int h, d, m, s;
	long v;
	String b, name;
	
	Button chS, chB, Intens, Detense, Rad, savex;
	RadioButton byH, byD, byW, byM;
	TextView date, discript, effects;
	Spinner cB, cS;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_generator);
		
		hours = getResources().getStringArray(R.array.Hour_Array);
		days = getResources().getStringArray(R.array.Day_Array);
		months = getResources().getStringArray(R.array.Month_Array);
		season = getResources().getStringArray(R.array.Season_Array);
		
		chS = (Button)findViewById(R.id.changeSeason);
		chB = (Button)findViewById(R.id.changeBiome);
		Intens = (Button)findViewById(R.id.Intesify);
		Detense = (Button)findViewById(R.id.Detensify);
		Rad = (Button)findViewById(R.id.Random);
		savex = (Button)findViewById(R.id.SavenExit);
		byH = (RadioButton)findViewById(R.id.radHour);
		byD = (RadioButton)findViewById(R.id.radDay);
		byW = (RadioButton)findViewById(R.id.radWeek);
		byM = (RadioButton)findViewById(R.id.radMonth);
		date = (TextView)findViewById(R.id.dateInfo);
		discript = (TextView)findViewById(R.id.discriptVis);
		effects = (TextView)findViewById(R.id.effectsVis);
		cB = (Spinner)findViewById(R.id.BiomeselectionSpinner);
		cS = (Spinner)findViewById(R.id.SeasonselectionSpinner);
		
		setListeners();

		getData();
		
		loadSpinnerData();
		
		setSpinners(b);
		
		setDateView(b);
		
		instantiateBiome(b);
	}
	//sets the listeners for the buttons
	private void setListeners()
	{
		chS.setOnClickListener(new OnClickListener(){
			
			 public void onClick(View view) 
			 {
				 changeSeason();
			 }

		 	});
		
		chB.setOnClickListener(new OnClickListener(){
			
			 public void onClick(View view) 
			 {
				 changeBiome();
			 }
		});
		
		Intens.setOnClickListener(new OnClickListener(){
			
			 public void onClick(View view) 
			 {
				 inTest();
			 }

		 	});
		
		Detense.setOnClickListener(new OnClickListener(){
			
			 public void onClick(View view) 
			 {
				 deTest();
			 }
		});
		
		Rad.setOnClickListener(new OnClickListener(){
			
			 public void onClick(View view) 
			 {
				 radTest();
			 }

		 	});
		
		savex.setOnClickListener(new OnClickListener(){
			
			 public void onClick(View view) 
			 {
				 savnex();
			 }
		});	
	}
	// gets the saved data and implements it
	private void getData()
	{
		Intent intent = getIntent();
		sid = intent.getLongExtra(EXTRA, 1);
		
		x.open();
		name = x.getName(sid);
		h = x.getHour(sid);
		d = x.getDay(sid);
		m = x.getMonth(sid);
		s = x.getSeason(sid);
		v = x.getValue(sid);
		b = x.getBiome(sid);
		x.close();
	}
	//loads the values of the biome spinner
	private void loadSpinnerData() {
        // database handler
        CreateSelect db = new CreateSelect(getApplicationContext());
        db.open();
        // Spinner Drop down elements
        List<String> lables = db.getAllNames();
        db.close();
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);
 
        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        cB.setAdapter(dataAdapter);
        
    }
	//sets the beginning value of the spinners
 	private void setSpinners(String x)
	{
		ArrayAdapter<CharSequence> season_adapter = ArrayAdapter.createFromResource(
				this, R.array.Season_Array, android.R.layout.simple_spinner_item );
				season_adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
		
		cS.setAdapter(season_adapter);
		cS.setSelection(s);
		
		String myString = x; //the value you want the position for
		ArrayAdapter myAdap = (ArrayAdapter) cB.getAdapter(); //cast to an ArrayAdapter
		int spinnerPosition = myAdap.getPosition(myString);
		//set the default according to value
		cB.setSelection(spinnerPosition);
	}
 	//sets the text view at the top of the screen
 	private void setDateView(String biome) {
		
		date.setText(hours[h] + "//" + days[d] + "//" + months[m] + "  " + 
				season[s] + "  " + biome );
		
	}
 	// creates the initial biome and fills the last data from the save into text views
 	public void instantiateBiome(String type)
	{
		
		BiomeCreator inst = new BiomeCreator(this, type);
		inst.open();
		discript.setText(inst.getDescription(v, s));
		effects.setText(inst.getEffect(v, s));
		inst.close();
	}
 	//shows spinner for change season
	protected void changeSeason() {
		chB.setVisibility(View.GONE);
		chS.setVisibility(View.GONE);
		cS.setVisibility(View.VISIBLE);
	}
	//shows spinner for change biome
	protected void changeBiome() {
		chB.setVisibility(View.GONE);
		chS.setVisibility(View.GONE);
		cB.setVisibility(View.VISIBLE);
		
	}
	//saves the profile and exits the app 
	protected void savnex() {
		
		x.open();
		x.updateRow(sid, name, h, d, m, s, cB.getSelectedItem().toString(), (int) v);
		x.close();
		this.finish();
		
	}
	//randomly generates weather based on the users specifications
	protected void radTest() {
		String sb;
		sb = cB.getSelectedItem().toString();
		if(cB.getVisibility() == View.VISIBLE)
		{
			instantiateBiome(sb);
		}
		chB.setVisibility(View.VISIBLE);
		chS.setVisibility(View.VISIBLE);
		cS.setVisibility(View.GONE);
		cB.setVisibility(View.GONE);
		s = cS.getSelectedItemPosition();
		sb = cB.getSelectedItem().toString();
		
		Random Rand = new Random();
		BiomeCreator ret = new BiomeCreator(this);
		
		if (byH.isChecked() == true)
		{
			if(h >= 23)
			{
				h = 0;
				d++;
				if(d > 31)
				{
					d = 0;
					m++;
					if (m > 11)
					{
						m = 0;
					}
				}
			}
			else
			{
				h++;
			}
			
			v = (v + (Rand.nextInt(3) - 1));
			
			if (v <= 0)
			{ 
				v = 1;
			}
			else if (v >= 11)
			{
				v = 10;
			}
			
			setDateView(sb);
			ret.open();
			discript.setText(ret.getDescription(v, s));
			effects.setText(ret.getEffect(v, s));
			ret.close();
			
		}
		
		else if (byD.isChecked() == true)
		{int inst;
			inst = (29 - (h+1));
			h = 6;
			
			if(d >= 31)
			{
				d = 0;
				m++;
				if(m > 11)
				{
					m = 0;
				}
			}
			else
			{
				d++;
			}
			
			while(inst > 0)
			{
				inst--;
				v = (v + (Rand.nextInt(3) - 1));
			
				if (v <= 0)
				{ 
					v = 1;
				}
				else if (v >= 11)
				{
					v = 10;
				}
			}
			setDateView(sb);
			ret.open();
			discript.setText(ret.getDescription(v, s));
			effects.setText(ret.getEffect(v, s));
			ret.close();
		}
		
		else if (byW.isChecked() == true)
		{ int hold, inst;
			inst = (173 - (h+1)); 
			d = d + 7;
			h = 6;
			if (d > 31)
			{
				hold = d - 31;
				d = hold;
				m++;
				if(m > 11)
					m = 0;
			}
			
			while(inst > 0)
			{
				inst--;
				v = (v + (Rand.nextInt(3) - 1));
			
				if (v <= 0)
				{ 
					v = 1;
				}
				else if (v >= 11)
				{
					v = 10;
				}
			}
			setDateView(sb);
			ret.open();
			discript.setText(ret.getDescription(v, s));
			effects.setText(ret.getEffect(v, s));
			ret.close();
		}
		
		else if (byM.isChecked() == true)
		{ 
			int inst = ((29 - (h+1)) + ((31-d)*24));
			d = 0;
			h = 6;
			if(m>10)
			{
				m = 0;
			}
			else
			{
				m++;
			}
			
			while(inst > 0)
			{
				inst--;
				v = (v + (Rand.nextInt(3) - 1));
			
				if (v <= 0)
				{ 
					v = 1;
				}
				else if (v >= 11)
				{
					v = 10;
				}
			}	
			setDateView(sb);
			ret.open();
			discript.setText(ret.getDescription(v, s));
			effects.setText(ret.getEffect(v, s));
			ret.close();
		}
	}	
	//randomly generates weather less intensly based on the users specifications
	protected void deTest() {
		String sb;
		sb = cB.getSelectedItem().toString();
		if(cB.getVisibility() == View.VISIBLE)
		{
			instantiateBiome(sb);
		}
		chB.setVisibility(View.VISIBLE);
		chS.setVisibility(View.VISIBLE);
		cS.setVisibility(View.GONE);
		cB.setVisibility(View.GONE);
		s = cS.getSelectedItemPosition();
		
		
		Random Rand = new Random();
		BiomeCreator ret = new BiomeCreator(this);
		
		if (byH.isChecked() == true)
		{
			if(h >= 23)
			{
				h = 0;
				d++;
				if(d > 31)
				{
					d = 0;
					m++;
					if (m > 11)
					{
						m = 0;
					}
				}
			}
			else
			{
				h++;
			}
			
			v = (v + (Rand.nextInt(2) - 1));
			
			if (v <= 0)
			{ 
				v = 1;
			}
			else if (v >= 11)
			{
				v = 10;
			}
			
			setDateView(sb);
			ret.open();
			discript.setText(ret.getDescription(v, s));
			effects.setText(ret.getEffect(v, s));
			ret.close();
			
		}
		
		else if (byD.isChecked() == true)
		{int inst;
			inst = (29 - (h+1));
			h = 6;
			
			if(d >= 31)
			{
				d = 0;
				m++;
				if(m > 11)
				{
					m = 0;
				}
			}
			else
			{
				d++;
			}
			
			while(inst > 0)
			{
				inst--;
				v = (v + (Rand.nextInt(2) - 1));
			
				if (v <= 0)
				{ 
					v = 1;
				}
				else if (v >= 11)
				{
					v = 10;
				}
			}
			setDateView(sb);
			ret.open();
			discript.setText(ret.getDescription(v, s));
			effects.setText(ret.getEffect(v, s));
			ret.close();
		}
		
		else if (byW.isChecked() == true)
		{ int hold, inst;
			inst = (173 - (h+1)); 
			d = d + 7;
			h = 6;
			if (d > 31)
			{
				hold = d - 31;
				d = hold;
				m++;
				if(m > 11)
					m = 0;
			}
			
			while(inst > 0)
			{
				inst--;
				v = (v + (Rand.nextInt(2) - 1));
			
				if (v <= 0)
				{ 
					v = 1;
				}
				else if (v >= 11)
				{
					v = 10;
				}
			}
			setDateView(sb);
			ret.open();
			discript.setText(ret.getDescription(v, s));
			effects.setText(ret.getEffect(v, s));
			ret.close();
		}
		
		else if (byM.isChecked() == true)
		{ 
			int inst = ((29 - (h+1)) + ((31-d)*24));
			d = 0;
			h = 6;
			if(m>10)
			{
				m = 0;
			}
			else
			{
				m++;
			}
			
			while(inst > 0)
			{
				inst--;
				v = (v + (Rand.nextInt(2) - 1));
			
				if (v <= 0)
				{ 
					v = 1;
				}
				else if (v >= 11)
				{
					v = 10;
				}
			}	
			setDateView(sb);
			ret.open();
			discript.setText(ret.getDescription(v, s));
			effects.setText(ret.getEffect(v, s));
			ret.close();
		}
	}
	//randomly generates weather more intensly based on the users specifications
	protected void inTest() {
		String sb;
		sb = cB.getSelectedItem().toString();
		if(cB.getVisibility() == View.VISIBLE)
		{
			instantiateBiome(sb);
		}
		chB.setVisibility(View.VISIBLE);
		chS.setVisibility(View.VISIBLE);
		cS.setVisibility(View.GONE);
		cB.setVisibility(View.GONE);
		s = cS.getSelectedItemPosition();
		
		
		Random Rand = new Random();
		BiomeCreator ret = new BiomeCreator(this);
		
		if (byH.isChecked() == true)
		{
			if(h >= 23)
			{
				h = 0;
				d++;
				if(d > 31)
				{
					d = 0;
					m++;
					if (m > 11)
					{
						m = 0;
					}
				}
			}
			else
			{
				h++;
			}
			
			v = (v + (Rand.nextInt(2)));
			
			if (v <= 0)
			{ 
				v = 1;
			}
			else if (v >= 11)
			{
				v = 10;
			}
			
			setDateView(sb);
			ret.open();
			discript.setText(ret.getDescription(v, s));
			effects.setText(ret.getEffect(v, s));
			ret.close();
			
		}
		
		else if (byD.isChecked() == true)
		{int inst;
			inst = (29 - (h+1));
			h = 6;
			
			if(d >= 31)
			{
				d = 0;
				m++;
				if(m > 11)
				{
					m = 0;
				}
			}
			else
			{
				d++;
			}
			
			while(inst > 0)
			{
				inst--;
				v = (v + (Rand.nextInt(2)));
			
				if (v <= 0)
				{ 
					v = 1;
				}
				else if (v >= 11)
				{
					v = 10;
				}
			}
			setDateView(sb);
			ret.open();
			discript.setText(ret.getDescription(v, s));
			effects.setText(ret.getEffect(v, s));
			ret.close();
		}
		
		else if (byW.isChecked() == true)
		{ int hold, inst;
			inst = (173 - (h+1)); 
			d = d + 7;
			h = 6;
			if (d > 31)
			{
				hold = d - 31;
				d = hold;
				m++;
				if(m > 11)
					m = 0;
			}
			
			while(inst > 0)
			{
				inst--;
				v = (v + (Rand.nextInt(2)));
			
				if (v <= 0)
				{ 
					v = 1;
				}
				else if (v >= 11)
				{
					v = 10;
				}
			}
			setDateView(sb);
			ret.open();
			discript.setText(ret.getDescription(v, s));
			effects.setText(ret.getEffect(v, s));
			ret.close();
		}
		
		else if (byM.isChecked() == true)
		{ 
			int inst = ((29 - (h+1)) + ((31-d)*24));
			d = 0;
			h = 6;
			if(m>10)
			{
				m = 0;
			}
			else
			{
				m++;
			}
			
			while(inst > 0)
			{
				inst--;
				v = (v + (Rand.nextInt(2)));
			
				if (v <= 0)
				{ 
					v = 1;
				}
				else if (v >= 11)
				{
					v = 10;
				}
			}	
			setDateView(sb);
			ret.open();
			discript.setText(ret.getDescription(v, s));
			effects.setText(ret.getEffect(v, s));
			ret.close();
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.generator, menu);
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
