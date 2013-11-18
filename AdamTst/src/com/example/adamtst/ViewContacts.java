package com.example.adamtst;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ViewContacts extends Activity {
	private TextView homePhone;
	private TextView mobilePhone;
	private TextView ofcPhone;
	private TextView email;
	private TextView address;
	private TextView diagnosis;
	private TextView notes;
    private String contactId;
	
	private DBHelper dbhelper = new DBHelper(this);
	
	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_contact);

		// enable the home button
	    ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
		
		
		//List view data
		Cursor cursor = dbhelper.fetchContactsById(getIntent().getStringExtra("contactId"));
		contactId = getIntent().getStringExtra("contactId");
		
			
		actionBar.setTitle(cursor.getString(1)); 
		
		homePhone = (TextView) findViewById(R.id.HomePhone);		
		homePhone.setText(formatPhone(cursor.getString(3)));
		
		mobilePhone = (TextView) findViewById(R.id.MobilePhone);
		mobilePhone.setText(formatPhone(cursor.getString(4)));
		
		ofcPhone = (TextView) findViewById(R.id.OfficePhone);
		ofcPhone.setText(formatPhone(cursor.getString(5))); 
		
		email = (TextView) findViewById(R.id.Email);
		email.setText(cursor.getString(6));

		address = (TextView) findViewById(R.id.Address);
		address.setText(cursor.getString(2));
		
		diagnosis = (TextView) findViewById(R.id.Diagnosis);
		diagnosis.setText(cursor.getString(7));
		
		notes = (TextView) findViewById(R.id.Notes);
		notes.setText(cursor.getString(8));


		
		
	}
	
	private String formatPhone(String phoneNo){
		return PhoneNumberUtils.formatNumber(phoneNo); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_contacts, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_edit:  
	            return true; 
	        case R.id.action_delete:
	        	deleteContact(contactId);
	        	//finish();
	        	onBackPressed();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	private void deleteContact(String contactId) {
		
		dbhelper.deleteContact(contactId);
		Toast.makeText(getApplicationContext(), "Contact Successfully deleted", Toast.LENGTH_SHORT).show();		
		
	}	
	
}
