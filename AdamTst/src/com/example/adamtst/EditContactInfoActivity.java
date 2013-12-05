package com.example.adamtst;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditContactInfoActivity extends Activity {

	private String valFirstName;
	private String valLastName;
	private String valHomePhone;
	private String valMobilePhone;
	private String valOfcPhone;
	private String valEmail;
	private String valAddress;
	
	private EditText firstName;
	private EditText lastName;
	private EditText mobilePhone;
	private EditText homePhone;	
	private EditText ofcPhone;
	private EditText email;	
	private EditText address; 	
	private Button doneButton;
	private Button revertButton;
	
	private Cursor cursor;
	private DBHelper dbhelper;		
	private String contactId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_contact_info);
		// set Screen
		contactId = getIntent().getStringExtra("contactId");
		assignValues(contactId);
		assignViewObjects();
		
		//Listener buttons
		addListenerOnRevertButton();
		addListenerOnSaveButton();
		
	}

	
    private void assignValues(String contactId){ 
    	dbhelper = new DBHelper(this);
		cursor = dbhelper.fetchEditContactsById(contactId);
		valFirstName = cursor.getString(1);
		valLastName = cursor.getString(2);
		valMobilePhone = formatPhone(cursor.getString(3));
		valHomePhone = formatPhone(cursor.getString(4));		
		valOfcPhone = formatPhone(cursor.getString(5));
		valEmail = cursor.getString(6);
		valAddress = cursor.getString(7);			
		cursor.close();
		dbhelper.close();
    }	
	

    private void assignViewObjects(){
    	firstName = (EditText) findViewById(R.id.editFirstName);
    	firstName.setText(valFirstName);
    	
    	lastName = (EditText) findViewById(R.id.editLastName);
    	lastName.setText(valLastName);

    	
    	mobilePhone = (EditText) findViewById(R.id.editMobilePhone);
    	mobilePhone.setText(valMobilePhone);
    	
    	homePhone = (EditText) findViewById(R.id.editHomePhone);
    	homePhone.setText(valHomePhone);
    	
    	ofcPhone = (EditText) findViewById(R.id.editOfficePhone);
    	ofcPhone.setText(valOfcPhone);
    	
    	email = (EditText) findViewById(R.id.editEmail);
    	email.setText(valEmail);
    	
    	address = (EditText) findViewById(R.id.editAddress);
    	address.setText(valAddress);
    }    
    
	
	private String formatPhone(String phoneNo){
		return PhoneNumberUtils.formatNumber(phoneNo); 
	}	 
	
	
	public void addListenerOnSaveButton() {		 
		doneButton = (Button) findViewById(R.id.DoneButton); 
		doneButton.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				updateData();
				Intent intent = new Intent(EditContactInfoActivity.this,ViewContacts.class);
				intent.putExtra("contactId", contactId);
				startActivity(intent);	
			} 
		}); 
	}
	
	public void updateData(){
		dbhelper.updateContact(contactId, 
				firstName.getText().toString(), 
				lastName.getText().toString(), 
				mobilePhone.getText().toString(), 
				homePhone.getText().toString(), 
				ofcPhone.getText().toString(), 
				email.getText().toString(), 
				address.getText().toString());
	}
	
	public void addListenerOnRevertButton() {		 
		revertButton = (Button) findViewById(R.id.RevertButton); 
		revertButton.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				assignViewObjects();				
			} 
		}); 
	}	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {		
		getMenuInflater().inflate(R.menu.edit_contact_info, menu);
		return true;
	}

}

