package com.example.adamtst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

public class ViewContacts extends Activity {
	private TextView textBox;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_contacts);
		
		Intent intent = getIntent();
		String value = intent.getStringExtra("contactId");
		
		textBox = (TextView) findViewById(R.id.test);
		textBox.setText(value);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_contacts, menu);
		return true;
	}

}
