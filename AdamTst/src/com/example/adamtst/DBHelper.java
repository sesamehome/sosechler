package com.example.adamtst;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper{
	
	final protected static String DATABASE_NAME = "ContactsApp";
	
	static final String contactsTable="contactsapp";
	public final String colID="_id";
	public final String colFirstName="FirstName";
	public final String colLastName="LastName";
	public final String colAddress="Address";
	public final String colHomePhone="HomePhone";
	public final String colCellPhone="CellPhone";
	public final String colOfcPhone="OfficePhone";
	public final String colEmail="Email";
	public final String colDiagnosis="Diagnosis";
	public final String colNotes="Notes";
	static final int DATABASE_VERSION = 6;
	
	// Database creation sql statement
	private final String DATABASE_CREATE = "create table "
	      + contactsTable + "(" + colID + " integer primary key autoincrement, " 
	      + colFirstName + " text not null," + colLastName + " text not null,"
	      + colAddress + "," + colHomePhone + "," + colCellPhone + ","
	      +  colOfcPhone + "," + colEmail + "," + colDiagnosis + "," + colNotes + ");";	
	
	public DBHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		if(oldVersion >= newVersion) return;
		db.execSQL("DROP TABLE IF EXISTS " + contactsTable + ";");
		Log.w(DBHelper.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		onCreate(db);
	}



	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.w(DBHelper.class.getName(),"Create sql:=" + DATABASE_CREATE);
		// database definition
		db.execSQL(DATABASE_CREATE);  
	}
	
	
	 public Cursor fetchAllContacts() {
		 
		 SQLiteDatabase db =  this.getReadableDatabase();
		 
		  Cursor mCursor = db.query(contactsTable, new String[] {colID,
				  colFirstName+ "|| ' ' ||"+ colLastName},
				  null, null, null, null, colLastName);
		 
		  if (mCursor != null) {
		   mCursor.moveToFirst();
		  }
		  return mCursor;
	 }
	 
	 
	 public Cursor fetchContactsByName(String inputText) {	
		  SQLiteDatabase db =  this.getReadableDatabase();
		  Cursor mCursor = null;
		  if (inputText == null  ||  inputText.length () == 0)  {
		   mCursor = db.query(contactsTable, new String[] {colID,
					  colFirstName+ "|| ' ' ||"+ colLastName},
					  null, null, null, null, colLastName);
		  } else {
		   mCursor = db.query(contactsTable, new String[] {colID,
					  colFirstName+ "|| ' ' ||"+ colLastName},
					  colFirstName + " like '%" + inputText + "%' or " + colLastName + " like '%" + inputText + "%'" , null, null, null, colLastName);
		  }
		  if (mCursor != null) {
		   mCursor.moveToFirst();
		  }
		  return mCursor;
		 
	}

	  
	 
	 public boolean deleteAllCotacts() {
		 
		  int doneDelete = 0;
		  SQLiteDatabase db =  this.getWritableDatabase();
		  doneDelete = db.delete(contactsTable, null , null);
		  return doneDelete > 0;
		 
		 }
	 
	 public long createContacts(SQLiteDatabase db,String firstName, String lastName, String address, String homePhone,String cellPhone, String ofcPhone,
			 String email,String diagnosis, String notes) {
			  
			
			  ContentValues initialValues = new ContentValues();
			  initialValues.put(colFirstName, firstName);
			  initialValues.put(colLastName, lastName);
			  initialValues.put(colAddress, address);
			  initialValues.put(colHomePhone, homePhone);
			  initialValues.put(colCellPhone, cellPhone);
			  initialValues.put(colOfcPhone, ofcPhone);
			  initialValues.put(colEmail, email);
			  initialValues.put(colDiagnosis, diagnosis);
			  initialValues.put(colNotes, notes);
			 
			  return db.insert(contactsTable, null, initialValues);
			 }

	 public void insertContacts() {
		  SQLiteDatabase db =  this.getWritableDatabase();
		  createContacts(db,"A", "1Tester", "444 Big Dr Pittsburgh PA", "303889301","","","adam@whatever.com", "","");
		  createContacts(db,"B", "2Tester", "444 Big Dr Pittsburgh PA", "303889301","","","adam@whatever.com", "","");
		  createContacts(db,"C", "3Tester", "444 Big Dr Pittsburgh PA", "303889301","","","adam@whatever.com", "","");
		  createContacts(db,"D", "4Tester", "444 Big Dr Pittsburgh PA", "303889301","","","adam@whatever.com", "","");
		  createContacts(db,"E", "5Tester", "444 Big Dr Pittsburgh PA", "303889301","","","adam@whatever.com", "","");
		  createContacts(db,"F", "6Tester", "444 Big Dr Pittsburgh PA", "303889301","","","adam@whatever.com", "","");
		  createContacts(db,"G", "7Tester", "444 Big Dr Pittsburgh PA", "303889301","","","adam@whatever.com", "","");
		 
		 }
	 
	 public void insertContact(String firstN, String lastN, String address, String homeP, 
			                   String cellP, String officeP, String email, String diagnosis, String notes) {
		  SQLiteDatabase db =  this.getWritableDatabase();
		 try{
		  createContacts(db, firstN  , lastN  ,  address, homeP , cellP , 
		                   officeP, email, diagnosis, notes);

		 }catch(Exception e){
			 System.out.println(e.toString());
		 }
		 
		 }


	
}
