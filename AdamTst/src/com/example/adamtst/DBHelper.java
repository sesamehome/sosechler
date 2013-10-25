package com.example.adamtst;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper{
	
	final protected static String DATABASE_NAME = "ContactsApp";
	public DBHelper(Context context){
		super(context, DATABASE_NAME, null, 2);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		if(oldVersion >= newVersion) return;
		db.execSQL("DROP DATABASE IF EXISTS" + DATABASE_NAME + ";");
		onCreate(db);
	}



	@Override
	public void onCreate(SQLiteDatabase db) {
		
		// database definition
		db.execSQL("CREATE TABLE contactsapp " + 
		           "(first TEXT, last TEXT, address TEXT, email TEXT, diagnosis TEXT);");
		
		// populate table
		db.execSQL("INSERT INTO contactsapp (first, last, address, email, diagnosis) VALUES ('Adam', 'Tester', '444 Big Dr Pittsburgh PA', 'adam@whatever.com', 'None');");
		db.execSQL("INSERT INTO contactsapp (first, last, address, email, diagnosis) VALUES ('Bill', 'Test2', '499 Big Dr Pittsburgh PA', 'bill@whatever.com', 'None');");
	}

	

	
}
