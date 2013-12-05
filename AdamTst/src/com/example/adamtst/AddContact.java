package com.example.adamtst;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddContact extends Activity {
	
	private DBHelper dbhelper = new DBHelper(this);
	private EditText etFirstName, etLastName, etAddress, etHomePhone, etCellPhone, etOfficePhone, etEmail, etDiagnosis, etNotes;
	Spinner spnPhone;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		// enable the home button
	    ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
		
        //initialize page objects
        initPageObjects();
        
        //set up click listener
        addPatientListenerOnButton(); 
        
	}

	public void initPageObjects(){
      	 etFirstName = (EditText) findViewById(R.id.et__first_name);
		 etLastName = (EditText) findViewById(R.id.et__last_name);
		 etAddress = (EditText) findViewById(R.id.et__address);
		 etHomePhone = (EditText) findViewById(R.id.et__home_phone);
		 etCellPhone = (EditText) findViewById(R.id.et__cell_phone);
		 etOfficePhone = (EditText) findViewById(R.id.et__office_phone);
		 etEmail = (EditText) findViewById(R.id.et__email);
		 etDiagnosis = (EditText) findViewById(R.id.et__diagnosis);
		 etNotes = (EditText) findViewById(R.id.et__notes);
		 //spnPhone = (Spinner) findViewById(R.id.spn_phone_spinner);
	}
	
	public void addPatientListenerOnButton() {
		 
		Button btnAddContact = (Button) findViewById(R.id.btn_add_contact);
		btnAddContact.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
				 saveNewPatient();
            }
        });		
		 
	}
	
	public void phoneTypesListenerOnSpinner() {
		 
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.phone_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) ;
		spnPhone.setAdapter(adapter);
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_contacts, menu); 
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_save: 
	        	saveNewPatient();
	            return true; 
	        case R.id.action_undo:
	        	clearFields();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void clearFields(){
		 etFirstName.setText("");
		 etLastName.setText("");
		 etAddress.setText("");
		 etHomePhone.setText("");
		 etCellPhone.setText("");
		 etOfficePhone.setText("");
		 etEmail.setText("");
		 etDiagnosis.setText("");
		 etNotes.setText("");		
	}
	
	private void saveNewPatient(){
    	if( etFirstName.getText().toString().length() == 0 )
    		etFirstName.setError( "First name is required!" );
    	
    	if( etLastName.getText().toString().length() == 0 )
    		etLastName.setError( "Last name is required!" );
    	
        if(!etFirstName.getText().toString().trim().equals("") && !etLastName.getText().toString().trim().equals("")){
            try{
        		dbhelper.insertContact(etFirstName.getText().toString(), etLastName.getText().toString(), etAddress.getText().toString(), 
						   etHomePhone.getText().toString(), etCellPhone.getText().toString(), etOfficePhone.getText().toString(), 
						   etEmail.getText().toString(), etDiagnosis.getText().toString(), etNotes.getText().toString());
        		Toast.makeText(getApplicationContext(), "Contact Successfully Saved", Toast.LENGTH_SHORT).show();		

            }catch(Exception e){
            	System.out.println("Insert exception: " + e);
            }
        }else{
        	 Toast.makeText(getApplicationContext(), "First and Last Name are required fields", Toast.LENGTH_SHORT).show();
        }		
	}
}
