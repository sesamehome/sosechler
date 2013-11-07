package com.example.adamtst;

import android.app.Activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.RawContacts;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends Activity {
	
	private DBHelper dbhelper = new DBHelper(this);
	private EditText etFirstName, etLastName, etAddress, etHomePhone, etCellPhone, etOfficePhone, etEmail, etDiagnosis, etNotes;
	private Button etAddContact, etContactList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		
        
        OnClickListener contactListClickListener = new OnClickListener() {
			 
            @Override
            public void onClick(View v) {
                // Creating an intent to open Android's Contacts List
                Intent contactlist = new Intent(AddContact.this, MainActivity.class);
 
                // Starting the activity
                startActivity(contactlist);
            }
        };
        
     // Setting click listener for the "List Contacts" button
        Button btnContactList = (Button) findViewById(R.id.list_view);
        
        btnContactList.setOnClickListener(contactListClickListener);
 
        OnClickListener addContactClickListener = new OnClickListener() {
			 
            @Override
            public void onClick(View v) {
            	 etFirstName = (EditText) findViewById(R.id.et__first_name);
				 etLastName = (EditText) findViewById(R.id.et__last_name);
				 etAddress = (EditText) findViewById(R.id.et__address);
				 etHomePhone = (EditText) findViewById(R.id.et__home_phone);
				 etCellPhone = (EditText) findViewById(R.id.et__cell_phone);
				 etOfficePhone = (EditText) findViewById(R.id.et__office_phone);
				 etEmail = (EditText) findViewById(R.id.et__email);
				 etDiagnosis = (EditText) findViewById(R.id.et__diagnosis);
				 etNotes = (EditText) findViewById(R.id.et__notes);
				 etAddContact = (Button) findViewById(R.id.btn_add_contact);
				 etContactList = (Button) findViewById(R.id.list_view);
                // Creating an intent to open Android's Contacts List
                Intent addcontact = new Intent(AddContact.this, AddContact.class);
 
                // Starting the activity
                startActivity(addcontact);
                if(!etFirstName.getText().toString().trim().equals("") && !etLastName.getText().toString().trim().equals("")){
                try{
                dbhelper.insertContact(etFirstName.getText().toString(), etLastName.getText().toString(), etAddress.getText().toString(), etHomePhone.getText().toString(), etCellPhone.getText().toString(), etOfficePhone.getText().toString(), etEmail.getText().toString(), etDiagnosis.getText().toString(), etNotes.getText().toString());
                }catch(Exception e){
                	System.out.println("Insert exception: " + e);
                }
                }else{
                	// Required field does not contain data
                }
            }
        };
        
     // Setting click listener for the "Add Contact" button
        Button btnAddContact = (Button) findViewById(R.id.btn_add_contact);
        
        btnAddContact.setOnClickListener(addContactClickListener);
        
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.view_contacts, menu);
		return true;
	}

}
