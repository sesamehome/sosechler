package com.example.adamtst;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditDiagnosis extends Activity {
	
	private String valFirstName;
	private String valLastName;
	private String valNotes;
	private String valDiagnosis;
	private String contactId;
	
	private TextView firstName; 
	private EditText diagnosis;
	private EditText notes;
	private Button doneButton;
	private Button revertButton;
	
	private Cursor cursor;
	private DBHelper dbhelper;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_diagnosis);

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
		cursor = dbhelper.fetchEditDiagnosisById(contactId);
		valFirstName = cursor.getString(1);
		valLastName = cursor.getString(2);
		valDiagnosis = cursor.getString(3);		
		valNotes = cursor.getString(4);

		cursor.close();
		dbhelper.close();
    }	
    
    private void assignViewObjects(){
    	firstName = (TextView) findViewById(R.id.editMedicalName);
    	firstName.setText(valFirstName + " " + valLastName);    	
    	
    	notes = (EditText) findViewById(R.id.editNotes);
    	notes.setText(valNotes);
    	
    	diagnosis = (EditText) findViewById(R.id.editDiagnosis);
    	diagnosis.setText(valDiagnosis);
    }    
        
	public void addListenerOnSaveButton() {		 
		doneButton = (Button) findViewById(R.id.MedicalDoneButton); 
		doneButton.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				updateData();
				Intent intent = new Intent(EditDiagnosis.this,ViewContacts.class);
				intent.putExtra("contactId", contactId);
				startActivity(intent);	
			} 
		}); 
	}
	
	public void updateData(){
		dbhelper.updateDiagnosis(contactId, 
				notes.getText().toString(), 
				diagnosis.getText().toString()); 
	}
	
	public void addListenerOnRevertButton() {		 
		revertButton = (Button) findViewById(R.id.MedicalRevertButton); 
		revertButton.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				assignViewObjects();				
			} 
		}); 
	}		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_diagnosis, menu);
		return true;
	}

}
