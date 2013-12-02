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

	// SQL statements
	private final String DATABASE_CREATE = "create table "
			+ contactsTable + "(" + colID + " integer primary key autoincrement, " 
			+ colFirstName + " text not null," + colLastName + " text not null,"
			+ colAddress + "," + colHomePhone + "," + colCellPhone + ","
			+  colOfcPhone + "," + colEmail + "," + colDiagnosis + "," + colNotes + ");";	
	
	private final String DATABASE_DROP="DROP TABLE IF EXISTS " + contactsTable + ";";

	public DBHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		if(oldVersion >= newVersion) return;
		db.execSQL(DATABASE_DROP);
		onCreate(db);
	}



	@Override
	public void onCreate(SQLiteDatabase db) {
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
		db.close();
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
					colFirstName + " like '%" + inputText + "%' or " + colLastName + " like '%" + 
					inputText + "%'" , null, null, null, colLastName);
		}
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		db.close();
		return mCursor;

	}

	public Cursor fetchContactsById(String id){
		SQLiteDatabase db =  this.getReadableDatabase();
		Cursor mCursor = null;
		mCursor = db.query(contactsTable, new String[] {colID,
					colFirstName+ "|| ' ' ||"+ colLastName,colAddress,colHomePhone,
					colCellPhone,colOfcPhone,colEmail,colDiagnosis,colNotes},
					colID + " ='" + id + "'" , null, null, null, colLastName);
		
		if (mCursor != null) {
			Log.d("MyActivity", "mCursor is not null");
			mCursor.moveToFirst();
		}
		db.close();
		return mCursor;		 
	}
	
	public Cursor fetchEditContactsById(String id){
		SQLiteDatabase db =  this.getReadableDatabase();
		Cursor mCursor = null;
		mCursor = db.query(contactsTable, new String[] {colID,
					colFirstName,colLastName,colCellPhone,colHomePhone,
					colOfcPhone,colEmail,colAddress,},
					colID + " ='" + id + "'" , null, null, null, null);
		
		if (mCursor != null) {
			Log.d("MyActivity", "mCursor is not null");
			mCursor.moveToFirst();
		}
		db.close();
		return mCursor;		 
	}
	

	public Cursor fetchDiagnosisById(String id){
		SQLiteDatabase db =  this.getReadableDatabase();
		Cursor mCursor = null;
		mCursor = db.query(contactsTable, new String[] {colID,colFirstName+ "|| ' ' ||"+ colLastName,colDiagnosis,colNotes},
					colID + " ='" + id + "'" , null, null, null, null);
		
		if (mCursor != null) {
			Log.d("MyActivity", "mCursor is not null");
			mCursor.moveToFirst();
		}
		db.close();
		return mCursor;		 
	}
	
	public Cursor fetchEditDiagnosisById(String id){
		SQLiteDatabase db =  this.getReadableDatabase();
		Cursor mCursor = null;
		mCursor = db.query(contactsTable, new String[] {colID,colFirstName,colLastName,colDiagnosis,colNotes},
					colID + " ='" + id + "'" , null, null, null, null);
		
		if (mCursor != null) {
			Log.d("MyActivity", "mCursor is not null");
			mCursor.moveToFirst();
		}
		db.close();
		return mCursor;		 
	}
	
	public boolean deleteAllContacts() {

		int doneDelete = 0;
		SQLiteDatabase db =  this.getWritableDatabase();
		doneDelete = db.delete(contactsTable, null , null);
		db.close();
		return doneDelete > 0;

	}
	
	public boolean deleteContact(String contactId){
		SQLiteDatabase db =  this.getWritableDatabase();
		boolean result = db.delete(contactsTable, colID + "='" + contactId + "'", null) > 0;
		db.close();
		return result;
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

		long result = db.insert(contactsTable, null, initialValues);
		db.close();
		return result;
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
	
	public int updateContact(String contactId,String firstName, String lastName,String cellPhone,String homePhone,String ofcPhone,
			String email,String address){
		SQLiteDatabase db =  this.getWritableDatabase();
		ContentValues args = new ContentValues();
		args.put(colFirstName, firstName);
		args.put(colLastName, lastName);
		args.put(colCellPhone, cellPhone);
		args.put(colHomePhone, homePhone);
		args.put(colOfcPhone, ofcPhone);
		args.put(colEmail, email);
		
		args.put(colAddress, address);
		int i = db.update(contactsTable, args, colID + "=" + contactId, null);
		db.close();
		return i;
	}
	
	public int updateDiagnosis(String contactId,String notes, String diagnosis){
		SQLiteDatabase db =  this.getWritableDatabase();
		ContentValues args = new ContentValues();
		args.put(colNotes, notes);
		args.put(colDiagnosis, diagnosis);
		int i = db.update(contactsTable, args, colID + "=" + contactId, null);
		db.close();
		return i;
	}



}
