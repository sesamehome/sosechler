package com.sosechler.psychassist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {

	private DBHelper dbhelper = new DBHelper(this);
	private EditText inputSearch;
	private ListView lv;
	ArrayAdapter<String> adapter;
	private SimpleCursorAdapter dataAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); 
		//Toast.makeText(getApplicationContext(), "Called after back", Toast.LENGTH_SHORT).show();	
		initListView();
	}

	private void openAddPatients(){
		Intent addcontact = new Intent(MainActivity.this, AddContact.class); 
		startActivity(addcontact);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void initListView(){
		//Declarations
		lv = (ListView) findViewById(R.id.list_view);
		inputSearch = (EditText) findViewById(R.id.inputSearch);		

		String[] columns = new String[] {dbhelper.colFirstName + "|| ' ' ||" + dbhelper.colLastName};  
		int[] to = new int[] {R.id.contact_name};

		//List view data
		Cursor cursor = dbhelper.fetchAllContacts();
		dataAdapter = new SimpleCursorAdapter(this, R.layout.list_item,cursor,columns,to,0);
		lv.setAdapter(dataAdapter);

		//Enabling Search Filter         
		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
				// When user changed the Text
				MainActivity.this.dataAdapter.getFilter().filter(cs.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub  
				System.out.print(arg0);
			}
		});

		//Filter results
		dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
			public Cursor runQuery(CharSequence constraint) {
				return dbhelper.fetchContactsByName(constraint.toString());
			}
		});

		//Clicking listViewItem 
		lv.setOnItemClickListener(new OnItemClickListener() { 
			public void onItemClick(AdapterView<?> listView, View view,int position, long id) {

				Cursor cursor = (Cursor) listView.getItemAtPosition(position);   
				String contactId =  cursor.getString(cursor.getColumnIndexOrThrow("_id"));
				//Toast.makeText(getApplicationContext(),contactId, Toast.LENGTH_SHORT).show();

				//start new intent
				Intent intent = new Intent(MainActivity.this,ViewContacts.class);
				intent.putExtra("contactId", contactId);
				startActivity(intent);


			}
		}); 


	} 


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_add: 
			openAddPatients();
			return true; 
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onResume(){
	    
		this.onCreate(null);
	    super.onResume();
	  
	}	

	/*code from previous version*/

	//	 OnClickListener addContactClickListener = new OnClickListener() {
	//		 
	//           @Override
	//           public void onClick(View v) {
	//               // Creating an intent to open Android's Contacts List
	//               Intent addcontact = new Intent(MainActivity.this, AddContact.class);
	//
	//               // Starting the activity
	//               startActivity(addcontact);
	//           }
	//       };
	//       
	//    // Setting click listener for the "Add Contact" button
	//       Button btnAddContact = (Button) findViewById(R.id.btn_add_contact);
	//       
	//       btnAddContact.setOnClickListener(addContactClickListener);
	//initListView

}
