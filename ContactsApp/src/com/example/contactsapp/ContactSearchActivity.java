package com.example.contactsapp;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
 
public class ContactSearchActivity extends ListActivity
 { 
	
	
	private SimpleCursorAdapter adapter;
	
	@Override   
	public void onCreate(Bundle savedInstanceState) { 
		      super.onCreate(savedInstanceState); 		      
		      parseIntent(getIntent()); 
		   } 

		   public void onNewIntent(Intent intent) { 
		      setIntent(intent); 
		      parseIntent(intent); 
		   } 

		   public void onListItemClick(ListView l, 
		      View v, int position, long id) { 
		      // call detail activity for clicked entry 
		   } 

		   private void parseIntent(Intent intent) { 
		      if (Intent.ACTION_SEARCH.equals(intent.getAction())) { 
		         String searchquery = 
		               intent.getStringExtra(SearchManager.QUERY); 
		         performSearch(searchquery); 
		      } 
		   }    

		   private void performSearch(String queryStr) { 
		   // get a Cursor, prepare the ListAdapter
		   // and set it
		   } 
		}