package com.example.adamtst;

import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements android.view.View.OnClickListener{
	
	private DBHelper dbhelper = new DBHelper(this);
	private SQLiteDatabase db; // db connector
	private EditText editText;
	private Button btn1;
	private TextView textView2;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// initialize db connector
		db = dbhelper.getReadableDatabase();
		
		editText =(EditText)findViewById(R.id.editTxt);
		btn1 =(Button)findViewById(R.id.btn1);
		btn1.setOnClickListener(this);
		textView2 = (TextView)findViewById(R.id.textView2);
	}

	public void onClick(View v) {
		// get the text box
		String txtstr = editText.getText().toString();
		String res1 = "";
		// Search by name
		Cursor frst = db.rawQuery("SELECT * FROM contactsapp " +
		             "WHERE first='" + txtstr + "';", null);
		Cursor lst = db.rawQuery("SELECT * FROM contactsapp " +
	                 "WHERE last='" + txtstr + "';", null);
		// get the record
		if(frst.getCount() !=0){
			frst.moveToFirst();
			res1 = frst.getString(0).toString() + " " + frst.getString(1).toString();
			textView2.setText(res1);
		}else if(lst.getCount() != 0){
			lst.moveToFirst();
			res1 = lst.getString(0).toString() + " " + lst.getString(1).toString();
			textView2.setText(res1);
		}else{
			textView2.setText("No result found");
		}
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}




	
}
